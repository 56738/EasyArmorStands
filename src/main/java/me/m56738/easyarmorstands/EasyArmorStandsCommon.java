package me.m56738.easyarmorstands;

import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.editor.layer.ElementSelectionLayer;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementDiscoverySource;
import me.m56738.easyarmorstands.api.element.ElementSpawnRequest;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.element.ElementTypeRegistry;
import me.m56738.easyarmorstands.api.element.EntityElementProvider;
import me.m56738.easyarmorstands.api.element.ReferenceProvider;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import me.m56738.easyarmorstands.clipboard.Clipboard;
import me.m56738.easyarmorstands.clipboard.ClipboardManager;
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
import me.m56738.easyarmorstands.dialog.DialogBuilder;
import me.m56738.easyarmorstands.editor.layer.ElementSelectionLayerImpl;
import me.m56738.easyarmorstands.editor.layer.EntityElementDiscoverySource;
import me.m56738.easyarmorstands.editor.layer.ValueLayer;
import me.m56738.easyarmorstands.element.ArmorStandElementType;
import me.m56738.easyarmorstands.element.DefaultEntityElementProvider;
import me.m56738.easyarmorstands.element.DefaultEntityElementType;
import me.m56738.easyarmorstands.element.DisplayElementType;
import me.m56738.easyarmorstands.element.ElementMenuPopulator;
import me.m56738.easyarmorstands.element.ElementSpawnRequestImpl;
import me.m56738.easyarmorstands.element.ElementTypeRegistryImpl;
import me.m56738.easyarmorstands.element.EntityElementKeys;
import me.m56738.easyarmorstands.element.EntityElementPopulator;
import me.m56738.easyarmorstands.element.EntityElementProviderRegistryImpl;
import me.m56738.easyarmorstands.element.InteractionElementType;
import me.m56738.easyarmorstands.element.MannequinElementType;
import me.m56738.easyarmorstands.element.ReferenceProviderImpl;
import me.m56738.easyarmorstands.element.SimpleEntityElementProvider;
import me.m56738.easyarmorstands.element.TextDisplayElementType;
import me.m56738.easyarmorstands.event.EventDispatcher;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.history.HistoryManager;
import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.menu.MenuButtonCollector;
import me.m56738.easyarmorstands.menu.layout.MenuLayout;
import me.m56738.easyarmorstands.menu.layout.MenuLayoutRule;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.message.MessageManager;
import me.m56738.easyarmorstands.message.TranslationManager;
import me.m56738.easyarmorstands.particle.ParticleProviderFactory;
import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.platform.entity.BlockDisplay;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.EntityType;
import me.m56738.easyarmorstands.platform.entity.ItemDisplay;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.entity.TextDisplay;
import me.m56738.easyarmorstands.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.property.TrackedPropertyContainer;
import me.m56738.easyarmorstands.property.type.DefaultPropertyTypes;
import me.m56738.easyarmorstands.property.type.PropertyTypeRegistryImpl;
import me.m56738.easyarmorstands.registry.EntityTypeKeys;
import me.m56738.easyarmorstands.registry.ItemTypeKeys;
import me.m56738.easyarmorstands.session.SessionImpl;
import me.m56738.easyarmorstands.session.SessionManagerImpl;
import me.m56738.easyarmorstands.session.SessionToolProvider;
import net.kyori.adventure.key.InvalidKeyException;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.exception.InvalidCommandSenderException;
import org.incendo.cloud.injection.ParameterInjector;
import org.incendo.cloud.minecraft.extras.MinecraftExceptionHandler;
import org.incendo.cloud.minecraft.extras.MinecraftHelp;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.loader.HeaderMode;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;
import org.spongepowered.configurate.util.MapFactories;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.Callable;

@NullMarked
public abstract class EasyArmorStandsCommon implements EasyArmorStands {
    private static final MiniMessage miniMessage = MiniMessage.builder()
            .emitVirtuals(true)
            .build();

    private final TypeSerializerCollection serializers;
    private final TranslationManager translationManager;
    private final Platform platform;
    private final CommandManager<EasCommandSender> commandManager;
    private EasConfig config = new EasConfig();
    private @Nullable MessageManager messageManager;
    private @Nullable ReferenceProvider referenceProvider;
    private @Nullable ElementTypeRegistryImpl elementTypeRegistry;
    private @Nullable PropertyTypeRegistryImpl propertyTypeRegistry;
    private @Nullable EntityElementProviderRegistryImpl entityElementProviderRegistry;
    private @Nullable SessionManagerImpl sessionManager;
    private @Nullable HistoryManager historyManager;
    private @Nullable ClipboardManager clipboardManager;

