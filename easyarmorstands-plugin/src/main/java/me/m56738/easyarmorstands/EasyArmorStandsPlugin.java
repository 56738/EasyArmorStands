package me.m56738.easyarmorstands;

import com.mojang.brigadier.arguments.StringArgumentType;
import io.leangen.geantyref.TypeToken;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import me.m56738.easyarmorstands.addon.AddonManager;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.EasyArmorStandsInitializer;
import me.m56738.easyarmorstands.api.context.ChangeContextFactory;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementSpawnRequest;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import me.m56738.easyarmorstands.clipboard.ClipboardListener;
import me.m56738.easyarmorstands.command.HistoryCommands;
import me.m56738.easyarmorstands.command.PropertyCommands;
import me.m56738.easyarmorstands.command.SessionCommands;
import me.m56738.easyarmorstands.common.clipboard.Clipboard;
import me.m56738.easyarmorstands.common.clipboard.ClipboardManager;
import me.m56738.easyarmorstands.common.command.ClipboardCommands;
import me.m56738.easyarmorstands.common.command.annotation.PropertyPermission;
import me.m56738.easyarmorstands.common.command.parser.NodeValueArgumentParser;
import me.m56738.easyarmorstands.common.command.processor.ClipboardInjector;
import me.m56738.easyarmorstands.common.command.processor.ClipboardProcessor;
import me.m56738.easyarmorstands.common.command.processor.ElementInjector;
import me.m56738.easyarmorstands.common.command.processor.ElementProcessor;
import me.m56738.easyarmorstands.common.command.processor.ElementSelectionInjector;
import me.m56738.easyarmorstands.common.command.processor.ElementSelectionProcessor;
import me.m56738.easyarmorstands.common.command.processor.GroupProcessor;
import me.m56738.easyarmorstands.common.command.processor.PropertyPermissionBuilderModifier;
import me.m56738.easyarmorstands.common.command.processor.SessionInjector;
import me.m56738.easyarmorstands.common.command.processor.SessionProcessor;
import me.m56738.easyarmorstands.common.command.processor.ValueNodeInjector;
import me.m56738.easyarmorstands.common.command.requirement.CommandRequirementBuilderModifier;
import me.m56738.easyarmorstands.common.command.requirement.CommandRequirementPostProcessor;
import me.m56738.easyarmorstands.common.command.requirement.ElementRequirement;
import me.m56738.easyarmorstands.common.command.requirement.ElementSelectionRequirement;
import me.m56738.easyarmorstands.common.command.requirement.RequireElement;
import me.m56738.easyarmorstands.common.command.requirement.RequireElementSelection;
import me.m56738.easyarmorstands.common.command.requirement.RequireSession;
import me.m56738.easyarmorstands.common.command.requirement.SessionRequirement;
import me.m56738.easyarmorstands.common.command.util.ElementSelection;
import me.m56738.easyarmorstands.common.editor.SessionImpl;
import me.m56738.easyarmorstands.common.editor.node.ValueNode;
import me.m56738.easyarmorstands.common.element.EntityElementProviderRegistryImpl;
import me.m56738.easyarmorstands.common.history.History;
import me.m56738.easyarmorstands.common.history.HistoryManager;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.common.property.context.PlayerChangeContextFactory;
import me.m56738.easyarmorstands.common.property.type.PropertyTypeRegistryImpl;
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
import me.m56738.easyarmorstands.element.ArmorStandElementProvider;
import me.m56738.easyarmorstands.element.ArmorStandElementType;
import me.m56738.easyarmorstands.element.ElementSpawnRequestImpl;
import me.m56738.easyarmorstands.element.EntityElementListener;
import me.m56738.easyarmorstands.element.SimpleEntityElementProvider;
import me.m56738.easyarmorstands.history.HistoryListener;
import me.m56738.easyarmorstands.message.MessageManager;
import me.m56738.easyarmorstands.paper.api.event.player.PlayerCreateElementEvent;
import me.m56738.easyarmorstands.paper.api.event.player.PlayerDestroyElementEvent;
import me.m56738.easyarmorstands.paper.api.event.player.PlayerDiscoverElementEvent;
import me.m56738.easyarmorstands.paper.api.event.player.PlayerEditPropertyEvent;
import me.m56738.easyarmorstands.paper.api.event.player.PlayerSelectElementEvent;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperEntityType;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import me.m56738.easyarmorstands.paper.api.platform.inventory.PaperItem;
import me.m56738.easyarmorstands.paper.permission.PaperPermissions;
import me.m56738.easyarmorstands.paper.platform.PaperPlatformImpl;
import me.m56738.easyarmorstands.paper.platform.command.PaperSenderMapper;
import me.m56738.easyarmorstands.session.SessionListener;
import me.m56738.easyarmorstands.session.SessionManagerImpl;
import me.m56738.easyarmorstands.update.UpdateManager;
import me.m56738.gizmo.bukkit.api.BukkitGizmos;
import net.kyori.adventure.text.format.TextColor;
import org.bstats.bukkit.Metrics;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.exception.InvalidCommandSenderException;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.minecraft.extras.MinecraftExceptionHandler;
import org.incendo.cloud.minecraft.extras.RichDescription;
import org.incendo.cloud.minecraft.extras.parser.TextColorParser;
import org.incendo.cloud.paper.PaperCommandManager;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.loader.HeaderMode;
import org.spongepowered.configurate.util.MapFactories;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.logging.Level;

