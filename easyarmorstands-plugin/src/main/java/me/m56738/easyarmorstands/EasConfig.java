package me.m56738.easyarmorstands;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.capability.tool.ToolCapability;
import me.m56738.easyarmorstands.item.ItemRenderer;
import me.m56738.easyarmorstands.item.ItemTemplate;
import me.m56738.easyarmorstands.message.MessageStyle;
import me.m56738.easyarmorstands.util.ArmorStandPartInfo;
import me.m56738.easyarmorstands.util.ConfigUtil;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;

public class EasConfig {
    private final EasyArmorStands plugin;
    private final Set<Consumer<EasConfig>> subscriptions = new HashSet<>();
    private final Map<MessageStyle, String> messageFormats = new EnumMap<>(MessageStyle.class);
    private boolean loaded;
    private ConfigurationSection config;
    private boolean updateCheck;
    private boolean serverSideTranslation;
    private ItemTemplate toolTemplate;
    private ItemTemplate backgroundTemplate;
    private ItemTemplate destroyButtonTemplate;
    private ItemTemplate colorPickerButtonTemplate;
    private ItemTemplate colorPickerActiveButtonTemplate;
    private ItemTemplate armorStandButtonTemplate;
    private ItemTemplate armorStandPositionButtonTemplate;
    private EnumMap<ArmorStandPart, ItemTemplate> armorStandPartButtonTemplates;
    private ConfigurationSection propertyConfig;

    public EasConfig(EasyArmorStands plugin) {
        this.plugin = plugin;
    }

    void load() {
        config = getConfig("config.yml");
        updateCheck = config.getBoolean("update-check");
        serverSideTranslation = config.getBoolean("server-side-translation");
        messageFormats.clear();
        for (MessageStyle style : MessageStyle.values()) {
            String name = style.name().toLowerCase(Locale.ROOT).replace('_', '-');
            String format = config.getString("format." + name);
            messageFormats.put(style, format);
        }
        toolTemplate = ConfigUtil.getItem(config, "tool", ItemRenderer.item()).editMeta(this::configureTool);
        backgroundTemplate = ConfigUtil.getButton(config, "menu.background");
        destroyButtonTemplate = ConfigUtil.getButton(config, "menu.element.buttons.destroy");
        colorPickerButtonTemplate = ConfigUtil.getButton(config, "menu.element.buttons.color-picker");
        colorPickerActiveButtonTemplate = colorPickerButtonTemplate.appendLore(config.getStringList("menu.element.buttons.color-picker.active-description"));
        armorStandButtonTemplate = ConfigUtil.getButton(config, "menu.spawn.buttons.armor-stand").addResolver(TagResolver.resolver("type", Tag.selfClosingInserting(plugin.getCapability(EntityTypeCapability.class).getName(EntityType.ARMOR_STAND))));
        armorStandPositionButtonTemplate = ConfigUtil.getButton(config, "menu.element.buttons.armor-stand-bone.position");
        armorStandPartButtonTemplates = new EnumMap<>(ArmorStandPart.class);
        for (ArmorStandPart part : ArmorStandPart.values()) {
            armorStandPartButtonTemplates.put(part, ConfigUtil.getButton(config, "menu.element.buttons.armor-stand-bone." + ArmorStandPartInfo.of(part).getName()));
        }
        propertyConfig = getConfig("properties.yml");

        loaded = true;
        for (Consumer<EasConfig> subscription : subscriptions) {
            subscription.accept(this);
        }
    }

    public void subscribe(Consumer<EasConfig> subscription) {
        subscriptions.add(subscription);
        if (loaded) {
            subscription.accept(this);
        }
    }

    private ConfigurationSection getConfig(String name) {
        YamlConfiguration defaultConfig = new YamlConfiguration();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(plugin.getResource(name), StandardCharsets.UTF_8))) {
            defaultConfig.load(reader);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to load default " + name, e);
        }

        YamlConfiguration config = new YamlConfiguration();
        try (BufferedReader reader = Files.newBufferedReader(plugin.getDataFolder().toPath().resolve(name))) {
            config.load(reader);
        } catch (NoSuchFileException ignored) {
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to load " + name, e);
        }

        config.setDefaults(defaultConfig);
        return config;
    }

    private void configureTool(ItemMeta meta) {
        ToolCapability toolCapability = plugin.getCapability(ToolCapability.class);
        if (toolCapability != null) {
            toolCapability.configureTool(meta);
        }
    }

    public Map<MessageStyle, String> getMessageFormats() {
        return Collections.unmodifiableMap(messageFormats);
    }

    public boolean isUpdateCheck() {
        return updateCheck;
    }

    public boolean isServerSideTranslation() {
        return serverSideTranslation;
    }

    public ItemTemplate getToolTemplate() {
        return toolTemplate;
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

    public ConfigurationSection getConfig() {
        return config;
    }

    public ConfigurationSection getPropertyConfig() {
        return propertyConfig;
    }
}
