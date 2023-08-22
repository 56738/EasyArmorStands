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
        registry.register(new BooleanTogglePropertyType(key("armor_stand/arms")));
        registry.register(new BooleanTogglePropertyType(key("armor_stand/base_plate")));
        registry.register(new BooleanPropertyType(key("armor_stand/can_tick")));
        registry.register(new GravityPropertyType(key("armor_stand/gravity")));
        registry.register(new BooleanTogglePropertyType(key("armor_stand/invulnerable")));
        registry.register(new BooleanTogglePropertyType(key("armor_stand/lock")));
        registry.register(new BooleanTogglePropertyType(key("armor_stand/marker")));
        registry.register(new EnumTogglePropertyType<>(key("armor_stand/size"), ArmorStandSize.class));
        registry.register(new ComponentPropertyType(key("entity/custom_name"), "/eas name set"));
        registry.register(new BooleanPropertyType(key("entity/custom_name/visible")));
        registry.register(new BooleanTogglePropertyType(key("entity/glowing")));
        registry.register(new LocationPropertyType(key("entity/location")));
        registry.register(new BooleanTogglePropertyType(key("entity/visible")));
        for (ArmorStandPart part : ArmorStandPart.values()) {
            registry.register(new EulerAnglePropertyType(key("armor_stand/pose/" + part.name().toLowerCase(Locale.ROOT))));
        }
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            registry.register(new ItemPropertyType(key("entity/equipment/" + slot.name().toLowerCase(Locale.ROOT))));
        }
    }

    private static Key key(@KeyPattern.Value String name) {
        return Key.key("easyarmorstands", name);
    }
}
