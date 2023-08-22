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
import me.m56738.easyarmorstands.api.element.EntityElementProviderRegistry;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import me.m56738.easyarmorstands.capability.CapabilityLoader;
import me.m56738.easyarmorstands.capability.tool.ToolCapability;
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
import me.m56738.easyarmorstands.element.EntityElementProviderRegistryImpl;
import me.m56738.easyarmorstands.element.SimpleEntityElementProvider;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.history.HistoryManager;
import me.m56738.easyarmorstands.menu.MenuListener;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.message.MessageManager;
import me.m56738.easyarmorstands.node.ValueNode;
import me.m56738.easyarmorstands.property.type.DefaultPropertyTypes;
import me.m56738.easyarmorstands.property.type.PropertyTypeRegistryImpl;
import me.m56738.easyarmorstands.session.SessionListener;
import me.m56738.easyarmorstands.session.SessionManager;
import me.m56738.easyarmorstands.update.UpdateManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.format.TextColor;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;

public class EasyArmorStands extends JavaPlugin {
    private static EasyArmorStands instance;
    private final CapabilityLoader loader = new CapabilityLoader(this, getClassLoader());
    private EasConfig config;
    private MessageManager messageManager;
    private PropertyTypeRegistryImpl propertyTypeRegistry;
    private EntityElementProviderRegistryImpl entityElementProviderRegistry;
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
        loader.load();
        adventure = BukkitAudiences.create(this);

        config = new EasConfig(this);
        config.load();

        messageManager = new MessageManager(this);
        config.subscribe(messageManager::load);

        propertyTypeRegistry = new PropertyTypeRegistryImpl();
        config.subscribe(propertyTypeRegistry::load);
        PropertyTypeRegistry.Holder.instance = propertyTypeRegistry;

        new DefaultPropertyTypes(propertyTypeRegistry);

        entityElementProviderRegistry = new EntityElementProviderRegistryImpl();
        EntityElementProviderRegistry.Holder.instance = entityElementProviderRegistry;

        sessionManager = new SessionManager();
        historyManager = new HistoryManager();

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
                        (sender, e) -> Message.error("easyarmorstands.error.not-a-player"))
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

        annotationParser.parse(new GlobalCommands(commandManager, sessionListener));
        annotationParser.parse(new SessionCommands());
        annotationParser.parse(new HistoryCommands());

        if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
            getLogger().info("Enabling WorldGuard integration");
            if (hasClass("com.sk89q.worldguard.protection.regions.RegionContainer")) {
                loadAddon("me.m56738.easyarmorstands.addon.worldguard.v7.WorldGuardAddon");
            } else if (hasClass("com.sk89q.worldguard.bukkit.WGBukkit")) {
                loadAddon("me.m56738.easyarmorstands.addon.worldguard.v6.WorldGuardAddon");
            } else {
                getLogger().warning("Unsupported WorldGuard version");
            }
        }

        if (Bukkit.getPluginManager().isPluginEnabled("PlotSquared")) {
            getLogger().info("Enabling PlotSquared integration");
            loadAddon("me.m56738.easyarmorstands.plotsquared.v6.PlotSquaredAddon");
        }

        if (Bukkit.getPluginManager().isPluginEnabled("HeadDatabase")) {
            getLogger().info("Enabling HeadDatabase integration");
            loadAddon("me.m56738.easyarmorstands.headdatabase.HeadDatabaseAddon");
        }

        if (hasClass("com.bergerkiller.bukkit.tc.attachments.ui.models.listing.DialogResult")) {
            getLogger().info("Enabling TrainCarts integration");
            loadAddon("me.m56738.easyarmorstands.traincarts.TrainCartsAddon");
        }

        if (hasClass("org.bukkit.entity.ItemDisplay")) {
            loadAddon("me.m56738.easyarmorstands.display.DisplayAddon");
        }

        if (!getDescription().getVersion().endsWith("-SNAPSHOT")) {
            config.subscribe(cfg -> {
                if (cfg.isUpdateCheck()) {
                    if (updateManager == null) {
                        updateManager = new UpdateManager(this, adventure, "easyarmorstands.update.notify", 108349);
                    }
                } else {
                    if (updateManager != null) {
                        updateManager.unregister();
                        updateManager = null;
                    }
                }
            });
        }
    }

    private boolean hasClass(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    private void loadAddon(String name) {
        try {
            Class.forName(name).getDeclaredConstructor(EasyArmorStands.class).newInstance(this);
        } catch (InvocationTargetException e) {
            getLogger().log(Level.SEVERE, "Failed to enable addon " + name, e.getCause());
        } catch (ReflectiveOperationException e) {
            getLogger().log(Level.SEVERE, "Failed to instantiate addon " + name, e);
        }
    }

    @Override
    public void onDisable() {
        if (sessionManager != null) {
            sessionManager.stopAllSessions();
        }
    }

    public void reload() {
        reloadConfig();
        config.load();
    }

    public History getHistory(Player player) {
        return historyManager.getHistory(player);
    }

    @Contract(pure = true)
    public <T> T getCapability(Class<T> type) {
        return loader.get(type);
    }

    public CapabilityLoader getCapabilityLoader() {
        return loader;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public PropertyTypeRegistry getPropertyTypeRegistry() {
        return propertyTypeRegistry;
    }

    public EntityElementProviderRegistryImpl getEntityElementProviderRegistry() {
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

    public ItemStack createTool(Locale locale) {
        return config.getToolTemplate().render(locale);
    }

    public boolean isTool(ItemStack item) {
        if (item == null) {
            return false;
        }
        ToolCapability toolCapability = EasyArmorStands.getInstance().getCapability(ToolCapability.class);
        if (toolCapability != null) {
            return toolCapability.isTool(item);
        }

        // Tool capability is not supported
        // Match any item with the right material and any customized display name
        // TODO Add NBT implementation for tool capability to avoid this
        if (!Objects.equals(config.getToolTemplate().getType(), item.getType())) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        return meta.hasDisplayName();
    }

    public EasConfig getConfiguration() {
        return config;
    }
}
