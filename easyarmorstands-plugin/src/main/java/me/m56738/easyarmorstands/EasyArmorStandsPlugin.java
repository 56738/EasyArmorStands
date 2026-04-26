package me.m56738.easyarmorstands;

import com.mojang.brigadier.arguments.StringArgumentType;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.addon.AddonManager;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.EasyArmorStandsInitializer;
import me.m56738.easyarmorstands.api.editor.layer.ElementSelectionLayer;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementDiscoverySource;
import me.m56738.easyarmorstands.api.element.ElementSpawnRequest;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.element.ElementTypeRegistry;
import me.m56738.easyarmorstands.api.element.EntityElementProvider;
import me.m56738.easyarmorstands.api.element.EntityElementReference;
import me.m56738.easyarmorstands.api.element.EntityElementType;
import me.m56738.easyarmorstands.api.event.menu.ElementMenuOpenEvent;
import me.m56738.easyarmorstands.api.event.menu.SpawnMenuOpenEvent;
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
import me.m56738.easyarmorstands.command.processor.ValueLayerInjector;
import me.m56738.easyarmorstands.command.requirement.CommandRequirementPostProcessor;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.command.util.ElementSelection;
import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.config.serializer.EasSerializers;
import me.m56738.easyarmorstands.config.version.Transformations;
import me.m56738.easyarmorstands.config.version.game.GameVersionTransformation;
import me.m56738.easyarmorstands.editor.layer.ElementSelectionLayerImpl;
import me.m56738.easyarmorstands.editor.layer.EntityElementDiscoverySource;
import me.m56738.easyarmorstands.editor.layer.ValueLayer;
import me.m56738.easyarmorstands.element.ArmorStandElementType;
import me.m56738.easyarmorstands.element.DefaultEntityElementProvider;
import me.m56738.easyarmorstands.element.DefaultEntityElementType;
import me.m56738.easyarmorstands.element.DisplayElementType;
import me.m56738.easyarmorstands.element.ElementMenuListener;
import me.m56738.easyarmorstands.element.ElementSpawnRequestImpl;
import me.m56738.easyarmorstands.element.ElementTypeRegistryImpl;
import me.m56738.easyarmorstands.element.EntityElementKeys;
import me.m56738.easyarmorstands.element.EntityElementListener;
import me.m56738.easyarmorstands.element.EntityElementProviderRegistryImpl;
import me.m56738.easyarmorstands.element.EntityElementReferenceImpl;
import me.m56738.easyarmorstands.element.InteractionElementType;
import me.m56738.easyarmorstands.element.MannequinElementType;
import me.m56738.easyarmorstands.element.SimpleEntityElementProvider;
import me.m56738.easyarmorstands.element.TextDisplayElementType;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.history.HistoryManager;
import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.menu.MenuButtonCollector;
import me.m56738.easyarmorstands.menu.MenuListener;
import me.m56738.easyarmorstands.menu.color.ColorPickerContext;
import me.m56738.easyarmorstands.menu.color.ColorPickerMenuContext;
import me.m56738.easyarmorstands.menu.color.ColorPicketContextWrapper;
import me.m56738.easyarmorstands.menu.factory.MenuFactory;
import me.m56738.easyarmorstands.menu.layout.MenuLayout;
import me.m56738.easyarmorstands.menu.layout.MenuLayoutRule;
import me.m56738.easyarmorstands.menu.slot.MenuSlotTypeRegistry;
import me.m56738.easyarmorstands.menu.slot.MenuSlotTypeRegistryImpl;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.message.MessageManager;
import me.m56738.easyarmorstands.message.TranslationManager;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.property.TrackedPropertyContainer;
import me.m56738.easyarmorstands.property.type.DefaultPropertyTypes;
import me.m56738.easyarmorstands.property.type.PropertyTypeRegistryImpl;
import me.m56738.easyarmorstands.region.RegionListenerManager;
import me.m56738.easyarmorstands.session.SessionImpl;
import me.m56738.easyarmorstands.session.SessionListener;
import me.m56738.easyarmorstands.session.SessionManagerImpl;
import me.m56738.easyarmorstands.update.UpdateManager;
import me.m56738.easyarmorstands.util.MainThreadExecutor;
import me.m56738.gizmo.bukkit.api.BukkitGizmos;
import net.kyori.adventure.text.Component;
import org.bstats.bukkit.Metrics;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
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
    private static EasyArmorStandsPlugin instance;
    private final NamespacedKey toolKey = new NamespacedKey(this, "tool");
    private final TranslationManager translationManager;
    private final PaperCommandManager.Bootstrapped<EasCommandSender> commandManager;
    private EasConfig config;
    private MenuFactory colorPickerFactory;
    private MessageManager messageManager;
    private AddonManager addonManager;
    private RegionListenerManager regionPrivilegeManager;
    private ElementTypeRegistryImpl elementTypeRegistry;
    private PropertyTypeRegistryImpl propertyTypeRegistry;
    private EntityElementProviderRegistryImpl entityElementProviderRegistry;
    private MenuSlotTypeRegistryImpl menuSlotTypeRegistry;
    private SessionManagerImpl sessionManager;
    private HistoryManager historyManager;
    private ClipboardManager clipboardManager;
    private UpdateManager updateManager;
    private BukkitGizmos gizmos;

    public EasyArmorStandsPlugin(
            MainThreadExecutor executor,
            TranslationManager translationManager,
            PaperCommandManager.Bootstrapped<EasCommandSender> commandManager) {
        executor.setPlugin(this);
        this.translationManager = translationManager;
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

        ArmorStandElementType armorStandElementType = new ArmorStandElementType();
        elementTypeRegistry.register(armorStandElementType);
        entityElementProviderRegistry.register(new SimpleEntityElementProvider<>(armorStandElementType));

        MannequinElementType mannequinElementType = new MannequinElementType();
        elementTypeRegistry.register(mannequinElementType);
        entityElementProviderRegistry.register(new SimpleEntityElementProvider<>(mannequinElementType));

        DisplayElementType<ItemDisplay> itemDisplayType = new DisplayElementType<>(EntityType.ITEM_DISPLAY, ItemDisplay.class);
        elementTypeRegistry.register(itemDisplayType);
        entityElementProviderRegistry.register(new SimpleEntityElementProvider<>(itemDisplayType));

        DisplayElementType<BlockDisplay> blockDisplayType = new DisplayElementType<>(EntityType.BLOCK_DISPLAY, BlockDisplay.class);
        elementTypeRegistry.register(blockDisplayType);
        entityElementProviderRegistry.register(new SimpleEntityElementProvider<>(blockDisplayType));

        DisplayElementType<TextDisplay> textDisplayType = new TextDisplayElementType();
        elementTypeRegistry.register(textDisplayType);
        entityElementProviderRegistry.register(new SimpleEntityElementProvider<>(textDisplayType));

        InteractionElementType interactionType = new InteractionElementType();
        elementTypeRegistry.register(interactionType);
        entityElementProviderRegistry.register(new SimpleEntityElementProvider<>(interactionType));

        for (EntityType entityType : EntityType.values()) {
            Class<? extends Entity> entityClass = entityType.getEntityClass();
            if (entityClass == null) {
                continue;
            }
            if (elementTypeRegistry.getOrNull(entityType.key()) != null) {
                continue;
            }
            DefaultEntityElementType<? extends Entity> type = new DefaultEntityElementType<>(entityType, entityClass);
            elementTypeRegistry.register(type);
            entityElementProviderRegistry.register(new DefaultEntityElementProvider<>(type));
        }

        menuSlotTypeRegistry = new MenuSlotTypeRegistryImpl();
        menuSlotTypeRegistry.register(new ColorAxisSlotType());
        menuSlotTypeRegistry.register(new ColorAxisChangeSlotType());
        menuSlotTypeRegistry.register(new ColorIndicatorSlotType());
        menuSlotTypeRegistry.register(new ColorPresetSlotType());

        loadConfig();
        messageManager = new MessageManager();
        messageManager.load(config);

        regionPrivilegeManager = new RegionListenerManager();

        addonManager = new AddonManager(getLogger());
        addonManager.load(getClassLoader());
    }

    @Override
    public void onEnable() {
        new Metrics(this, 17911);
        gizmos = BukkitGizmos.create(this);

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
                        (formatter, context) -> Message.error("easyarmorstands.error.not-a-player"))
                .registerTo(commandManager);

        MinecraftHelp<EasCommandSender> help = MinecraftHelp.createNative("/eas help", commandManager);

        commandManager.parameterInjectorRegistry()
                .registerInjector(ValueLayer.class, new ValueLayerInjector())
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
        messageManager.load(config);
        translationManager.load(getDataPath(), getComponentLogger());
        loadMenuTemplates();
        addonManager.reload();
        for (SessionImpl session : sessionManager.getAllSessions()) {
            if (session.getLayer() instanceof ElementSelectionLayerImpl layer) {
                layer.refresh();
            }
        }
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

    private void loadConfig(String name, ConfigProcessor configProcessor) {
        try {
            loadMergedConfig(name, configProcessor, true);
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
                : loadMergedConfig(configName, processor, false);
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

    private CommentedConfigurationNode loadMergedConfig(String name, ConfigProcessor configProcessor, boolean create) throws ConfigurateException {
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
        boolean exists = !node.empty();

        if (exists) {
            configProcessor.process(node);
        }

        node.mergeFrom(defaultNode);

        configProcessor.apply(node);

        if (exists || create) {
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

    public void openSpawnMenu(Player player) {
        MenuButtonCollector collector = new MenuButtonCollector();
        new SpawnMenuOpenEvent(player, collector).callEvent();
        Menu menu = MenuLayout.SIMPLE.createMenu(Component.translatable("easyarmorstands.menu.spawn.title"), player.locale(), collector.getButtons());
        player.openInventory(menu.getInventory());
    }

    public Menu createColorPicker(Player player, ColorPickerContext context) {
        ColorPicketContextWrapper contextWrapper = new ColorPicketContextWrapper(context);
        ColorPickerMenuContext menuContext = new ColorPickerMenuContext(new EasPlayer(player), contextWrapper);
        Menu menu = colorPickerFactory.createMenu(menuContext);
        contextWrapper.subscribe(() -> menu.updateItems(slot -> slot instanceof ColorSlot));
        return menu;
    }

    public void openElementMenu(Player player, Element element) {
        MenuButtonCollector collector = new MenuButtonCollector();
        PropertyContainer properties = new TrackedPropertyContainer(element, new EasPlayer(player));
        new ElementMenuOpenEvent(player, element, collector, properties).callEvent();
        MenuLayout layout;
        if (collector.getButtons().stream()
                .anyMatch(MenuLayoutRule.equipmentSlot(EquipmentSlot.HEAD)::matches)) {
            layout = MenuLayout.EQUIPMENT;
        } else {
            layout = MenuLayout.DEFAULT;
        }
        Menu menu = layout.createMenu(element.getType().getDisplayName(), player.locale(), collector.getButtons());
        player.openInventory(menu.getInventory());
    }

    @Override
    public @NotNull EntityElementProviderRegistryImpl entityElementProviderRegistry() {
        return entityElementProviderRegistry;
    }

    public @NotNull MenuSlotTypeRegistry menuSlotTypeRegistry() {
        return Objects.requireNonNull(menuSlotTypeRegistry);
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

    @Override
    public @Nullable Element getElement(Entity entity) {
        if (!entity.isValid()) {
            return null;
        }
        if (entity.hasMetadata("gizmo")) {
            return null;
        }
        return entityElementProviderRegistry.getElement(entity);
    }

    @Override
    public void setEntityElementProvider(Entity entity, EntityElementProvider provider) {
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        if (provider != null) {
            pdc.set(EntityElementKeys.ELEMENT_TYPE, PersistentDataType.STRING, provider.key().asString());
        } else {
            pdc.remove(EntityElementKeys.ELEMENT_TYPE);
        }
        EasyArmorStandsPlugin.getInstance().refreshEntity(entity);
    }

    @Override
    public void refreshEntity(Entity entity) {
        for (SessionImpl session : sessionManager.getAllSessions()) {
            ElementSelectionLayer layer = session.findLayer(ElementSelectionLayer.class);
            if (layer != null) {
                for (ElementDiscoverySource source : layer.getSources()) {
                    if (source instanceof EntityElementDiscoverySource entitySource) {
                        layer.refreshEntry(entitySource.getEntry(entity));
                    }
                }
            }
        }
    }

    private interface ConfigProcessor {
        void process(CommentedConfigurationNode node) throws ConfigurateException;

        void apply(CommentedConfigurationNode node) throws ConfigurateException;
    }
}
