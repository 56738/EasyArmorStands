package me.m56738.easyarmorstands.display.property.display;

import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import me.m56738.easyarmorstands.display.property.type.BlockDataPropertyType;
import me.m56738.easyarmorstands.display.property.type.BrightnessPropertyType;
import me.m56738.easyarmorstands.display.property.type.GlowColorPropertyType;
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
        registry.register(new BlockDataPropertyType(key("block_display/block")));
        registry.register(new EnumTogglePropertyType<>(key("display/billboard"), Display.Billboard.class));
        registry.register(new FloatPropertyType(key("display/box/height")));
        registry.register(new FloatPropertyType(key("display/box/width")));
        registry.register(new BrightnessPropertyType(key("display/brightness")));
        registry.register(new QuaternionfcPropertyType(key("display/left_rotation")));
        registry.register(new QuaternionfcPropertyType(key("display/right_rotation")));
        registry.register(new Vector3fcPropertyType(key("display/scale")));
        registry.register(new Vector3fcPropertyType(key("display/translation")));
        registry.register(new GlowColorPropertyType(key("display/glowing/color")));
        registry.register(new ItemPropertyType(key("item_display/item")));
        registry.register(new EnumTogglePropertyType<>(key("item_display/transform"), ItemDisplay.ItemDisplayTransform.class));
        registry.register(new EnumTogglePropertyType<>(key("text_display/alignment"), TextDisplay.TextAlignment.class));
        registry.register(new TextBackgroundTogglePropertyType(key("text_display/background")));
        registry.register(new IntegerPropertyType(key("text_display/line_width")));
        registry.register(new BooleanTogglePropertyType(key("text_display/see_through")));
        registry.register(new BooleanTogglePropertyType(key("text_display/shadow")));
        registry.register(new ComponentPropertyType(key("text_display/text"), "/eas text set"));
    }

    private static Key key(@KeyPattern.Value String name) {
        return Key.key("easyarmorstands", name);
    }
}
