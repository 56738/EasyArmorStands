package me.m56738.easyarmorstands;

import com.mojang.brigadier.arguments.StringArgumentType;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.adapter.EntityPlaceAdapter;
import me.m56738.easyarmorstands.addon.AddonManager;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.EasyArmorStandsInitializer;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementSpawnRequest;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.element.ElementTypeRegistry;
import me.m56738.easyarmorstands.api.element.EntityElement;
import me.m56738.easyarmorstands.api.element.EntityElementReference;
import me.m56738.easyarmorstands.api.element.EntityElementType;
import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuFactory;
import me.m56738.easyarmorstands.api.menu.MenuProvider;
import me.m56738.easyarmorstands.api.menu.MenuSlotTypeRegistry;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import me.m56738.easyarmorstands.api.region.RegionPrivilegeManager;
import me.m56738.easyarmorstands.clipboard.Clipboard;
import me.m56738.easyarmorstands.clipboard.ClipboardListener;
import me.m56738.easyarmorstands.clipboard.ClipboardManager;
import me.m56738.easyarmorstands.color.ColorAxisChangeSlotType;
import me.m56738.easyarmorstands.color.ColorAxisSlotType;
import me.m56738.easyarmorstands.color.ColorIndicatorSlotType;
import me.m56738.easyarmorstands.color.ColorPresetSlotType;
import me.m56738.easyarmorstands.color.ColorSlot;
import me.m56738.easyarmorstands.command.ComponentSuggestionMapper;
import me.m56738.easyarmorstands.command.parser.BlockDataArgumentParser;
import me.m56738.easyarmorstands.command.processor.ClipboardInjector;
import me.m56738.easyarmorstands.command.processor.ClipboardProcessor;
import me.m56738.easyarmorstands.command.processor.ElementInjector;
import me.m56738.easyarmorstands.command.processor.ElementProcessor;
import me.m56738.easyarmorstands.command.processor.ElementSelectionInjector;
import me.m56738.easyarmorstands.command.processor.ElementSelectionProcessor;
import me.m56738.easyarmorstands.command.processor.GroupProcessor;
import me.m56738.easyarmorstands.command.processor.SessionInjector;
import me.m56738.easyarmorstands.command.processor.SessionProcessor;
import me.m56738.easyarmorstands.command.processor.ValueNodeInjector;
import me.m56738.easyarmorstands.command.requirement.CommandRequirementPostProcessor;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.command.util.ElementSelection;
import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.config.serializer.EasSerializers;
import me.m56738.easyarmorstands.config.version.Transformations;
import me.m56738.easyarmorstands.config.version.game.GameVersionTransformation;
import me.m56738.easyarmorstands.editor.node.ValueNode;
import me.m56738.easyarmorstands.element.ArmorStandElementProvider;
import me.m56738.easyarmorstands.element.ArmorStandElementType;
import me.m56738.easyarmorstands.element.DisplayElementProvider;
import me.m56738.easyarmorstands.element.DisplayElementType;
import me.m56738.easyarmorstands.element.ElementSpawnRequestImpl;
import me.m56738.easyarmorstands.element.ElementTypeRegistryImpl;
import me.m56738.easyarmorstands.element.EntityElementListener;
import me.m56738.easyarmorstands.element.EntityElementProviderRegistryImpl;
import me.m56738.easyarmorstands.element.EntityElementReferenceImpl;
import me.m56738.easyarmorstands.element.InteractionElementProvider;
import me.m56738.easyarmorstands.element.InteractionElementType;
import me.m56738.easyarmorstands.element.MannequinElementProvider;
import me.m56738.easyarmorstands.element.MannequinElementType;
import me.m56738.easyarmorstands.element.SimpleEntityElementProvider;
import me.m56738.easyarmorstands.element.TextDisplayElementType;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.history.HistoryManager;
import me.m56738.easyarmorstands.menu.ColorPickerMenuContext;
import me.m56738.easyarmorstands.menu.ColorPicketContextWrapper;
import me.m56738.easyarmorstands.menu.ElementMenuContext;
import me.m56738.easyarmorstands.menu.MenuListener;
import me.m56738.easyarmorstands.menu.MenuProviderImpl;
import me.m56738.easyarmorstands.menu.MenuSlotTypeRegistryImpl;
import me.m56738.easyarmorstands.menu.SimpleMenuContext;
import me.m56738.easyarmorstands.menu.slot.ArmorStandPartSlotType;
import me.m56738.easyarmorstands.menu.slot.ArmorStandPositionSlotType;
import me.m56738.easyarmorstands.menu.slot.ArmorStandSpawnSlotType;
import me.m56738.easyarmorstands.menu.slot.BackgroundSlotType;
import me.m56738.easyarmorstands.menu.slot.ColorPickerSlotType;
import me.m56738.easyarmorstands.menu.slot.DestroySlotType;
import me.m56738.easyarmorstands.menu.slot.DisplayBoxSlotType;
import me.m56738.easyarmorstands.menu.slot.DisplaySpawnSlotType;
import me.m56738.easyarmorstands.menu.slot.EntityCopySlotType;
import me.m56738.easyarmorstands.menu.slot.FallbackSlotType;
import me.m56738.easyarmorstands.menu.slot.InteractionSpawnSlotType;
import me.m56738.easyarmorstands.menu.slot.MannequinSpawnSlotType;
import me.m56738.easyarmorstands.menu.slot.PropertySlotType;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.message.MessageManager;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.property.type.DefaultPropertyTypes;
import me.m56738.easyarmorstands.property.type.PropertyTypeRegistryImpl;
import me.m56738.easyarmorstands.region.RegionListenerManager;
import me.m56738.easyarmorstands.session.SessionImpl;
import me.m56738.easyarmorstands.session.SessionListener;
import me.m56738.easyarmorstands.session.SessionManagerImpl;
import me.m56738.easyarmorstands.update.UpdateManager;
import me.m56738.easyarmorstands.util.MainThreadExecutor;
import me.m56738.easyarmorstands.util.ReflectionUtil;
import me.m56738.gizmo.bukkit.api.BukkitGizmos;
import net.kyori.adventure.key.Key;
import org.bstats.bukkit.Metrics;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.brigadier.CloudBrigadierManager;
import org.incendo.cloud.exception.InvalidCommandSenderException;
import org.incendo.cloud.injection.ParameterInjector;
import org.incendo.cloud.minecraft.extras.MinecraftExceptionHandler;
import org.incendo.cloud.minecraft.extras.MinecraftHelp;
import org.incendo.cloud.minecraft.extras.parser.TextColorParser;
import org.incendo.cloud.paper.PaperCommandManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.loader.HeaderMode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.util.MapFactories;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private final Map<Class<?>, MenuFactory> entityMenuFactories = new HashMap<>();
    private final NamespacedKey toolKey = new NamespacedKey(this, "tool");
    private final PaperCommandManager.Bootstrapped<EasCommandSender> commandManager;
    private EasConfig config;
    private MenuFactory spawnMenuFactory;
    private MenuFactory colorPickerFactory;
    private MessageManager messageManager;
    private AddonManager addonManager;
    private RegionListenerManager regionPrivilegeManager;
    private ElementTypeRegistryImpl elementTypeRegistry;
    private PropertyTypeRegistryImpl propertyTypeRegistry;
    private EntityElementProviderRegistryImpl entityElementProviderRegistry;
    private MenuSlotTypeRegistryImpl menuSlotTypeRegistry;
    private MenuProviderImpl menuProvider;
    private SessionManagerImpl sessionManager;
    private HistoryManager historyManager;
    private ClipboardManager clipboardManager;
    private UpdateManager updateManager;
    private BukkitGizmos gizmos;

    public EasyArmorStandsPlugin(
            MainThreadExecutor executor,
            PaperCommandManager.Bootstrapped<EasCommandSender> commandManager) {
        executor.setPlugin(this);
        this.commandManager = commandManager;
    }

    public static EasyArmorStandsPlugin getInstance() {
        return instance;
    }

    public Path getConfigFolder() {
        return getDataFolder().toPath();
    }

    @Override
    public void onLoad() {
        Permissions.registerAll();

        instance = this;
        EasyArmorStandsInitializer.initialize(this);

        elementTypeRegistry = new ElementTypeRegistryImpl();
        propertyTypeRegistry = new PropertyTypeRegistryImpl();
        entityElementProviderRegistry = new EntityElementProviderRegistryImpl();

        new DefaultPropertyTypes(propertyTypeRegistry);

        entityElementProviderRegistry.register(new SimpleEntityElementProvider());

        ArmorStandElementType armorStandElementType = new ArmorStandElementType();
        elementTypeRegistry.register(EasyArmorStands.key("armor_stand"), armorStandElementType);
        entityElementProviderRegistry.register(new ArmorStandElementProvider(armorStandElementType));

        MannequinElementType mannequinElementType = new MannequinElementType();
        elementTypeRegistry.register(EasyArmorStands.key("mannequin"), mannequinElementType);
        entityElementProviderRegistry.register(new MannequinElementProvider(mannequinElementType));

        DisplayElementType<ItemDisplay> itemDisplayType = new DisplayElementType<>(EntityType.ITEM_DISPLAY, ItemDisplay.class);
        elementTypeRegistry.register(EasyArmorStands.key("item_display"), itemDisplayType);
        entityElementProviderRegistry.register(new DisplayElementProvider<>(itemDisplayType));

        DisplayElementType<BlockDisplay> blockDisplayType = new DisplayElementType<>(EntityType.BLOCK_DISPLAY, BlockDisplay.class);
        elementTypeRegistry.register(EasyArmorStands.key("block_display"), blockDisplayType);
        entityElementProviderRegistry.register(new DisplayElementProvider<>(blockDisplayType));

        DisplayElementType<TextDisplay> textDisplayType = new TextDisplayElementType();
        elementTypeRegistry.register(EasyArmorStands.key("text_display"), textDisplayType);
        entityElementProviderRegistry.register(new DisplayElementProvider<>(textDisplayType));

        InteractionElementType interactionType = new InteractionElementType();
        elementTypeRegistry.register(EasyArmorStands.key("interaction"), interactionType);
        entityElementProviderRegistry.register(new InteractionElementProvider(interactionType));

        menuSlotTypeRegistry = new MenuSlotTypeRegistryImpl();
        menuSlotTypeRegistry.register(new EntityCopySlotType());
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
        menuSlotTypeRegistry.register(new DisplayBoxSlotType());
        menuSlotTypeRegistry.register(new DisplaySpawnSlotType(Key.key("easyarmorstands", "spawn/block_display"), blockDisplayType));
        menuSlotTypeRegistry.register(new DisplaySpawnSlotType(Key.key("easyarmorstands", "spawn/item_display"), itemDisplayType));
        menuSlotTypeRegistry.register(new DisplaySpawnSlotType(Key.key("easyarmorstands", "spawn/text_display"), textDisplayType));
        menuSlotTypeRegistry.register(new InteractionSpawnSlotType(interactionType));
        menuSlotTypeRegistry.register(new MannequinSpawnSlotType(mannequinElementType));
        menuSlotTypeRegistry.register(new PropertySlotType());
        menuSlotTypeRegistry.register(new FallbackSlotType(Key.key("easyarmorstands:traincarts/model_browser")));
        menuSlotTypeRegistry.register(new FallbackSlotType(Key.key("easyarmorstands:headdatabase")));

        menuProvider = new MenuProviderImpl();

        loadConfig();
        messageManager = new MessageManager(this);
        messageManager.load(config);

        regionPrivilegeManager = new RegionListenerManager();

        addonManager = new AddonManager(getLogger());
        addonManager.load(getClassLoader());
    }

    @Override
    public void onEnable() {
        new Metrics(this, 17911);
        gizmos = BukkitGizmos.create(this);

        loadProperties();

        sessionManager = new SessionManagerImpl();
        historyManager = new HistoryManager();
        clipboardManager = new ClipboardManager();

        SessionListener sessionListener = new SessionListener(this, sessionManager);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(sessionListener, this);
        getServer().getPluginManager().registerEvents(historyManager, this);
        getServer().getPluginManager().registerEvents(new ClipboardListener(clipboardManager), this);
        getServer().getPluginManager().registerEvents(new EntityElementListener(), this);
        getServer().getScheduler().runTaskTimer(this, sessionManager::update, 0, 1);
        getServer().getScheduler().runTaskTimer(this, sessionListener::update, 0, 1);

        commandManager.onEnable();

        MinecraftExceptionHandler.<EasCommandSender>createNative()
                .defaultArgumentParsingHandler()
                .defaultInvalidSyntaxHandler()
                .defaultNoPermissionHandler()
                .defaultCommandExecutionHandler()
                .defaultInvalidSenderHandler()
                .handler(InvalidCommandSenderException.class,
                        (_, _) -> Message.error("easyarmorstands.error.not-a-player"))
                .registerTo(commandManager);

        MinecraftHelp<EasCommandSender> help = MinecraftHelp.createNative("/eas help", commandManager);

        commandManager.parameterInjectorRegistry()
                .registerInjector(ValueNode.class, new ValueNodeInjector())
                .registerInjector(SessionImpl.class, new SessionInjector())
                .registerInjector(Clipboard.class, new ClipboardInjector())
                .registerInjector(Element.class, new ElementInjector())
                .registerInjector(ElementSelection.class, new ElementSelectionInjector())
                .registerInjector(SessionListener.class, ParameterInjector.constantInjector(sessionListener))
                .registerInjector(ElementTypeRegistry.class, ParameterInjector.constantInjector(elementTypeRegistry))
                .registerInjector(new TypeToken<>() {
                }, ParameterInjector.constantInjector(help));

        if (commandManager.hasBrigadierManager()) {
            CloudBrigadierManager<EasCommandSender, ?> brigadierManager = commandManager.brigadierManager();
            try {
                BlockDataArgumentParser.registerBrigadier(brigadierManager);
            } catch (Throwable e) {
                getLogger().warning("Failed to register Brigadier mappings for block data arguments");
            }
            brigadierManager.registerMapping(new TypeToken<TextColorParser<EasCommandSender>>() {
            }, builder -> builder.cloudSuggestions().toConstant(StringArgumentType.greedyString()));
        }

        commandManager.registerCommandPreProcessor(new ElementSelectionProcessor());
        commandManager.registerCommandPreProcessor(new GroupProcessor());
        commandManager.registerCommandPreProcessor(new ElementProcessor());
        commandManager.registerCommandPreProcessor(new SessionProcessor());
        commandManager.registerCommandPreProcessor(new ClipboardProcessor());

        commandManager.registerCommandPostProcessor(new CommandRequirementPostProcessor());

        commandManager.appendSuggestionMapper(new ComponentSuggestionMapper());

        if (ReflectionUtil.hasClass("org.bukkit.event.entity.EntityPlaceEvent")) {
            try {
                EntityPlaceAdapter.enable(this, sessionListener);
            } catch (ReflectiveOperationException e) {
                getLogger().log(Level.WARNING, "Failed to listen to entity place event", e);
            }
        }

        addonManager.enable();

        try {
            loadUpdateChecker();
        } catch (Exception e) {
            getLogger().log(Level.WARNING, "Failed to initialize update checks");
        }

        loadMenuTemplates();
    }

    private Callable<BufferedReader> getDefaultConfigSource(String name) {
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
        if (addonManager != null) {
            addonManager.disable();
        }
        if (regionPrivilegeManager != null) {
            regionPrivilegeManager.unregisterAll();
        }
        Permissions.unregisterAll();
        if (gizmos != null) {
            gizmos.close();
        }
    }

    public void reload() {
        loadConfig();
        loadProperties();
        messageManager.load(config);
        loadMenuTemplates();
        addonManager.reload();
    }

    private void loadConfig() {
        loadConfig("config.yml", new ConfigProcessor() {
            @Override
            public void process(CommentedConfigurationNode node) throws ConfigurateException {
                GameVersionTransformation.config().apply(node);
                Transformations.config().apply(node);
            }

            @Override
            public void apply(CommentedConfigurationNode node) throws ConfigurateException {
                config = node.get(EasConfig.class);
            }
        });
    }

    private void loadProperties() {
        loadConfig("properties.yml", new ConfigProcessor() {
            @Override
            public void process(CommentedConfigurationNode node) throws ConfigurateException {
                GameVersionTransformation.properties().apply(node);
                Transformations.properties().apply(node);
            }

            @Override
            public void apply(CommentedConfigurationNode node) throws ConfigurateException {
                propertyTypeRegistry.load(node);
            }
        });
    }

    private void loadConfig(String name, ConfigProcessor configProcessor) {
        try {
            loadMergedConfig(name, configProcessor);
            return;
        } catch (ConfigurateException e) {
            getLogger().severe("Failed to load " + name + ": " + e.getMessage());
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Failed to load " + name, e);
        }

        try {
            loadDefaultConfig(name, configProcessor);
        } catch (ConfigurateException e) {
            getLogger().log(Level.SEVERE, "Failed to load default " + name, e);
        }
    }

    private void loadUpdateChecker() {
        if (getPluginMeta().getVersion().endsWith("-SNAPSHOT")) {
            return;
        }
        if (config.updateCheck.enabled) {
            if (updateManager == null) {
                updateManager = new UpdateManager(this, Permissions.UPDATE_NOTIFY);
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

    public MenuFactory loadMenuTemplate(String name) {
        try {
            return loadMenuTemplate(name, false);
        } catch (ConfigurateException e) {
            getLogger().log(Level.SEVERE, "Failed to load menu \"" + name + "\": " + e.getMessage());
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Failed to load menu \"" + name + "\"", e);
        }

        try {
            return loadMenuTemplate(name, true);
        } catch (ConfigurateException e) {
            getLogger().log(Level.SEVERE, "Failed to load default menu \"" + name + "\": " + e.getMessage());
        } catch (Exception e) {
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

        ConfigProcessor processor = new ConfigProcessor() {
            @Override
            public void process(CommentedConfigurationNode node) throws ConfigurateException {
                GameVersionTransformation.menu().apply(node);
            }

            @Override
            public void apply(CommentedConfigurationNode node) {
            }
        };

        String configName = "menu/" + name + ".yml";
        CommentedConfigurationNode node = fallback
                ? loadDefaultConfig(configName, processor)
                : loadMergedConfig(configName, processor);
        CommentedConfigurationNode parentsNode = node.node("parent");
        if (!parentsNode.virtual()) {
            List<String> parents = parentsNode.getList(String.class);
            if (parents != null) {
                for (String parent : parents) {
                    node.mergeFrom(loadMenuTemplate(parent, new LinkedHashSet<>(seen), fallback));
                }
            }
        }
        return node;
    }

    private CommentedConfigurationNode loadMergedConfig(String name, ConfigProcessor configProcessor) throws ConfigurateException {
        CommentedConfigurationNode defaultNode = loadDefaultConfig(name, new ConfigProcessor() {
            @Override
            public void process(CommentedConfigurationNode node) throws ConfigurateException {
                configProcessor.process(node);
            }

            @Override
            public void apply(CommentedConfigurationNode node) {
            }
        });

        YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                .nodeStyle(NodeStyle.BLOCK)
                .indent(2)
                .headerMode(HeaderMode.PRESET)
                .defaultOptions(o -> o
                        .serializers(b -> b.registerAll(EasSerializers.serializers()))
                        .mapFactory(MapFactories.sortedNatural())
                        .header(defaultNode.options().header())
                        .shouldCopyDefaults(false))
                .path(getConfigFolder().resolve(name))
                .build();

        CommentedConfigurationNode node = loader.load();

        if (!node.empty()) {
            configProcessor.process(node);
        }

        node.mergeFrom(defaultNode);

        configProcessor.apply(node);

        if (!node.empty()) {
            loader.save(node);
        }

        return node;
    }

    private CommentedConfigurationNode loadDefaultConfig(String name, ConfigProcessor configProcessor) throws ConfigurateException {
        YamlConfigurationLoader defaultLoader = YamlConfigurationLoader.builder()
                .defaultOptions(o -> o.serializers(b -> b.registerAll(EasSerializers.serializers())))
                .source(getDefaultConfigSource(name))
                .build();

        CommentedConfigurationNode node = defaultLoader.load();

        if (!node.empty()) {
            configProcessor.process(node);
        }
        configProcessor.apply(node);

        return node;
    }

    public History getHistory(Player player) {
        return historyManager.getHistory(player);
    }

    public Clipboard getClipboard(Player player) {
        return clipboardManager.getClipboard(player);
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    public BukkitGizmos getGizmos() {
        return gizmos;
    }

    public CommandManager<EasCommandSender> getCommandManager() {
        return commandManager;
    }

    public ItemStack createTool(Locale locale) {
        ItemStack item = config.editor.tool.render(locale);
        item.editPersistentDataContainer(pdc ->
                pdc.set(toolKey, PersistentDataType.BYTE, (byte) 1));
        return item;
    }

    public boolean isTool(@Nullable ItemStack item) {
        if (item == null) {
            return false;
        }
        return item.getPersistentDataContainer().has(toolKey, PersistentDataType.BYTE);
    }

    public EasConfig getConfiguration() {
        return config;
    }

    public Menu createSpawnMenu(Player player) {
        return spawnMenuFactory.createMenu(new SimpleMenuContext(new EasPlayer(player)));
    }

    public Menu createColorPicker(Player player, ColorPickerContext context) {
        ColorPicketContextWrapper contextWrapper = new ColorPicketContextWrapper(context);
        ColorPickerMenuContext menuContext = new ColorPickerMenuContext(new EasPlayer(player), contextWrapper);
        Menu menu = colorPickerFactory.createMenu(menuContext);
        contextWrapper.subscribe(() -> menu.updateItems(slot -> slot instanceof ColorSlot));
        return menu;
    }

    public void openMenu(Player player, Session session, MenuFactory factory, Element element) {
        Menu menu = factory.createMenu(new ElementMenuContext(new EasPlayer(player), session, element));
        player.openInventory(menu.getInventory());
    }

    public void openEntityMenu(Player player, Session session, EntityElement<?> element) {
        MenuFactory factory = entityMenuFactories.get(element.getType().getEntityClass());
        if (factory != null) {
            openMenu(player, session, factory, element);
        }
    }

    @Override
    public @NotNull EntityElementProviderRegistryImpl entityElementProviderRegistry() {
        return entityElementProviderRegistry;
    }

    public @NotNull MenuSlotTypeRegistry menuSlotTypeRegistry() {
        return Objects.requireNonNull(menuSlotTypeRegistry);
    }

    public @NotNull MenuProvider menuProvider() {
        return Objects.requireNonNull(menuProvider);
    }

    @Override
    public @NotNull SessionManagerImpl sessionManager() {
        return Objects.requireNonNull(sessionManager);
    }

    @Override
    public @NotNull ElementSpawnRequest elementSpawnRequest(ElementType type) {
        return new ElementSpawnRequestImpl(type);
    }

    @Override
    public @NotNull ElementTypeRegistry elementTypeRegistry() {
        return elementTypeRegistry;
    }

    @Override
    public @NotNull PropertyTypeRegistry propertyTypeRegistry() {
        return Objects.requireNonNull(propertyTypeRegistry);
    }

    @Override
    public @NotNull RegionPrivilegeManager regionPrivilegeManager() {
        return Objects.requireNonNull(regionPrivilegeManager);
    }

    @Override
    public @NotNull <E extends Entity> EntityElementReference<E> createReference(EntityElementType<E> type, E entity) {
        return new EntityElementReferenceImpl<>(type, entity);
    }

    private interface ConfigProcessor {
        void process(CommentedConfigurationNode node) throws ConfigurateException;

        void apply(CommentedConfigurationNode node) throws ConfigurateException;
    }
}
