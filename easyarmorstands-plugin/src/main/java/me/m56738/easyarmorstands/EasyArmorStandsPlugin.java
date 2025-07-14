package me.m56738.easyarmorstands;

import com.mojang.brigadier.arguments.StringArgumentType;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import me.m56738.easyarmorstands.adapter.EntityPlaceAdapter;
import me.m56738.easyarmorstands.addon.AddonManager;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.EasyArmorStandsInitializer;
import me.m56738.easyarmorstands.api.context.ChangeContextFactory;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementSpawnRequest;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.event.element.ElementMenuLayoutEvent;
import me.m56738.easyarmorstands.api.event.player.PlayerCreateElementEvent;
import me.m56738.easyarmorstands.api.event.player.PlayerDestroyElementEvent;
import me.m56738.easyarmorstands.api.event.player.PlayerDiscoverElementEvent;
import me.m56738.easyarmorstands.api.event.player.PlayerEditPropertyEvent;
import me.m56738.easyarmorstands.api.event.player.PlayerSelectElementEvent;
import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuFactory;
import me.m56738.easyarmorstands.api.menu.MenuProvider;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
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
import me.m56738.easyarmorstands.command.ClipboardCommands;
import me.m56738.easyarmorstands.command.GlobalCommands;
import me.m56738.easyarmorstands.command.HistoryCommands;
import me.m56738.easyarmorstands.command.PropertyCommands;
import me.m56738.easyarmorstands.command.SessionCommands;
import me.m56738.easyarmorstands.command.annotation.PropertyPermission;
import me.m56738.easyarmorstands.command.parser.NodeValueArgumentParser;
import me.m56738.easyarmorstands.command.processor.ClipboardInjector;
import me.m56738.easyarmorstands.command.processor.ClipboardProcessor;
import me.m56738.easyarmorstands.command.processor.ElementInjector;
import me.m56738.easyarmorstands.command.processor.ElementProcessor;
import me.m56738.easyarmorstands.command.processor.ElementSelectionInjector;
import me.m56738.easyarmorstands.command.processor.ElementSelectionProcessor;
import me.m56738.easyarmorstands.command.processor.GroupProcessor;
import me.m56738.easyarmorstands.command.processor.PropertyPermissionBuilderModifier;
import me.m56738.easyarmorstands.command.processor.SessionInjector;
import me.m56738.easyarmorstands.command.processor.SessionProcessor;
import me.m56738.easyarmorstands.command.processor.ValueNodeInjector;
import me.m56738.easyarmorstands.command.requirement.CommandRequirementBuilderModifier;
import me.m56738.easyarmorstands.command.requirement.CommandRequirementPostProcessor;
import me.m56738.easyarmorstands.command.requirement.ElementRequirement;
import me.m56738.easyarmorstands.command.requirement.ElementSelectionRequirement;
import me.m56738.easyarmorstands.command.requirement.RequireElement;
import me.m56738.easyarmorstands.command.requirement.RequireElementSelection;
import me.m56738.easyarmorstands.command.requirement.RequireSession;
import me.m56738.easyarmorstands.command.requirement.SessionRequirement;
import me.m56738.easyarmorstands.command.util.ElementSelection;
import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.config.serializer.EasSerializers;
import me.m56738.easyarmorstands.config.version.Transformations;
import me.m56738.easyarmorstands.config.version.game.GameVersionTransformation;
import me.m56738.easyarmorstands.display.DisplayListener;
import me.m56738.easyarmorstands.display.command.BlockDataArgumentParser;
import me.m56738.easyarmorstands.display.command.DisplayCommands;
import me.m56738.easyarmorstands.display.element.DisplayElementProvider;
import me.m56738.easyarmorstands.display.element.DisplayElementType;
import me.m56738.easyarmorstands.display.element.InteractionElementProvider;
import me.m56738.easyarmorstands.display.element.InteractionElementType;
import me.m56738.easyarmorstands.display.element.TextDisplayElementType;
import me.m56738.easyarmorstands.display.menu.DisplayBoxSlotType;
import me.m56738.easyarmorstands.display.menu.DisplaySpawnSlotType;
import me.m56738.easyarmorstands.display.menu.InteractionSpawnSlotType;
import me.m56738.easyarmorstands.display.property.DefaultDisplayPropertyTypes;
import me.m56738.easyarmorstands.editor.node.ValueNode;
import me.m56738.easyarmorstands.element.ArmorStandElementProvider;
import me.m56738.easyarmorstands.element.ArmorStandElementType;
import me.m56738.easyarmorstands.element.ElementSpawnRequestImpl;
import me.m56738.easyarmorstands.element.EntityElementListener;
import me.m56738.easyarmorstands.element.EntityElementProviderRegistryImpl;
import me.m56738.easyarmorstands.element.SimpleEntityElementProvider;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.history.HistoryManager;
import me.m56738.easyarmorstands.lib.bstats.bukkit.Metrics;
import me.m56738.easyarmorstands.lib.cloud.annotations.AnnotationParser;
import me.m56738.easyarmorstands.lib.cloud.exception.InvalidCommandSenderException;
import me.m56738.easyarmorstands.lib.cloud.execution.ExecutionCoordinator;
import me.m56738.easyarmorstands.lib.cloud.minecraft.extras.MinecraftExceptionHandler;
import me.m56738.easyarmorstands.lib.cloud.minecraft.extras.RichDescription;
import me.m56738.easyarmorstands.lib.cloud.minecraft.extras.parser.TextColorParser;
import me.m56738.easyarmorstands.lib.cloud.paper.PaperCommandManager;
import me.m56738.easyarmorstands.lib.cloud.paper.util.sender.PaperSimpleSenderMapper;
import me.m56738.easyarmorstands.lib.cloud.paper.util.sender.Source;
import me.m56738.easyarmorstands.lib.configurate.CommentedConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.ConfigurateException;
import me.m56738.easyarmorstands.lib.configurate.loader.HeaderMode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.configurate.util.MapFactories;
import me.m56738.easyarmorstands.lib.configurate.yaml.NodeStyle;
import me.m56738.easyarmorstands.lib.configurate.yaml.YamlConfigurationLoader;
import me.m56738.easyarmorstands.lib.geantyref.TypeToken;
import me.m56738.easyarmorstands.lib.gizmo.bukkit.api.BukkitGizmos;
import me.m56738.easyarmorstands.menu.ColorPickerMenuContext;
import me.m56738.easyarmorstands.menu.ColorPicketContextWrapper;
import me.m56738.easyarmorstands.menu.ElementMenuContext;
import me.m56738.easyarmorstands.menu.MenuListener;
import me.m56738.easyarmorstands.menu.MenuProviderImpl;
import me.m56738.easyarmorstands.menu.MenuSlotTypeRegistry;
import me.m56738.easyarmorstands.menu.MenuSlotTypeRegistryImpl;
import me.m56738.easyarmorstands.menu.SimpleMenuContext;
import me.m56738.easyarmorstands.menu.layout.MenuBuilder;
import me.m56738.easyarmorstands.menu.layout.MenuBuilderFactory;
import me.m56738.easyarmorstands.menu.listener.ElementMenuListener;
import me.m56738.easyarmorstands.menu.slot.ArmorStandPositionSlotType;
import me.m56738.easyarmorstands.menu.slot.ArmorStandSpawnSlotType;
import me.m56738.easyarmorstands.menu.slot.BackgroundSlotType;
import me.m56738.easyarmorstands.menu.slot.ColorPickerSlotType;
import me.m56738.easyarmorstands.menu.slot.FallbackSlotType;
import me.m56738.easyarmorstands.menu.slot.PropertySlotType;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.message.MessageManager;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.property.context.PlayerChangeContextFactory;
import me.m56738.easyarmorstands.property.type.DefaultPropertyTypes;
import me.m56738.easyarmorstands.property.type.PropertyTypeRegistryImpl;
import me.m56738.easyarmorstands.region.RegionListenerManager;
import me.m56738.easyarmorstands.session.SessionImpl;
import me.m56738.easyarmorstands.session.SessionListener;
import me.m56738.easyarmorstands.session.SessionManagerImpl;
import me.m56738.easyarmorstands.update.UpdateManager;
import me.m56738.easyarmorstands.util.ReflectionUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EasyArmorStandsPlugin extends JavaPlugin implements EasyArmorStands {
    private static final NamespacedKey TOOL_KEY = new NamespacedKey("easyarmorstands", "tool");
    private static EasyArmorStandsPlugin instance;
    private EasConfig config;
    private MenuFactory spawnMenuFactory;
    private MenuFactory colorPickerFactory;
    private MessageManager messageManager;
    private AddonManager addonManager;
    private RegionListenerManager regionPrivilegeManager;
    private PropertyTypeRegistryImpl propertyTypeRegistry;
    private EntityElementProviderRegistryImpl entityElementProviderRegistry;
    private MenuSlotTypeRegistryImpl menuSlotTypeRegistry;
    private MenuProviderImpl menuProvider;
    private SessionManagerImpl sessionManager;
    private HistoryManager historyManager;
    private ClipboardManager clipboardManager;
    private UpdateManager updateManager;
    private BukkitGizmos gizmos;
    private PaperCommandManager<Source> commandManager;
    private AnnotationParser<Source> annotationParser;
    private DisplayElementType<ItemDisplay> itemDisplayType;

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

        propertyTypeRegistry = new PropertyTypeRegistryImpl();
        new DefaultPropertyTypes(propertyTypeRegistry);
        new DefaultDisplayPropertyTypes(propertyTypeRegistry);

        itemDisplayType = new DisplayElementType<>(EntityType.ITEM_DISPLAY, ItemDisplay.class);
        DisplayElementType<BlockDisplay> blockDisplayType = new DisplayElementType<>(EntityType.BLOCK_DISPLAY, BlockDisplay.class);
        TextDisplayElementType textDisplayType = new TextDisplayElementType();
        InteractionElementType interactionType = new InteractionElementType();

        ArmorStandElementType armorStandElementType = new ArmorStandElementType();
        entityElementProviderRegistry = new EntityElementProviderRegistryImpl();
        entityElementProviderRegistry.register(new ArmorStandElementProvider(armorStandElementType));
        entityElementProviderRegistry.register(new SimpleEntityElementProvider());
        entityElementProviderRegistry.register(new DisplayElementProvider<>(itemDisplayType));
        entityElementProviderRegistry.register(new DisplayElementProvider<>(blockDisplayType));
        entityElementProviderRegistry.register(new DisplayElementProvider<>(textDisplayType));
        entityElementProviderRegistry.register(new InteractionElementProvider(interactionType));

        menuSlotTypeRegistry = new MenuSlotTypeRegistryImpl();
        menuSlotTypeRegistry.register(new ArmorStandPositionSlotType());
        menuSlotTypeRegistry.register(new ArmorStandSpawnSlotType(armorStandElementType));
        menuSlotTypeRegistry.register(new BackgroundSlotType());
        menuSlotTypeRegistry.register(new ColorAxisSlotType());
        menuSlotTypeRegistry.register(new ColorAxisChangeSlotType());
        menuSlotTypeRegistry.register(new ColorIndicatorSlotType());
        menuSlotTypeRegistry.register(new ColorPickerSlotType());
        menuSlotTypeRegistry.register(new ColorPresetSlotType());
        menuSlotTypeRegistry.register(new PropertySlotType());
        menuSlotTypeRegistry.register(new DisplaySpawnSlotType(Key.key("easyarmorstands", "spawn/item_display"), itemDisplayType));
        menuSlotTypeRegistry.register(new DisplaySpawnSlotType(Key.key("easyarmorstands", "spawn/block_display"), blockDisplayType));
        menuSlotTypeRegistry.register(new DisplaySpawnSlotType(Key.key("easyarmorstands", "spawn/text_display"), textDisplayType));
        menuSlotTypeRegistry.register(new DisplayBoxSlotType());
        menuSlotTypeRegistry.register(new InteractionSpawnSlotType(interactionType));
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
        getServer().getPluginManager().registerEvents(new ElementMenuListener(), this);
        getServer().getPluginManager().registerEvents(new DisplayListener(), this);
        getServer().getScheduler().runTaskTimer(this, sessionManager::update, 0, 1);
        getServer().getScheduler().runTaskTimer(this, sessionListener::update, 0, 1);

        commandManager = createCommandManager();

        MinecraftExceptionHandler.create(Source::source)
                .defaultArgumentParsingHandler()
                .defaultInvalidSyntaxHandler()
                .defaultNoPermissionHandler()
                .defaultCommandExecutionHandler()
                .defaultInvalidSenderHandler()
                .handler(InvalidCommandSenderException.class,
                        (sender, e) -> Message.error("easyarmorstands.error.not-a-player"))
                .registerTo(commandManager);

        commandManager.parameterInjectorRegistry()
                .registerInjector(ValueNode.class, new ValueNodeInjector())
                .registerInjector(SessionImpl.class, new SessionInjector())
                .registerInjector(Clipboard.class, new ClipboardInjector())
                .registerInjector(Element.class, new ElementInjector())
                .registerInjector(ElementSelection.class, new ElementSelectionInjector());

        commandManager.parserRegistry().registerNamedParserSupplier("node_value",
                p -> new NodeValueArgumentParser<>());

        commandManager.parserRegistry().registerParserSupplier(TypeToken.get(TextColor.class),
                p -> new TextColorParser<>());
        commandManager.brigadierManager().registerMapping(new TypeToken<TextColorParser<Source>>() {
        }, builder -> builder.cloudSuggestions().toConstant(StringArgumentType.greedyString()));

        commandManager.parserRegistry().registerParserSupplier(TypeToken.get(BlockData.class),
                p -> new BlockDataArgumentParser<>());
        commandManager.brigadierManager().registerMapping(new TypeToken<BlockDataArgumentParser<Source>>() {
        }, builder -> builder.toConstant(ArgumentTypes.blockState()));

        commandManager.registerCommandPreProcessor(new ElementSelectionProcessor());
        commandManager.registerCommandPreProcessor(new GroupProcessor());
        commandManager.registerCommandPreProcessor(new ElementProcessor());
        commandManager.registerCommandPreProcessor(new SessionProcessor());
        commandManager.registerCommandPreProcessor(new ClipboardProcessor());

        commandManager.registerCommandPostProcessor(new CommandRequirementPostProcessor());

        annotationParser = new AnnotationParser<>(commandManager, Source.class);
        annotationParser.descriptionMapper(RichDescription::translatable);

        annotationParser.registerBuilderModifier(RequireSession.class, new CommandRequirementBuilderModifier<>(a -> new SessionRequirement()));
        annotationParser.registerBuilderModifier(RequireElement.class, new CommandRequirementBuilderModifier<>(a -> new ElementRequirement()));
        annotationParser.registerBuilderModifier(RequireElementSelection.class, new CommandRequirementBuilderModifier<>(a -> new ElementSelectionRequirement()));

        annotationParser.registerBuilderModifier(PropertyPermission.class, new PropertyPermissionBuilderModifier());
        annotationParser.parse(new GlobalCommands(commandManager, sessionListener));
        annotationParser.parse(new SessionCommands());
        annotationParser.parse(new HistoryCommands());
        annotationParser.parse(new ClipboardCommands());
        annotationParser.parse(new DisplayCommands());

        PropertyCommands.register(commandManager);

        if (ReflectionUtil.hasClass("org.bukkit.event.entity.EntityPlaceEvent")) {
            try {
                EntityPlaceAdapter.enable(this, sessionListener);
            } catch (ReflectiveOperationException e) {
                getLogger().log(Level.WARNING, "Failed to listen to entity place event", e);
            }
        }

        addonManager.enable();

        loadUpdateChecker();
        loadMenuTemplates();
    }

    private PaperCommandManager<Source> createCommandManager() {
        return PaperCommandManager.builder(PaperSimpleSenderMapper.simpleSenderMapper())
                .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
                .buildOnEnable(this);
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
                updateManager = new UpdateManager(this, Permissions.UPDATE_NOTIFY, 108349);
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

    public ItemStack createTool(Locale locale) {
        ItemStack item = config.editor.tool.render(locale);
        item.editMeta(meta -> meta.getPersistentDataContainer().set(TOOL_KEY, PersistentDataType.BYTE, (byte) 1));
        return item;
    }

    public boolean isTool(ItemStack item) {
        if (item == null) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }

        return meta.getPersistentDataContainer().has(TOOL_KEY);
    }

    public EasConfig getConfiguration() {
        return config;
    }

    public Menu createSpawnMenu(Player player) {
        return spawnMenuFactory.createMenu(new SimpleMenuContext(player));
    }

    public Menu createColorPicker(Player player, ColorPickerContext context) {
        ColorPicketContextWrapper contextWrapper = new ColorPicketContextWrapper(context);
        ColorPickerMenuContext menuContext = new ColorPickerMenuContext(player, contextWrapper);
        Menu menu = colorPickerFactory.createMenu(menuContext);
        contextWrapper.subscribe(() -> menu.updateItems(slot -> slot instanceof ColorSlot));
        return menu;
    }

    public void openMenu(Player player, Session session, MenuFactory factory, Element element) {
        Menu menu = factory.createMenu(new ElementMenuContext(player, session, element));
        player.openInventory(menu.getInventory());
    }

    public void openMenu(Player player, Element element, MenuBuilderFactory builderFactory) {
        MenuSlot background = Objects.requireNonNull(EasyArmorStandsPlugin.getInstance().getConfiguration().editor.menu.background.createSlot(new SimpleMenuContext(player)));
        MenuBuilder builder = builderFactory.createMenuBuilder(element.getType().getDisplayName(), player.locale(), background);
        new ElementMenuLayoutEvent(player, element, builder).callEvent();
        Menu menu = builder.createMenu();
        player.openInventory(menu.getInventory());
    }

    public DisplayElementType<ItemDisplay> getItemDisplayType() {
        return itemDisplayType;
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
    public @NotNull PropertyTypeRegistry propertyTypeRegistry() {
        return Objects.requireNonNull(propertyTypeRegistry);
    }

    @Override
    public @NotNull RegionPrivilegeManager regionPrivilegeManager() {
        return Objects.requireNonNull(regionPrivilegeManager);
    }

    @Override
    public @NotNull ChangeContextFactory changeContext() {
        return new PlayerChangeContextFactory();
    }

    public boolean canDiscoverElement(Player player, EditableElement element) {
        return new PlayerDiscoverElementEvent(player, element).callEvent();
    }

    public boolean canSelectElement(Player player, EditableElement element) {
        return new PlayerSelectElementEvent(player, element).callEvent();
    }

    public boolean canCreateElement(Player player, ElementType type, PropertyContainer properties) {
        return new PlayerCreateElementEvent(player, type, properties).callEvent();
    }

    public boolean canDestroyElement(Player player, Element element) {
        return new PlayerDestroyElementEvent(player, element).callEvent();
    }

    public <T> boolean canChangeProperty(Player player, Element element, Property<T> property, T value) {
        return new PlayerEditPropertyEvent<>(player, element, property, property.getValue(), value).callEvent();
    }

    private interface ConfigProcessor {
        void process(CommentedConfigurationNode node) throws ConfigurateException;

        void apply(CommentedConfigurationNode node) throws ConfigurateException;
    }
}
