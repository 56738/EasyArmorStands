package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.ArmorStandSize;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.joml.Quaterniondc;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class PropertyTypes {
    private static final Set<ConfigurablePropertyType<?>> types = new HashSet<>();
    private static ConfigurationSection currentConfig;
    public static final PropertyType<Boolean> ARMOR_STAND_ARMS = register(new BooleanTogglePropertyType("armor-stand-arms"));
    public static final PropertyType<Boolean> ARMOR_STAND_BASE_PLATE = register(new BooleanTogglePropertyType("armor-stand-base-plate"));
    public static final PropertyType<Boolean> ARMOR_STAND_CAN_TICK = register(new BooleanPropertyType("armor-stand-can-tick"));
    public static final PropertyType<Boolean> ARMOR_STAND_GRAVITY = register(new GravityPropertyType("armor-stand-gravity"));
    public static final PropertyType<Boolean> ARMOR_STAND_INVULNERABILITY = register(new BooleanTogglePropertyType("armor-stand-invulnerability"));
    public static final PropertyType<Boolean> ARMOR_STAND_LOCK = register(new BooleanTogglePropertyType("armor-stand-lock"));
    public static final PropertyType<Boolean> ARMOR_STAND_MARKER = register(new BooleanTogglePropertyType("armor-stand-marker"));
    public static final Map<ArmorStandPart, PropertyType<Quaterniondc>> ARMOR_STAND_POSE = buildArmorStandPoseProperties();
    public static final PropertyType<ArmorStandSize> ARMOR_STAND_SIZE = register(new EnumTogglePropertyType<>("armor-stand-size", ArmorStandSize.class));
    public static final PropertyType<Boolean> ARMOR_STAND_VISIBILITY = register(new BooleanTogglePropertyType("armor-stand-visibility")); // TODO change to entity-visibility
    public static final PropertyType<Component> ENTITY_CUSTOM_NAME = register(new ComponentPropertyType("entity-custom-name", "/eas name set"));
    public static final PropertyType<Boolean> ENTITY_CUSTOM_NAME_VISIBLE = register(new BooleanPropertyType("entity-custom-name-visible"));
    public static final Map<EquipmentSlot, PropertyType<ItemStack>> ENTITY_EQUIPMENT = buildEntityEquipmentProperties();
    public static final PropertyType<Boolean> ENTITY_GLOWING = register(new BooleanPropertyType("entity-glowing"));
    public static final PropertyType<Location> ENTITY_LOCATION = register(new LocationPropertyType("entity-location"));

    private PropertyTypes() {
    }

    public static <T> PropertyType<T> register(ConfigurablePropertyType<T> type) {
        types.add(type);
        load(type);
        return type;
    }

    public static void load(ConfigurationSection config) {
        currentConfig = config;
        for (ConfigurablePropertyType<?> type : types) {
            load(type);
        }
    }

    private static void load(ConfigurablePropertyType<?> type) {
        if (currentConfig == null) {
            return;
        }
        String key = type.key();
        ConfigurationSection section = currentConfig.getConfigurationSection(key);
        if (section != null) {
            type.load(section);
        }
    }

    private static Map<ArmorStandPart, PropertyType<Quaterniondc>> buildArmorStandPoseProperties() {
        EnumMap<ArmorStandPart, PropertyType<Quaterniondc>> map = new EnumMap<>(ArmorStandPart.class);
        for (ArmorStandPart part : ArmorStandPart.values()) {
            map.put(part, register(new QuaterniondcPropertyType("armor-stand-pose." + part.getName())));
        }
        return Collections.unmodifiableMap(map);
    }

    private static Map<EquipmentSlot, PropertyType<ItemStack>> buildEntityEquipmentProperties() {
        EnumMap<EquipmentSlot, PropertyType<ItemStack>> map = new EnumMap<>(EquipmentSlot.class);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            String name = slot.name().toLowerCase(Locale.ROOT).replace('_', '-');
            map.put(slot, register(new ItemPropertyType("entity-equipment." + name)));
        }
        return Collections.unmodifiableMap(map);
    }
}