    public EasyArmorStandsCommon(
            TranslationManager translationManager,
            Platform platform, CommandManager<EasCommandSender> commandManager) {
        this.platform = platform;
        this.translationManager = translationManager;
        this.commandManager = commandManager;
        this.serializers = EasSerializers.serializers(this);
    }

    public abstract String getVersion();

    public abstract ParticleProviderFactory particleProviderFactory();

    public abstract SessionToolProvider sessionToolProvider();

    public abstract Path getConfigFolder();

    public abstract ComponentLogger getLogger();

    public abstract ClassLoader getClassLoader();

    public abstract EventDispatcher eventDispatcher();

    public ClipboardManager clipboardManager() {
        if (clipboardManager == null) {
            throw new IllegalStateException();
        }
        return clipboardManager;
    }

    public static MiniMessage miniMessage() {
        return miniMessage;
    }

    public Platform platform() {
        return platform;
    }

    public void onLoad() {
        EntityTypeKeys.validate(platform);
        ItemTypeKeys.validate(platform);

        referenceProvider = new ReferenceProviderImpl(platform);
        elementTypeRegistry = new ElementTypeRegistryImpl();
        propertyTypeRegistry = new PropertyTypeRegistryImpl();
        entityElementProviderRegistry = new EntityElementProviderRegistryImpl(this);

        new DefaultPropertyTypes(propertyTypeRegistry);

        ArmorStandElementType armorStandElementType = new ArmorStandElementType(this);
        elementTypeRegistry.register(armorStandElementType);
        entityElementProviderRegistry.register(new SimpleEntityElementProvider<>(this, armorStandElementType));

        MannequinElementType mannequinElementType = new MannequinElementType(this);
        elementTypeRegistry.register(mannequinElementType);
        entityElementProviderRegistry.register(new SimpleEntityElementProvider<>(this, mannequinElementType));

        DisplayElementType<ItemDisplay> itemDisplayType = new DisplayElementType<>(this, platform.getEntityType(EntityTypeKeys.ITEM_DISPLAY), ItemDisplay.class);
        elementTypeRegistry.register(itemDisplayType);
        entityElementProviderRegistry.register(new SimpleEntityElementProvider<>(this, itemDisplayType));

        DisplayElementType<BlockDisplay> blockDisplayType = new DisplayElementType<>(this, platform.getEntityType(EntityTypeKeys.BLOCK_DISPLAY), BlockDisplay.class);
        elementTypeRegistry.register(blockDisplayType);
        entityElementProviderRegistry.register(new SimpleEntityElementProvider<>(this, blockDisplayType));

        DisplayElementType<TextDisplay> textDisplayType = new TextDisplayElementType(this);
        elementTypeRegistry.register(textDisplayType);
        entityElementProviderRegistry.register(new SimpleEntityElementProvider<>(this, textDisplayType));

        InteractionElementType interactionType = new InteractionElementType(this);
        elementTypeRegistry.register(interactionType);
        entityElementProviderRegistry.register(new SimpleEntityElementProvider<>(this, interactionType));

        for (EntityType entityType : platform.getEntityTypes()) {
            if (elementTypeRegistry.getOrNull(entityType.key()) != null) {
                continue;
            }
            DefaultEntityElementType<Entity> type = new DefaultEntityElementType<>(this, entityType, Entity.class);
            elementTypeRegistry.register(type);
            entityElementProviderRegistry.register(new DefaultEntityElementProvider<>(this, type));
        }

        loadConfig();
        messageManager = new MessageManager();
        messageManager.load(config);
    }

    public void onEnable() {
        sessionManager = new SessionManagerImpl(eventDispatcher(), sessionToolProvider(), this);
        historyManager = new HistoryManager(this);
        clipboardManager = new ClipboardManager(this);

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
                .registerInjector(EasyArmorStandsCommon.class, ParameterInjector.constantInjector(this))
                .registerInjector(Platform.class, ParameterInjector.constantInjector(platform))
                .registerInjector(ValueLayer.class, new ValueLayerInjector())
                .registerInjector(SessionImpl.class, new SessionInjector())
                .registerInjector(Clipboard.class, new ClipboardInjector())
                .registerInjector(Element.class, new ElementInjector())
                .registerInjector(ElementSelection.class, new ElementSelectionInjector())
                .registerInjector(ElementTypeRegistry.class, ParameterInjector.constantInjector(elementTypeRegistry))
                .registerInjector(new TypeToken<>() {
                }, ParameterInjector.constantInjector(help));

        commandManager.registerCommandPreProcessor(new ElementSelectionProcessor(this));
        commandManager.registerCommandPreProcessor(new GroupProcessor());
        commandManager.registerCommandPreProcessor(new ElementProcessor());
        commandManager.registerCommandPreProcessor(new SessionProcessor());
        commandManager.registerCommandPreProcessor(new ClipboardProcessor());

        commandManager.registerCommandPostProcessor(new CommandRequirementPostProcessor());
    }

