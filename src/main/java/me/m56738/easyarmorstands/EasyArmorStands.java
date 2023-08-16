package me.m56738.easyarmorstands;

import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.arguments.parser.ParserParameters;
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
import me.m56738.easyarmorstands.command.Description;
import me.m56738.easyarmorstands.command.GlobalCommands;
import me.m56738.easyarmorstands.command.HistoryCommands;
import me.m56738.easyarmorstands.command.SessionCommands;
import me.m56738.easyarmorstands.command.parser.NodeValueArgumentParser;
import me.m56738.easyarmorstands.command.processor.ValueNodeInjector;
import me.m56738.easyarmorstands.command.sender.CommandSenderWrapper;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.element.ArmorStandElementProvider;
import me.m56738.easyarmorstands.element.ElementMenuListener;
import me.m56738.easyarmorstands.element.EntityElementListener;
import me.m56738.easyarmorstands.element.EntityElementProviderRegistry;
import me.m56738.easyarmorstands.element.SimpleEntityElementProvider;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.history.HistoryManager;
import me.m56738.easyarmorstands.menu.MenuListener;
import me.m56738.easyarmorstands.message.CommandTagResolver;
import me.m56738.easyarmorstands.message.MessageLoader;
import me.m56738.easyarmorstands.message.TemplateTagResolver;
import me.m56738.easyarmorstands.node.ValueNode;
import me.m56738.easyarmorstands.permission.PermissionLoader;
import me.m56738.easyarmorstands.session.SessionListener;
import me.m56738.easyarmorstands.session.SessionManager;
import me.m56738.easyarmorstands.update.UpdateListener;
import me.m56738.easyarmorstands.update.UpdateManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Level;

public class EasyArmorStands extends JavaPlugin {
    private static EasyArmorStands instance;
    private final CapabilityLoader loader = new CapabilityLoader(this, getClassLoader());
    private final AddonLoader addonLoader = new AddonLoader(this, getClassLoader());
    private EntityElementProviderRegistry entityElementProviderRegistry;
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

        entityElementProviderRegistry = new EntityElementProviderRegistry();
        sessionManager = new SessionManager();
        historyManager = new HistoryManager();
        adventure = BukkitAudiences.create(this);

        entityElementProviderRegistry.register(new ArmorStandElementProvider());
        entityElementProviderRegistry.register(new SimpleEntityElementProvider());

        SessionListener sessionListener = new SessionListener(this, sessionManager);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(sessionListener, this);
        getServer().getPluginManager().registerEvents(historyManager, this);
        getServer().getPluginManager().registerEvents(new EntityElementListener(), this);
        getServer().getPluginManager().registerEvents(new ElementMenuListener(), this);
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

        commandManager.parameterInjectorRegistry().registerInjector(ValueNode.class, new ValueNodeInjector());

        commandManager.parserRegistry().registerNamedParserSupplier("node_value",
                p -> new NodeValueArgumentParser<>());

        commandManager.parserRegistry().registerParserSupplier(TypeToken.get(TextColor.class),
                p -> new TextColorArgument.TextColorParser<>());

        annotationParser = new AnnotationParser<>(commandManager, EasCommandSender.class,
                p -> CommandMeta.simple()
                        .with(CommandMeta.DESCRIPTION, p.get(StandardParameters.DESCRIPTION, "No description"))
                        .build());

        annotationParser.registerAnnotationMapper(Description.class,
                a -> ParserParameters.single(StandardParameters.DESCRIPTION,
                        PlainTextComponentSerializer.plainText().serialize(a.value().render())));

        MessageLoader.setSerializer(MiniMessage.builder()
                .editTags(builder -> builder.resolvers(
                        new CommandTagResolver(commandManager),
                        new TemplateTagResolver(),
                        TagResolver.resolver("version", Tag.selfClosingInserting(Component.text(getDescription().getVersion())))))
                .build());

        YamlConfiguration messageConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "messages.yml"));
        InputStream defaultMessagesStream = getResource("messages.yml");
        if (defaultMessagesStream != null) {
            try (InputStreamReader reader = new InputStreamReader(defaultMessagesStream, StandardCharsets.UTF_8)) {
                YamlConfiguration defaults = new YamlConfiguration();
                defaults.load(reader);
                messageConfig.setDefaults(defaults);
            } catch (IOException | InvalidConfigurationException e) {
                getLogger().log(Level.SEVERE, "Failed to load default messages", e);
            }
        }
        MessageLoader.setConfig(messageConfig);

        annotationParser.parse(new GlobalCommands(commandManager, sessionListener));
        annotationParser.parse(new SessionCommands());
        annotationParser.parse(new HistoryCommands());

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

    @Contract(pure = true)
    public <T> T getCapability(Class<T> type) {
        return loader.get(type);
    }

    @Contract(pure = true)
    public <T extends Addon> @Nullable T getAddon(Class<T> type) {
        return addonLoader.get(type);
    }

    public CapabilityLoader getCapabilityLoader() {
        return loader;
    }

    public EntityElementProviderRegistry getEntityElementProviderRegistry() {
        return entityElementProviderRegistry;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    public UpdateManager getUpdateManager() {
        return updateManager;
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
