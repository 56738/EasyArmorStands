package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.joml.Quaterniondc;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings("PatternValidation")
public class PropertyTypes {
    public static final PropertyTypeRegistry REGISTRY = PropertyTypeRegistry.Holder.instance;
    public static final PropertyType<Boolean> ARMOR_STAND_ARMS = get("armor_stand_arms", Boolean.class);
    public static final PropertyType<Boolean> ARMOR_STAND_BASE_PLATE = get("armor_stand_base_plate", Boolean.class);
    public static final PropertyType<Boolean> ARMOR_STAND_CAN_TICK = get("armor_stand_can_tick", Boolean.class);
    public static final PropertyType<Boolean> ARMOR_STAND_GRAVITY = get("armor_stand_gravity", Boolean.class);
    public static final PropertyType<Boolean> ARMOR_STAND_INVULNERABILITY = get("armor_stand_invulnerability", Boolean.class);
    public static final PropertyType<Boolean> ARMOR_STAND_LOCK = get("armor_stand_lock", Boolean.class);
    public static final PropertyType<Boolean> ARMOR_STAND_MARKER = get("armor_stand_marker", Boolean.class);
    public static final Map<ArmorStandPart, PropertyType<Quaterniondc>> ARMOR_STAND_POSE = new EnumMap<>(ArmorStandPart.class);
    public static final PropertyType<ArmorStandSize> ARMOR_STAND_SIZE = get("armor_stand_size", ArmorStandSize.class);
    public static final PropertyType<Component> ENTITY_CUSTOM_NAME = get("entity_custom_name", Component.class);
    public static final PropertyType<Boolean> ENTITY_CUSTOM_NAME_VISIBLE = get("entity_custom_name_visible", Boolean.class);
    public static final Map<EquipmentSlot, PropertyType<ItemStack>> ENTITY_EQUIPMENT = new EnumMap<>(EquipmentSlot.class);
    public static final PropertyType<Boolean> ENTITY_GLOWING = get("entity_glowing", Boolean.class);
    public static final PropertyType<Location> ENTITY_LOCATION = get("entity_location", Location.class);
    public static final PropertyType<Boolean> ENTITY_VISIBILITY = get("entity_visibility", Boolean.class);

    static {
        for (ArmorStandPart part : ArmorStandPart.values()) {
            ARMOR_STAND_POSE.put(part, get("armor_stand_pose/" + part.name().toLowerCase(Locale.ROOT), Quaterniondc.class));
        }
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ENTITY_EQUIPMENT.put(slot, get("entity_equipment/" + slot.name().toLowerCase(Locale.ROOT), ItemStack.class));
        }
    }

    private PropertyTypes() {
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, Class<T> type) {
        return REGISTRY.get(Key.key("easyarmorstands", name), type);
    }
}