    private Callable<BufferedReader> getDefaultConfigSource(String name) {
        return () -> {
            InputStream resource = getClassLoader().getResourceAsStream(name);
            if (resource == null) {
                throw new FileNotFoundException(name);
            }
            return new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8));
        };
    }

    public void onDisable() {
        sessionManager.stopAllSessions();
    }

    public void reload() {
        loadConfig();
        messageManager.load(config);
        translationManager.load(getConfigFolder(), getLogger());
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
                GameVersionTransformation.config(platform).apply(node);
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
            getLogger().error("Failed to load {}: {}", name, e.getMessage());
        } catch (Exception e) {
            getLogger().error("Failed to load {}", name, e);
        }

        try {
            loadDefaultConfig(name, configProcessor);
        } catch (ConfigurateException e) {
            getLogger().error("Failed to load default {}", name, e);
        }
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
                        .serializers(b -> b.registerAll(serializers))
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
                .defaultOptions(o -> o.serializers(b -> b.registerAll(serializers)))
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

    public CommandManager<EasCommandSender> getCommandManager() {
        return commandManager;
    }

    public EasConfig getConfiguration() {
        return config;
    }

    public void openSpawnMenu(Player player) {
        MenuButtonCollector collector = new MenuButtonCollector();
        new EntityElementPopulator(platform()).populateSpawnMenu(this, collector, player);
        eventDispatcher().dispatchSpawnMenuOpen(player, collector);
        Menu menu = MenuLayout.createSimple(this).createMenu(Component.translatable("easyarmorstands.menu.spawn.title"), player.locale(), collector.getButtons());
        player.openInventory(menu.getInventory());
    }

    public abstract Menu createColorPicker(Player player, Property<ItemStack> property);

    public abstract boolean isColorPickerSupported(ItemStack item);

    public void openElementMenu(Player player, Element element) {
        MenuButtonCollector collector = new MenuButtonCollector();
        PropertyContainer properties = new TrackedPropertyContainer(this, element, new EasPlayer(this, player));
        new ElementMenuPopulator(this).populateMenu(element, collector, player, properties);
        eventDispatcher().dispatchElementMenuOpen(player, element, collector, properties);
        MenuLayout layout;
        if (collector.getButtons().stream()
                .anyMatch(MenuLayoutRule.equipmentSlot(EquipmentSlot.HEAD)::matches)) {
            layout = MenuLayout.createEquipment(this);
        } else {
            layout = MenuLayout.createDefault(this);
        }
        Menu menu = layout.createMenu(element.getType().getDisplayName(), player.locale(), collector.getButtons());
        player.openInventory(menu.getInventory());
    }

    @Override
    public EntityElementProviderRegistryImpl entityElementProviderRegistry() {
        return entityElementProviderRegistry;
    }

    @Override
    public SessionManagerImpl sessionManager() {
        return Objects.requireNonNull(sessionManager);
    }

    @Override
    public ElementSpawnRequest elementSpawnRequest(ElementType type) {
        return new ElementSpawnRequestImpl(this, type);
    }

    @Override
    public ElementTypeRegistry elementTypeRegistry() {
        return elementTypeRegistry;
    }

    @Override
    public PropertyTypeRegistry propertyTypeRegistry() {
        return Objects.requireNonNull(propertyTypeRegistry);
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

    public ReferenceProvider referenceProvider() {
        return referenceProvider;
    }

    public DialogBuilder createDialogBuilder(Player player) {
        return new DialogBuilder(platform.getDialogFactory(), player.locale());
    }

    @SuppressWarnings("PatternValidation")
    public @Nullable Key getEntityElementProvider(Entity entity) {
        String providerName = entity.getCustomDataString(EntityElementKeys.ELEMENT_TYPE);
        if (providerName == null) {
            return null;
        }
        try {
            return Key.key(providerName);
        } catch (InvalidKeyException e) {
            return null;
        }
    }

    @Override
    public void setEntityElementProvider(Entity entity, @Nullable EntityElementProvider provider) {
        if (provider != null) {
            entity.setCustomDataString(EntityElementKeys.ELEMENT_TYPE, provider.key().asString());
        } else {
            entity.removeCustomData(EntityElementKeys.ELEMENT_TYPE);
        }
    }

    public abstract ItemStack createEntitySpawnEgg(Entity entity);

    public void update() {
        sessionManager.update();
    }

    private interface ConfigProcessor {
        void process(CommentedConfigurationNode node) throws ConfigurateException;

        void apply(CommentedConfigurationNode node) throws ConfigurateException;
    }
}
