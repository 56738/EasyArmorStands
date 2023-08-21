package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Locale;

public class DefaultPropertyTypes {
    @SuppressWarnings("PatternValidation")
    public DefaultPropertyTypes(PropertyTypeRegistry registry) {
        registry.register(new BooleanTogglePropertyType(key("armor_stand_arms")));
        registry.register(new BooleanTogglePropertyType(key("armor_stand_base_plate")));
        registry.register(new BooleanPropertyType(key("armor_stand_can_tick")));
        registry.register(new GravityPropertyType(key("armor_stand_gravity")));
        registry.register(new BooleanTogglePropertyType(key("armor_stand_invulnerability")));
        registry.register(new BooleanTogglePropertyType(key("armor_stand_lock")));
        registry.register(new BooleanTogglePropertyType(key("armor_stand_marker")));
        registry.register(new EnumTogglePropertyType<>(key("armor_stand_size"), ArmorStandSize.class));
        registry.register(new ComponentPropertyType(key("entity_custom_name"), "/eas name set"));
        registry.register(new BooleanPropertyType(key("entity_custom_name_visible")));
        registry.register(new BooleanTogglePropertyType(key("entity_glowing")));
        registry.register(new LocationPropertyType(key("entity_location")));
        registry.register(new BooleanTogglePropertyType(key("entity_visibility")));
        for (ArmorStandPart part : ArmorStandPart.values()) {
            registry.register(new QuaterniondcPropertyType(key("armor_stand_pose/" + part.name().toLowerCase(Locale.ROOT))));
        }
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            registry.register(new ItemPropertyType(key("entity_equipment/" + slot.name().toLowerCase(Locale.ROOT))));
        }
    }

    private static Key key(@KeyPattern.Value String name) {
        return Key.key("easyarmorstands", name);
    }
}
