package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.Hand;
import me.m56738.easyarmorstands.api.SkinPart;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.KeyPattern;
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
        registry.register(new BooleanTogglePropertyType(key("entity/ai")));
        registry.register(new OptionalComponentPropertyType(key("entity/custom_name"), "/eas name set"));
        registry.register(new BooleanPropertyType(key("entity/custom_name/visible")));
        registry.register(new BooleanTogglePropertyType(key("entity/glowing")));
        registry.register(new LocationPropertyType(key("entity/location")));
        registry.register(new DoublePropertyType(key("entity/scale")));
        registry.register(new BooleanTogglePropertyType(key("entity/silent")));
        registry.register(new StringSetPropertyType(key("entity/tags")));
        registry.register(new BooleanTogglePropertyType(key("entity/visible")));
        registry.register(new EnumTogglePropertyType<>(key("mannequin/main_hand"), Hand.class));
        registry.register(new ProfilePropertyType(key("mannequin/profile")));
        registry.register(new BooleanTogglePropertyType(key("mannequin/immovable")));
        registry.register(new OptionalComponentPropertyType(key("mannequin/description"), "/eas description set"));
        for (SkinPart part : SkinPart.values()) {
            String name = part.name().toLowerCase(Locale.ROOT);
            registry.register(new BooleanTogglePropertyType(key("mannequin/part/" + name + "/visible")));
        }
        for (ArmorStandPart part : ArmorStandPart.values()) {
            String name = part.name().toLowerCase(Locale.ROOT);
            registry.register(new EulerAnglePropertyType(key("armor_stand/pose/" + name)));
        }
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            String name = slot.name().toLowerCase(Locale.ROOT);
            registry.register(new ItemPropertyType(key("entity/equipment/" + name)));
        }
    }

    private static Key key(@KeyPattern.Value String name) {
        return Key.key("easyarmorstands", name);
    }
}
