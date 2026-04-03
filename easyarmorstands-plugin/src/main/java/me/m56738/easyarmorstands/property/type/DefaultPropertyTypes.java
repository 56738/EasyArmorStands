package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.SkinPart;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.MainHand;

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
        registry.register(new BlockDataPropertyType(key("block_display/block")));
        registry.register(new EnumTogglePropertyType<>(key("display/billboard"), Display.Billboard.class));
        registry.register(new FloatPropertyType(key("display/box/height")));
        registry.register(new FloatPropertyType(key("display/box/width")));
        registry.register(new BrightnessPropertyType(key("display/brightness")));
        registry.register(new QuaternionfcPropertyType(key("display/left_rotation")));
        registry.register(new QuaternionfcPropertyType(key("display/right_rotation")));
        registry.register(new Vector3fcPropertyType(key("display/scale")));
        registry.register(new Vector3fcPropertyType(key("display/translation")));
        registry.register(new FloatPropertyType(key("display/view_range")));
        registry.register(new GlowColorPropertyType(key("display/glowing/color")));
        registry.register(new BooleanTogglePropertyType(key("entity/ai")));
        registry.register(new OptionalComponentPropertyType(key("entity/custom_name"), "/eas name set"));
        registry.register(new BooleanPropertyType(key("entity/custom_name/visible")));
        registry.register(new BooleanTogglePropertyType(key("entity/glowing")));
        registry.register(new LocationPropertyType(key("entity/location")));
        registry.register(new DoublePropertyType(key("entity/scale")));
        registry.register(new BooleanTogglePropertyType(key("entity/silent")));
        registry.register(new StringSetPropertyType(key("entity/tags")));
        registry.register(new BooleanTogglePropertyType(key("entity/visible")));
        registry.register(new BooleanTogglePropertyType(key("interaction/responsive")));
        registry.register(new ItemPropertyType(key("item_display/item")));
        registry.register(new EnumTogglePropertyType<>(key("item_display/transform"), ItemDisplay.ItemDisplayTransform.class));
        registry.register(new EnumTogglePropertyType<>(key("mannequin/main_hand"), MainHand.class));
        registry.register(new ProfilePropertyType(key("mannequin/profile")));
        registry.register(new BooleanTogglePropertyType(key("mannequin/immovable")));
        registry.register(new OptionalComponentPropertyType(key("mannequin/description"), "/eas description set"));
        registry.register(new EnumTogglePropertyType<>(key("text_display/alignment"), TextDisplay.TextAlignment.class));
        registry.register(new TextBackgroundTogglePropertyType(key("text_display/background")));
        registry.register(new IntegerPropertyType(key("text_display/line_width")));
        registry.register(new BooleanTogglePropertyType(key("text_display/see_through")));
        registry.register(new BooleanTogglePropertyType(key("text_display/shadow")));
        registry.register(new ComponentPropertyType(key("text_display/text"), "/eas text set"));
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