public class EasyArmorStandsPlugin extends JavaPlugin implements EasyArmorStands {
    private static EasyArmorStandsPlugin instance;
    private PaperPlatformImpl platform;
    private EasConfig config;
    private MessageManager messageManager;
    private AddonManager addonManager;
    private PropertyTypeRegistryImpl propertyTypeRegistry;
    private EntityElementProviderRegistryImpl entityElementProviderRegistry;
    private SessionManagerImpl sessionManager;
    private HistoryManager historyManager;
    private ClipboardManager clipboardManager;
    private UpdateManager updateManager;
    private BukkitGizmos gizmos;
    private PaperCommandManager<CommandSource> commandManager;
    private AnnotationParser<CommandSource> annotationParser;
    private DisplayElementType itemDisplayType;

    public static EasyArmorStandsPlugin getInstance() {
        return instance;
    }

    public Path getConfigFolder() {
        return getDataFolder().toPath();
    }

    @Override
    public void onLoad() {
        platform = new PaperPlatformImpl(this);
        PaperPermissions.registerAll(platform);

        instance = this;
        EasyArmorStandsInitializer.initialize(this);

        propertyTypeRegistry = new PropertyTypeRegistryImpl();

        itemDisplayType = new DisplayElementType(platform, PaperEntityType.fromNative(EntityType.ITEM_DISPLAY));
        DisplayElementType blockDisplayType = new DisplayElementType(platform, PaperEntityType.fromNative(EntityType.BLOCK_DISPLAY));
        TextDisplayElementType textDisplayType = new TextDisplayElementType(platform);
        InteractionElementType interactionType = new InteractionElementType(platform);

        ArmorStandElementType armorStandElementType = new ArmorStandElementType(platform);
        entityElementProviderRegistry = new EntityElementProviderRegistryImpl(platform);
        entityElementProviderRegistry.register(new ArmorStandElementProvider(armorStandElementType));
        entityElementProviderRegistry.register(new SimpleEntityElementProvider(platform));
        entityElementProviderRegistry.register(new DisplayElementProvider(itemDisplayType));
        entityElementProviderRegistry.register(new DisplayElementProvider(blockDisplayType));
        entityElementProviderRegistry.register(new DisplayElementProvider(textDisplayType));
        entityElementProviderRegistry.register(new InteractionElementProvider(interactionType));

        loadConfig();
        messageManager = new MessageManager(this);
        messageManager.load(config);

        addonManager = new AddonManager(getLogger());
        addonManager.load(getClassLoader());
    }

