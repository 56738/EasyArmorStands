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
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
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
import me.m56738.easyarmorstands.element.EntityElementProviderRegistry;
import me.m56738.easyarmorstands.element.SimpleEntityElementProvider;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.history.HistoryManager;
import me.m56738.easyarmorstands.menu.MenuListener;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.message.MessageManager;
import me.m56738.easyarmorstands.node.ValueNode;
import me.m56738.easyarmorstands.property.type.PropertyTypes;
import me.m56738.easyarmorstands.session.SessionListener;
import me.m56738.easyarmorstands.session.SessionManager;
import me.m56738.easyarmorstands.update.UpdateManager;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.ConfigUtil;
import me.m56738.easyarmorstands.util.ItemTemplate;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;

public class EasyArmorStands extends JavaPlugin {
    private static EasyArmorStands instance;
    private final CapabilityLoader loader = new CapabilityLoader(this, getClassLoader());
    private final AddonLoader addonLoader = new AddonLoader(this, getClassLoader());
    private MessageManager messageManager;
    private EntityElementProviderRegistry entityElementProviderRegistry;
    private SessionManager sessionManager;
    private HistoryManager historyManager;
    private UpdateManager updateManager;
    private BukkitAudiences adventure;
    private ItemTemplate toolTemplate;
    private ItemTemplate backgroundTemplate;
    private ItemTemplate destroyButtonTemplate;
    private ItemTemplate colorPickerButtonTemplate;
    private ItemTemplate colorPickerActiveButtonTemplate;
    private ItemTemplate armorStandButtonTemplate;
    private ItemTemplate armorStandPositionButtonTemplate;
    private EnumMap<ArmorStandPart, ItemTemplate> armorStandPartButtonTemplates;
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

        messageManager = new MessageManager(this);
        loader.load();
        load();

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

        addonLoader.load();
    }

    @Override
    public void onDisable() {
        if (sessionManager != null) {
            sessionManager.stopAllSessions();
        }
    }

    private void load() {
        FileConfiguration config = getConfig();

        messageManager.load(getDataFolder().toPath(), config);

        toolTemplate = ConfigUtil.getItem(config, "tool").editMeta(this::configureTool);
        backgroundTemplate = ConfigUtil.getButton(config, "menu.background");
        destroyButtonTemplate = ConfigUtil.getButton(config, "menu.element.buttons.destroy");
        colorPickerButtonTemplate = ConfigUtil.getButton(config, "menu.element.buttons.color-picker");
        colorPickerActiveButtonTemplate = colorPickerButtonTemplate.appendLore(config.getStringList("menu.element.buttons.color-picker.active-description"));
        armorStandButtonTemplate = ConfigUtil.getButton(config, "menu.spawn.buttons.armor-stand").addResolver(TagResolver.resolver("type", Tag.selfClosingInserting(getCapability(EntityTypeCapability.class).getName(EntityType.ARMOR_STAND))));
        armorStandPositionButtonTemplate = ConfigUtil.getButton(config, "menu.element.buttons.armor-stand-bone.position");
        armorStandPartButtonTemplates = new EnumMap<>(ArmorStandPart.class);
        for (ArmorStandPart part : ArmorStandPart.values()) {
            armorStandPartButtonTemplates.put(part, ConfigUtil.getButton(config, "menu.element.buttons.armor-stand-bone." + part.getName()));
        }

        boolean isSnapshot = getDescription().getVersion().endsWith("-SNAPSHOT");
        if (config.getBoolean("update-check", false) && !isSnapshot) {
            if (updateManager == null) {
                updateManager = new UpdateManager(this, adventure, "easyarmorstands.update.notify", 108349);
            }
        } else {
            if (updateManager != null) {
                updateManager.unregister();
                updateManager = null;
            }
        }

        loadProperties();
    }

    private void configureTool(ItemMeta meta) {
        ToolCapability toolCapability = getCapability(ToolCapability.class);
        if (toolCapability != null) {
            toolCapability.configureTool(meta);
        }
    }

    private void loadProperties() {
        YamlConfiguration defaultConfig = new YamlConfiguration();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getResource("properties.yml"), StandardCharsets.UTF_8))) {
            defaultConfig.load(reader);
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Failed to load default property definitions", e);
            return;
        }

        YamlConfiguration config = new YamlConfiguration();
        try (BufferedReader reader = Files.newBufferedReader(new File(getDataFolder(), "properties.yml").toPath())) {
            config.load(reader);
        } catch (NoSuchFileException ignored) {
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Failed to load property definitions", e);
            return;
        }

        config.setDefaults(defaultConfig);

        PropertyTypes.load(config);
    }

    public void reload() {
        reloadConfig();
        load();
        addonLoader.reload();
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

    public MessageManager getMessageManager() {
        return messageManager;
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

    public ItemStack createTool(Locale locale) {
        return toolTemplate.render(locale);
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
        if (!Objects.equals(toolTemplate.getType(), item.getType())) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        return meta.hasDisplayName();
    }

    public ItemTemplate getBackgroundTemplate() {
        return backgroundTemplate;
    }

    public ItemTemplate getDestroyButtonTemplate() {
        return destroyButtonTemplate;
    }

    public ItemTemplate getColorPickerButtonTemplate(boolean active) {
        return active ? colorPickerActiveButtonTemplate : colorPickerButtonTemplate;
    }

    public ItemTemplate getArmorStandButtonTemplate() {
        return armorStandButtonTemplate;
    }

    public ItemTemplate getArmorStandPositionButtonTemplate() {
        return armorStandPositionButtonTemplate;
    }

    public ItemTemplate getArmorStandPartButtonTemplate(ArmorStandPart part) {
        return armorStandPartButtonTemplates.get(part);
    }
}
