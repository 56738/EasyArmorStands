package me.m56738.easyarmorstands.property.v1_19_4.display;

import me.m56738.easyarmorstands.property.type.BooleanTogglePropertyType;
import me.m56738.easyarmorstands.property.type.ComponentPropertyType;
import me.m56738.easyarmorstands.property.type.ConfigurablePropertyType;
import me.m56738.easyarmorstands.property.type.EnumTogglePropertyType;
import me.m56738.easyarmorstands.property.type.FloatPropertyType;
import me.m56738.easyarmorstands.property.type.IntegerPropertyType;
import me.m56738.easyarmorstands.property.type.ItemPropertyType;
import me.m56738.easyarmorstands.property.type.PropertyType;
import me.m56738.easyarmorstands.property.type.PropertyTypes;
import me.m56738.easyarmorstands.property.type.QuaternionfcPropertyType;
import me.m56738.easyarmorstands.property.type.Vector3fcPropertyType;
import me.m56738.easyarmorstands.property.v1_19_4.type.BlockDataPropertyType;
import me.m56738.easyarmorstands.property.v1_19_4.type.BrightnessPropertyType;
import me.m56738.easyarmorstands.property.v1_19_4.type.TextBackgroundTogglePropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.entity.Display.Brightness;
import org.bukkit.entity.ItemDisplay.ItemDisplayTransform;
import org.bukkit.entity.TextDisplay.TextAlignment;
import org.bukkit.inventory.ItemStack;
import org.joml.Quaternionfc;
import org.joml.Vector3fc;

public class DisplayPropertyTypes {
    public static final PropertyType<BlockData> BLOCK_DISPLAY_BLOCK = register(new BlockDataPropertyType("block-display-block"));
    public static final PropertyType<Billboard> DISPLAY_BILLBOARD = register(new EnumTogglePropertyType<>("display-billboard", Billboard.class));
    public static final PropertyType<Float> DISPLAY_BOX_HEIGHT = register(new FloatPropertyType("display-box-height"));
    public static final PropertyType<Float> DISPLAY_BOX_WIDTH = register(new FloatPropertyType("display-box-width"));
    public static final PropertyType<Brightness> DISPLAY_BRIGHTNESS = register(new BrightnessPropertyType("display-brightness"));
    public static final PropertyType<Quaternionfc> DISPLAY_LEFT_ROTATION = register(new QuaternionfcPropertyType("display-left-rotation"));
    public static final PropertyType<Quaternionfc> DISPLAY_RIGHT_ROTATION = register(new QuaternionfcPropertyType("display-right-rotation"));
    public static final PropertyType<Vector3fc> DISPLAY_SCALE = register(new Vector3fcPropertyType("display-scale"));
    public static final PropertyType<Vector3fc> DISPLAY_TRANSLATION = register(new Vector3fcPropertyType("display-translation"));
    public static final PropertyType<ItemStack> ITEM_DISPLAY_ITEM = register(new ItemPropertyType("item-display-item"));
    public static final PropertyType<ItemDisplayTransform> ITEM_DISPLAY_TRANSFORM = register(new EnumTogglePropertyType<>("item-display-transform", ItemDisplayTransform.class));
    public static final PropertyType<TextAlignment> TEXT_DISPLAY_ALIGNMENT = register(new EnumTogglePropertyType<>("text-display-alignment", TextAlignment.class));
    public static final PropertyType<Color> TEXT_DISPLAY_BACKGROUND = register(new TextBackgroundTogglePropertyType("text-display-background"));
    public static final PropertyType<Integer> TEXT_DISPLAY_LINE_WIDTH = register(new IntegerPropertyType("text-display-line-width"));
    public static final PropertyType<Boolean> TEXT_DISPLAY_SEE_THROUGH = register(new BooleanTogglePropertyType("text-display-see-through"));
    public static final PropertyType<Boolean> TEXT_DISPLAY_SHADOW = register(new BooleanTogglePropertyType("text-display-shadow"));
    public static final PropertyType<Component> TEXT_DISPLAY_TEXT = register(new ComponentPropertyType("text-display-text", "/eas text set"));

    private DisplayPropertyTypes() {
    }

    private static <T> PropertyType<T> register(ConfigurablePropertyType<T> type) {
        return PropertyTypes.register(type);
    }
}