    @Override
    public void onEnable() {
        new Metrics(this, 17911);
        gizmos = BukkitGizmos.create(this);

        sessionManager = new SessionManagerImpl(platform, entityElementProviderRegistry);
        historyManager = new HistoryManager(platform);
        clipboardManager = new ClipboardManager();

        SessionListener sessionListener = new SessionListener(this, sessionManager);
        getServer().getPluginManager().registerEvents(sessionListener, this);
        getServer().getPluginManager().registerEvents(new HistoryListener(historyManager), this);
        getServer().getPluginManager().registerEvents(new ClipboardListener(clipboardManager), this);
        getServer().getPluginManager().registerEvents(new EntityElementListener(), this);
        getServer().getPluginManager().registerEvents(new DisplayListener(), this);
        getServer().getScheduler().runTaskTimer(this, sessionManager::update, 0, 1);
        getServer().getScheduler().runTaskTimer(this, sessionListener::update, 0, 1);

        commandManager = createCommandManager();

        MinecraftExceptionHandler.create(CommandSource::source)
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
        commandManager.brigadierManager().registerMapping(new TypeToken<TextColorParser<CommandSource>>() {
        }, builder -> builder.cloudSuggestions().toConstant(StringArgumentType.greedyString()));

        commandManager.parserRegistry().registerParserSupplier(TypeToken.get(BlockData.class),
                p -> new BlockDataArgumentParser<>());
        commandManager.brigadierManager().registerMapping(new TypeToken<BlockDataArgumentParser<CommandSource>>() {
        }, builder -> builder.toConstant(ArgumentTypes.blockState()));

        commandManager.registerCommandPreProcessor(new ElementSelectionProcessor());
        commandManager.registerCommandPreProcessor(new GroupProcessor());
        commandManager.registerCommandPreProcessor(new ElementProcessor());
        commandManager.registerCommandPreProcessor(new SessionProcessor());
        commandManager.registerCommandPreProcessor(new ClipboardProcessor());

        commandManager.registerCommandPostProcessor(new CommandRequirementPostProcessor());

        annotationParser = new AnnotationParser<>(commandManager, CommandSource.class);
        annotationParser.descriptionMapper(RichDescription::translatable);

        annotationParser.registerBuilderModifier(RequireSession.class, new CommandRequirementBuilderModifier<>(a -> new SessionRequirement()));
        annotationParser.registerBuilderModifier(RequireElement.class, new CommandRequirementBuilderModifier<>(a -> new ElementRequirement()));
        annotationParser.registerBuilderModifier(RequireElementSelection.class, new CommandRequirementBuilderModifier<>(a -> new ElementSelectionRequirement()));
        annotationParser.registerBuilderModifier(PropertyPermission.class, new PropertyPermissionBuilderModifier(propertyTypeRegistry));
        annotationParser.parse(new SessionCommands());
        annotationParser.parse(new HistoryCommands());
        annotationParser.parse(new ClipboardCommands());
        annotationParser.parse(new DisplayCommands());

        PropertyCommands.register(commandManager, platform);

        addonManager.enable();

        loadUpdateChecker();
    }

    private PaperCommandManager<CommandSource> createCommandManager() {
        return PaperCommandManager.builder(new PaperSenderMapper())
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
        PaperPermissions.unregisterAll();
        if (gizmos != null) {
            gizmos.close();
        }
    }

    public void reload() {
        loadConfig();
        messageManager.load(config);
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
        return PaperItem.toNative(platform.createTool());
    }

    public boolean isTool(ItemStack item) {
        return platform.isTool(PaperItem.fromNative(item));
    }

    public EasConfig getConfiguration() {
        return config;
    }

    public DisplayElementType getItemDisplayType() {
        return itemDisplayType;
    }

    @Override
    public @NotNull EntityElementProviderRegistryImpl entityElementProviderRegistry() {
        return entityElementProviderRegistry;
    }

    @Override
    public @NotNull SessionManagerImpl sessionManager() {
        return Objects.requireNonNull(sessionManager);
    }

    @Override
    public @NotNull ElementSpawnRequest elementSpawnRequest(ElementType type) {
        return new ElementSpawnRequestImpl(platform, historyManager, type);
    }

    @Override
    public @NotNull CommonPlatform platform() {
        return platform;
    }

    @Override
    public @NotNull PropertyTypeRegistry propertyTypeRegistry() {
        return Objects.requireNonNull(propertyTypeRegistry);
    }

    @Override
    public @NotNull ChangeContextFactory changeContext() {
        return new PlayerChangeContextFactory(historyManager);
    }

    public boolean canDiscoverElement(Player player, EditableElement element) {
        return new PlayerDiscoverElementEvent(PaperPlayer.toNative(player), element).callEvent();
    }

    public boolean canSelectElement(Player player, EditableElement element) {
        return new PlayerSelectElementEvent(PaperPlayer.toNative(player), element).callEvent();
    }

    public boolean canCreateElement(Player player, ElementType type, PropertyContainer properties) {
        return new PlayerCreateElementEvent(PaperPlayer.toNative(player), type, properties).callEvent();
    }

    public boolean canDestroyElement(Player player, Element element) {
        return new PlayerDestroyElementEvent(PaperPlayer.toNative(player), element).callEvent();
    }

    public <T> boolean canChangeProperty(Player player, Element element, Property<T> property, T value) {
        return new PlayerEditPropertyEvent<>(PaperPlayer.toNative(player), element, property, property.getValue(), value).callEvent();
    }

    private interface ConfigProcessor {
        void process(CommentedConfigurationNode node) throws ConfigurateException;

        void apply(CommentedConfigurationNode node) throws ConfigurateException;
    }
}
