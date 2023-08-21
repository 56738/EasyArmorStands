package me.m56738.easyarmorstands.display.property.display;

import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import me.m56738.easyarmorstands.display.property.type.BlockDataPropertyType;
import me.m56738.easyarmorstands.display.property.type.BrightnessPropertyType;
import me.m56738.easyarmorstands.display.property.type.TextBackgroundTogglePropertyType;
import me.m56738.easyarmorstands.property.type.BooleanTogglePropertyType;
import me.m56738.easyarmorstands.property.type.ComponentPropertyType;
import me.m56738.easyarmorstands.property.type.EnumTogglePropertyType;
import me.m56738.easyarmorstands.property.type.FloatPropertyType;
import me.m56738.easyarmorstands.property.type.IntegerPropertyType;
import me.m56738.easyarmorstands.property.type.ItemPropertyType;
import me.m56738.easyarmorstands.property.type.QuaternionfcPropertyType;
import me.m56738.easyarmorstands.property.type.Vector3fcPropertyType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;

public class DefaultDisplayPropertyTypes {
    public DefaultDisplayPropertyTypes(PropertyTypeRegistry registry) {
        registry.register(new BlockDataPropertyType(key("block_display_block")));
        registry.register(new EnumTogglePropertyType<>(key("display_billboard"), Display.Billboard.class));
        registry.register(new FloatPropertyType(key("display_box_height")));
        registry.register(new FloatPropertyType(key("display_box_width")));
        registry.register(new BrightnessPropertyType(key("display_brightness")));
        registry.register(new QuaternionfcPropertyType(key("display_left_rotation")));
        registry.register(new QuaternionfcPropertyType(key("display_right_rotation")));
        registry.register(new Vector3fcPropertyType(key("display_scale")));
        registry.register(new Vector3fcPropertyType(key("display_translation")));
        registry.register(new ItemPropertyType(key("item_display_item")));
        registry.register(new EnumTogglePropertyType<>(key("item_display_transform"), ItemDisplay.ItemDisplayTransform.class));
        registry.register(new EnumTogglePropertyType<>(key("text_display_alignment"), TextDisplay.TextAlignment.class));
        registry.register(new TextBackgroundTogglePropertyType(key("text_display_background")));
        registry.register(new IntegerPropertyType(key("text_display_line_width")));
        registry.register(new BooleanTogglePropertyType(key("text_display_see_through")));
        registry.register(new BooleanTogglePropertyType(key("text_display_shadow")));
        registry.register(new ComponentPropertyType(key("text_display_text"), "/eas text set"));
    }

    private static Key key(@KeyPattern.Value String name) {
        return Key.key("easyarmorstands", name);
    }
}
