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
import me.m56738.easyarmorstands.adapter.EntityPlaceAdapter;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.EasyArmorStandsInitializer;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.EntityElement;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuFactory;
import me.m56738.easyarmorstands.api.menu.MenuSlotTypeRegistry;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import me.m56738.easyarmorstands.capability.CapabilityLoader;
import me.m56738.easyarmorstands.capability.tool.ToolCapability;
import me.m56738.easyarmorstands.color.ColorAxisChangeSlotType;
import me.m56738.easyarmorstands.color.ColorAxisSlotType;
import me.m56738.easyarmorstands.color.ColorIndicatorSlotType;
import me.m56738.easyarmorstands.color.ColorPickerContextImpl;
import me.m56738.easyarmorstands.color.ColorPresetSlotType;
import me.m56738.easyarmorstands.color.ColorSlot;
import me.m56738.easyarmorstands.command.GlobalCommands;
import me.m56738.easyarmorstands.command.HistoryCommands;
import me.m56738.easyarmorstands.command.SessionCommands;
import me.m56738.easyarmorstands.command.parser.NodeValueArgumentParser;
import me.m56738.easyarmorstands.command.processor.ValueNodeInjector;
import me.m56738.easyarmorstands.command.sender.CommandSenderWrapper;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.config.serializer.EasSerializers;
import me.m56738.easyarmorstands.element.ArmorStandElementProvider;
import me.m56738.easyarmorstands.element.ArmorStandElementType;
import me.m56738.easyarmorstands.element.EntityElementListener;
import me.m56738.easyarmorstands.element.EntityElementProviderRegistryImpl;
import me.m56738.easyarmorstands.element.SimpleEntityElementProvider;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.history.HistoryManager;
import me.m56738.easyarmorstands.menu.ColorPickerMenuContext;
import me.m56738.easyarmorstands.menu.ElementMenuContext;
import me.m56738.easyarmorstands.menu.MenuListener;
import me.m56738.easyarmorstands.menu.MenuSlotTypeRegistryImpl;
import me.m56738.easyarmorstands.menu.SimpleMenuContext;
import me.m56738.easyarmorstands.menu.slot.ArmorStandPartSlotType;
import me.m56738.easyarmorstands.menu.slot.ArmorStandPositionSlotType;
import me.m56738.easyarmorstands.menu.slot.ArmorStandSpawnSlotType;
import me.m56738.easyarmorstands.menu.slot.BackgroundSlotType;
import me.m56738.easyarmorstands.menu.slot.ColorPickerSlotType;
import me.m56738.easyarmorstands.menu.slot.DestroySlotType;
import me.m56738.easyarmorstands.menu.slot.FallbackSlotType;
import me.m56738.easyarmorstands.menu.slot.PropertySlotType;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.message.MessageManager;
import me.m56738.easyarmorstands.node.ValueNode;
import me.m56738.easyarmorstands.property.type.DefaultPropertyTypes;
import me.m56738.easyarmorstands.property.type.PropertyTypeRegistryImpl;
import me.m56738.easyarmorstands.session.SessionListener;
import me.m56738.easyarmorstands.session.SessionManager;
import me.m56738.easyarmorstands.update.UpdateManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.format.TextColor;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EasyArmorStandsPlugin extends JavaPlugin implements EasyArmorStands {
    private static EasyArmorStandsPlugin instance;
    private final CapabilityLoader loader = new CapabilityLoader(this, getClassLoader());
    private final Map<Class<?>, MenuFactory> entityMenuFactories = new HashMap<>();
    private EasConfig config;
    private MenuFactory spawnMenuFactory;
    private MenuFactory colorPickerFactory;
    private MessageManager messageManager;
    private PropertyTypeRegistryImpl propertyTypeRegistry;
    private EntityElementProviderRegistryImpl entityElementProviderRegistry;
    private MenuSlotTypeRegistryImpl menuSlotTypeRegistry;
    private SessionManager sessionManager;
    private HistoryManager historyManager;
    private UpdateManager updateManager;
    private BukkitAudiences adventure;
    private PaperCommandManager<EasCommandSender> commandManager;
    private AnnotationParser<EasCommandSender> annotationParser;

    public static EasyArmorStandsPlugin getInstance() {
        return instance;
    }

    public Path getConfigFolder() {
        return getDataFolder().toPath();
    }

    @Override
    public void onLoad() {
        instance = this;
        EasyArmorStandsInitializer.initialize(this);
        loader.load();

        propertyTypeRegistry = new PropertyTypeRegistryImpl();
        new DefaultPropertyTypes(propertyTypeRegistry);

        ArmorStandElementType armorStandElementType = new ArmorStandElementType();
        entityElementProviderRegistry = new EntityElementProviderRegistryImpl();
        entityElementProviderRegistry.register(new ArmorStandElementProvider(armorStandElementType));
        entityElementProviderRegistry.register(new SimpleEntityElementProvider());

        menuSlotTypeRegistry = new MenuSlotTypeRegistryImpl();
        menuSlotTypeRegistry.register(new ArmorStandPartSlotType());
        menuSlotTypeRegistry.register(new ArmorStandPositionSlotType());
        menuSlotTypeRegistry.register(new ArmorStandSpawnSlotType(armorStandElementType));
        menuSlotTypeRegistry.register(new BackgroundSlotType());
        menuSlotTypeRegistry.register(new ColorAxisSlotType());
        menuSlotTypeRegistry.register(new ColorAxisChangeSlotType());
        menuSlotTypeRegistry.register(new ColorIndicatorSlotType());
        menuSlotTypeRegistry.register(new ColorPickerSlotType());
        menuSlotTypeRegistry.register(new ColorPresetSlotType());
        menuSlotTypeRegistry.register(new DestroySlotType());
        menuSlotTypeRegistry.register(new PropertySlotType());
    }

    @Override
    public void onEnable() {
        new Metrics(this, 17911);
        adventure = BukkitAudiences.create(this);

        loadConfig();

        messageManager = new MessageManager(this);
        messageManager.load(config);

        loadProperties();

        sessionManager = new SessionManager();
        historyManager = new HistoryManager();

        SessionListener sessionListener = new SessionListener(this, sessionManager);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(sessionListener, this);
        getServer().getPluginManager().registerEvents(historyManager, this);
        getServer().getPluginManager().registerEvents(new EntityElementListener(), this);
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
                loadAddon("me.m56738.easyarmorstands.worldguard.v7.WorldGuardAddon");
            } else if (hasClass("com.sk89q.worldguard.bukkit.WGBukkit")) {
                loadAddon("me.m56738.easyarmorstands.worldguard.v6.WorldGuardAddon");
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
        } else {
            menuSlotTypeRegistry().register(new FallbackSlotType(Key.key("easyarmorstands:headdatabase")));
        }

        if (hasClass("com.bergerkiller.bukkit.tc.attachments.ui.models.listing.DialogResult")) {
            getLogger().info("Enabling TrainCarts integration");
            loadAddon("me.m56738.easyarmorstands.traincarts.TrainCartsAddon");
        } else {
            menuSlotTypeRegistry().register(new FallbackSlotType(Key.key("easyarmorstands:traincarts/model_browser")));
        }

        if (hasClass("org.bukkit.entity.ItemDisplay")) {
            loadAddon("me.m56738.easyarmorstands.display.DisplayAddon");
        }

        if (hasClass("org.bukkit.event.entity.EntityPlaceEvent")) {
            try {
                EntityPlaceAdapter.enable(this, sessionListener);
            } catch (ReflectiveOperationException e) {
                getLogger().log(Level.WARNING, "Failed to listen to entity place event", e);
            }
        }

        loadUpdateChecker();
        loadMenuTemplates();
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
            Class.forName(name).getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException e) {
            getLogger().log(Level.SEVERE, "Failed to enable addon " + name, e.getCause());
        } catch (ReflectiveOperationException e) {
            getLogger().log(Level.SEVERE, "Failed to instantiate addon " + name, e);
        }
    }

    public Callable<BufferedReader> getConfigSource(String name) {
        return () -> {
            InputStream resource = getResource(name);
            if (resource == null) {
                throw new FileNotFoundException(name);
            }
            return new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8));
        };
    }

    @Override
    public void onDisable() {
        if (sessionManager != null) {
            sessionManager.stopAllSessions();
        }
    }

    public void reload() {
        loadConfig();
        loadProperties();
        messageManager.load(config);
        loadMenuTemplates();
    }

    private void loadConfig() {
        loadConfig("config.yml", c -> config = c.get(EasConfig.class));
    }

    private void loadConfig(String name, Loader loader) {
        try {
            loader.load(getConfig(name));
            return;
        } catch (ConfigurateException e) {
            getLogger().severe("Failed to load " + name + ": " + e.getMessage());
        }

        try {
            loader.load(getDefaultConfig(name));
        } catch (ConfigurateException e) {
            getLogger().log(Level.SEVERE, "Failed to load default " + name, e);
        }
    }

    private void loadProperties() {
        loadConfig("properties.yml", propertyTypeRegistry::load);
    }

    private void loadUpdateChecker() {
        if (getDescription().getVersion().endsWith("-SNAPSHOT")) {
            return;
        }
        if (config.updateCheck) {
            if (updateManager == null) {
                updateManager = new UpdateManager(this, adventure, "easyarmorstands.update.notify", 108349);
            }
        } else {
            if (updateManager != null) {
                updateManager.unregister();
                updateManager = null;
            }
        }
    }

    private void loadMenuTemplates() {
        spawnMenuFactory = loadMenuTemplate("spawn");
        colorPickerFactory = loadMenuTemplate("color_picker");
        MenuFactory defaultEntityMenuFactory = loadMenuTemplate("entity/default");
        MenuFactory livingEntityMenuFactory = loadMenuTemplate("entity/living");
        for (EntityType type : EntityType.values()) {
            MenuFactory factory = loadMenuTemplate("entity/type/" + type.name().toLowerCase(Locale.ROOT));
            if (factory == null) {
                if (type.isAlive()) {
                    factory = livingEntityMenuFactory;
                } else {
                    factory = defaultEntityMenuFactory;
                }
            }
            entityMenuFactories.put(type.getEntityClass(), factory);
        }
    }

    private MenuFactory loadMenuTemplate(String name) {
        try {
            return loadMenuTemplate(name, false);
        } catch (ConfigurateException e) {
            getLogger().severe("Failed to load menu \"" + name + "\": " + e.getMessage());
        }

        try {
            return loadMenuTemplate(name, true);
        } catch (ConfigurateException e) {
            getLogger().log(Level.SEVERE, "Failed to load default menu \"" + name + "\"", e);
        }

        return null;
    }

    private MenuFactory loadMenuTemplate(String name, boolean fallback) throws ConfigurateException {
        return loadMenuTemplate(name, new LinkedHashSet<>(), fallback).get(MenuFactory.class);
    }

    private CommentedConfigurationNode loadMenuTemplate(String name, Set<String> seen, boolean fallback) throws ConfigurateException {
        if (!seen.add(name)) {
            String description = Stream.concat(seen.stream(), Stream.of(name))
                    .collect(Collectors.joining(", ", "[", "]"));
            throw new SerializationException("Cycle detected: " + description);
        }

        String configName = "menu/" + name + ".yml";
        CommentedConfigurationNode node = fallback ? getDefaultConfig(configName) : getConfig(configName);
        List<String> parents = node.node("parent").getList(String.class);
        if (parents != null) {
            for (String parent : parents) {
                node.mergeFrom(loadMenuTemplate(parent, new LinkedHashSet<>(seen), fallback));
            }
        }
        return node;
    }

    public CommentedConfigurationNode getConfig(String name) throws ConfigurateException {
        YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                .defaultOptions(o -> o.serializers(b -> b.registerAll(EasSerializers.serializers())))
                .path(getConfigFolder().resolve(name))
                .build();

        CommentedConfigurationNode node = loader.load();
        node.mergeFrom(getDefaultConfig(name));
        return node;
    }

    public CommentedConfigurationNode getDefaultConfig(String name) throws ConfigurateException {
        YamlConfigurationLoader defaultLoader = YamlConfigurationLoader.builder()
                .defaultOptions(o -> o.serializers(b -> b.registerAll(EasSerializers.serializers())))
                .source(getConfigSource(name))
                .build();

        return defaultLoader.load();
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

    public ItemStack createTool(Locale locale) {
        ItemStack item = config.tool.render(locale);
        ToolCapability toolCapability = getCapability(ToolCapability.class);
        if (toolCapability != null) {
            ItemMeta meta = item.getItemMeta();
            toolCapability.configureTool(meta);
            item.setItemMeta(meta);
        }
        return item;
    }

    public boolean isTool(ItemStack item) {
        if (item == null) {
            return false;
        }
        ToolCapability toolCapability = EasyArmorStandsPlugin.getInstance().getCapability(ToolCapability.class);
        if (toolCapability != null) {
            return toolCapability.isTool(item);
        }

        // Tool capability is not supported
        // Match any item with the right material and any customized display name
        if (!Objects.equals(config.tool.getType(), item.getType())) {
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

    public Menu createSpawnMenu(Player player) {
        return spawnMenuFactory.createMenu(new SimpleMenuContext(new EasPlayer(player)));
    }

    public Menu createColorPicker(Player player, ColorPickerContextImpl context) {
        Menu menu = colorPickerFactory.createMenu(new ColorPickerMenuContext(new EasPlayer(player), context));
        context.subscribe(() -> menu.updateItems(slot -> slot instanceof ColorSlot));
        return menu;
    }

    public void openEntityMenu(Player player, Session session, EntityElement<?> element) {
        MenuFactory factory = entityMenuFactories.get(element.getType().getEntityType());
        if (factory == null) {
            return;
        }
        Menu menu = factory.createMenu(new ElementMenuContext(new EasPlayer(player), session, element));
        player.openInventory(menu.getInventory());
    }

    @Override
    public @NotNull EntityElementProviderRegistryImpl entityElementProviderRegistry() {
        return entityElementProviderRegistry;
    }

    @Override
    public @NotNull MenuSlotTypeRegistry menuSlotTypeRegistry() {
        return menuSlotTypeRegistry;
    }

    @Override
    public @NotNull PropertyTypeRegistry propertyTypeRegistry() {
        return propertyTypeRegistry;
    }

    private interface Loader {
        void load(CommentedConfigurationNode config) throws SerializationException;
    }
}
