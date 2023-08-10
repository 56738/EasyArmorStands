package me.m56738.easyarmorstands;

import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.arguments.parser.StandardParameters;
import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler;
import cloud.commandframework.minecraft.extras.TextColorArgument;
import cloud.commandframework.paper.PaperCommandManager;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.addon.AddonLoader;
import me.m56738.easyarmorstands.capability.CapabilityLoader;
import me.m56738.easyarmorstands.command.EntityCommands;
import me.m56738.easyarmorstands.command.GlobalCommands;
import me.m56738.easyarmorstands.command.HistoryCommands;
import me.m56738.easyarmorstands.command.SessionCommands;
import me.m56738.easyarmorstands.command.annotation.RequireEntity;
import me.m56738.easyarmorstands.command.annotation.RequireSession;
import me.m56738.easyarmorstands.command.parser.EntityPropertyArgumentParser;
import me.m56738.easyarmorstands.command.parser.NodeValueArgumentParser;
import me.m56738.easyarmorstands.command.processor.EntityInjectionService;
import me.m56738.easyarmorstands.command.processor.EntityPostprocessor;
import me.m56738.easyarmorstands.command.processor.EntityPreprocessor;
import me.m56738.easyarmorstands.command.processor.Keys;
import me.m56738.easyarmorstands.command.processor.SessionInjector;
import me.m56738.easyarmorstands.command.processor.SessionPostprocessor;
import me.m56738.easyarmorstands.command.processor.SessionPreprocessor;
import me.m56738.easyarmorstands.command.processor.ValueNodeInjector;
import me.m56738.easyarmorstands.command.sender.CommandSenderWrapper;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.history.HistoryManager;
import me.m56738.easyarmorstands.menu.MenuListener;
import me.m56738.easyarmorstands.node.ValueNode;
import me.m56738.easyarmorstands.permission.PermissionLoader;
import me.m56738.easyarmorstands.property.EntityPropertyRegistry;
import me.m56738.easyarmorstands.property.EntityPropertyTypeRegistry;
import me.m56738.easyarmorstands.property.LegacyEntityPropertyType;
import me.m56738.easyarmorstands.property.ResettableEntityProperty;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandPoseProperty;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.session.SessionListener;
import me.m56738.easyarmorstands.session.SessionManager;
import me.m56738.easyarmorstands.update.UpdateListener;
import me.m56738.easyarmorstands.update.UpdateManager;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumMap;
import java.util.Objects;
import java.util.logging.Level;

public class EasyArmorStands extends JavaPlugin {
    private static EasyArmorStands instance;
    private final CapabilityLoader loader = new CapabilityLoader(this, getClassLoader());
    private final AddonLoader addonLoader = new AddonLoader(this, getClassLoader());
    private final EnumMap<ArmorStandPart, ArmorStandPoseProperty> armorStandPoseProperties =
            new EnumMap<>(ArmorStandPart.class);
    private EntityPropertyRegistry entityPropertyRegistry;
    private EntityPropertyTypeRegistry entityPropertyTypeRegistry;
    private SessionManager sessionManager;
    private HistoryManager historyManager;
    private UpdateManager updateManager;
    private BukkitAudiences adventure;
    private PaperCommandManager<EasCommandSender> commandManager;
    private AnnotationParser<EasCommandSender> annotationParser;

    public static EasyArmorStands getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        new Metrics(this, 17911);

        try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/permissions.yml")))) {
            new PermissionLoader(this).load(YamlConfiguration.loadConfiguration(reader));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loader.load();

        sessionManager = new SessionManager();
        historyManager = new HistoryManager();
        adventure = BukkitAudiences.create(this);

        SessionListener sessionListener = new SessionListener(this, sessionManager);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(sessionListener, this);
        getServer().getPluginManager().registerEvents(historyManager, this);
        getServer().getScheduler().runTaskTimer(this, sessionManager::update, 0, 1);

        CommandSenderWrapper senderWrapper = new CommandSenderWrapper(adventure);

