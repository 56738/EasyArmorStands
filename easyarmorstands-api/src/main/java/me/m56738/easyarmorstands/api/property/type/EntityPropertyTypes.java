package me.m56738.easyarmorstands.api.property.type;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings("PatternValidation")
public class EntityPropertyTypes {
    public static final PropertyType<Component> CUSTOM_NAME = get("entity/custom_name", Component.class);
    public static final PropertyType<Boolean> CUSTOM_NAME_VISIBLE = get("entity/custom_name/visible", Boolean.class);
    public static final Map<EquipmentSlot, PropertyType<ItemStack>> EQUIPMENT = new EnumMap<>(EquipmentSlot.class);
    public static final PropertyType<Boolean> GLOWING = get("entity/glowing", Boolean.class);
    public static final PropertyType<Location> LOCATION = get("entity/location", Location.class);
    public static final PropertyType<Boolean> VISIBLE = get("entity/visible", Boolean.class);

    static {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            EQUIPMENT.put(slot, get("entity/equipment/" + slot.name().toLowerCase(Locale.ROOT), ItemStack.class));
        }
    }

    private EntityPropertyTypes() {
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, Class<T> type) {
        return PropertyTypeRegistry.Holder.instance.get(Key.key("easyarmorstands", name), type);
    }
}