        try {
            commandManager = new PaperCommandManager<>(
                    this,
                    CommandExecutionCoordinator.simpleCoordinator(),
                    senderWrapper::wrap,
                    EasCommandSender::get);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (commandManager.hasCapability(CloudBukkitCapabilities.BRIGADIER)) {
            try {
                commandManager.registerBrigadier();
            } catch (BukkitCommandManager.BrigadierFailureException e) {
                getLogger().log(Level.WARNING, "Failed to register Brigadier mappings");
            }
        }

        new MinecraftExceptionHandler<EasCommandSender>()
                .withArgumentParsingHandler()
                .withInvalidSyntaxHandler()
                .withNoPermissionHandler()
                .withCommandExecutionHandler()
                .withHandler(MinecraftExceptionHandler.ExceptionType.INVALID_SENDER,
                        (sender, e) -> Component.text("Only players can use this command", NamedTextColor.RED))
                .apply(commandManager, s -> s);

        commandManager.registerCommandPreProcessor(new EntityPreprocessor<>());
        commandManager.registerCommandPostProcessor(new EntityPostprocessor());
        commandManager.parameterInjectorRegistry().registerInjectionService(new EntityInjectionService<>());

        commandManager.registerCommandPreProcessor(new SessionPreprocessor<>(sessionManager, EasCommandSender::get));
        commandManager.registerCommandPostProcessor(new SessionPostprocessor());
        commandManager.parameterInjectorRegistry().registerInjector(Session.class, new SessionInjector<>());

        commandManager.parameterInjectorRegistry().registerInjector(ValueNode.class, new ValueNodeInjector<>());

        commandManager.parserRegistry().registerNamedParserSupplier("node_value",
                p -> new NodeValueArgumentParser<>());

        commandManager.parserRegistry().registerParserSupplier(TypeToken.get(ResettableEntityProperty.class),
                p -> new EntityPropertyArgumentParser<>(ResettableEntityProperty.class));

        commandManager.parserRegistry().registerParserSupplier(TypeToken.get(LegacyEntityPropertyType.class),
                p -> new EntityPropertyArgumentParser<>(LegacyEntityPropertyType.class));

        commandManager.parserRegistry().registerParserSupplier(TypeToken.get(TextColor.class),
                p -> new TextColorArgument.TextColorParser<>());

        entityPropertyRegistry = new EntityPropertyRegistry();
        entityPropertyTypeRegistry = new EntityPropertyTypeRegistry();

        annotationParser = new AnnotationParser<>(commandManager, EasCommandSender.class,
                p -> CommandMeta.simple()
                        .with(CommandMeta.DESCRIPTION, p.get(StandardParameters.DESCRIPTION, "No description"))
                        .build());

        annotationParser.registerBuilderModifier(RequireSession.class, (a, b) -> b.meta(Keys.SESSION_REQUIRED, true));
        annotationParser.registerBuilderModifier(RequireEntity.class, (a, b) -> b.meta(Keys.ENTITY_REQUIRED,
                entity -> a.value().isAssignableFrom(entity.getClass())));

        annotationParser.parse(new GlobalCommands(commandManager, sessionManager, sessionListener));
        annotationParser.parse(new SessionCommands());
        annotationParser.parse(new HistoryCommands());
        annotationParser.parse(new EntityCommands());

        addonLoader.load();

        getConfig().options().copyDefaults(true);
        getConfig().addDefault("update-check", true);
        saveConfig();

        boolean isSnapshot = getDescription().getVersion().endsWith("-SNAPSHOT");
        if (getConfig().getBoolean("update-check", false) && !isSnapshot) {
            updateManager = new UpdateManager(this, adventure, "easyarmorstands.update.notify", 108349);
            getServer().getPluginManager().registerEvents(new UpdateListener(updateManager, adventure), this);
            getServer().getScheduler().runTaskTimerAsynchronously(this, updateManager::refresh, 0, 20 * 60 * 60 * 24);
        }
    }

    @Override
    public void onDisable() {
        sessionManager.stopAllSessions();
    }

    public History getHistory(Player player) {
        return historyManager.getHistory(player);
    }

    public <T> T getCapability(Class<T> type) {
        return loader.get(type);
    }

    public <T extends Addon> @Nullable T getAddon(Class<T> type) {
        return addonLoader.get(type);
    }

    public CapabilityLoader getCapabilityLoader() {
        return loader;
    }

    public EntityPropertyRegistry getEntityPropertyRegistry() {
        return entityPropertyRegistry;
    }

    public EntityPropertyTypeRegistry getEntityPropertyTypeRegistry() {
        return entityPropertyTypeRegistry;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    public BukkitAudiences getAdventure() {
        return adventure;
    }

    public PaperCommandManager<EasCommandSender> getCommandManager() {
        return commandManager;
    }

    public AnnotationParser<EasCommandSender> getAnnotationParser() {
        return annotationParser;
    }
}
