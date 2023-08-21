package me.m56738.easyarmorstands.display.api.property.type;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
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
    public static final PropertyTypeRegistry REGISTRY = PropertyTypeRegistry.Holder.instance;
    public static final PropertyType<BlockData> BLOCK_DISPLAY_BLOCK = get("block_display_block", BlockData.class);
    public static final PropertyType<Billboard> DISPLAY_BILLBOARD = get("display_billboard", Billboard.class);
    public static final PropertyType<Float> DISPLAY_BOX_HEIGHT = get("display_box_height", Float.class);
    public static final PropertyType<Float> DISPLAY_BOX_WIDTH = get("display_box_width", Float.class);
    public static final PropertyType<Brightness> DISPLAY_BRIGHTNESS = get("display_brightness", Brightness.class);
    public static final PropertyType<Quaternionfc> DISPLAY_LEFT_ROTATION = get("display_left_rotation", Quaternionfc.class);
    public static final PropertyType<Quaternionfc> DISPLAY_RIGHT_ROTATION = get("display_right_rotation", Quaternionfc.class);
    public static final PropertyType<Vector3fc> DISPLAY_SCALE = get("display_scale", Vector3fc.class);
    public static final PropertyType<Vector3fc> DISPLAY_TRANSLATION = get("display_translation", Vector3fc.class);
    public static final PropertyType<ItemStack> ITEM_DISPLAY_ITEM = get("item_display_item", ItemStack.class);
    public static final PropertyType<ItemDisplayTransform> ITEM_DISPLAY_TRANSFORM = get("item_display_transform", ItemDisplayTransform.class);
    public static final PropertyType<TextAlignment> TEXT_DISPLAY_ALIGNMENT = get("text_display_alignment", TextAlignment.class);
    public static final PropertyType<Color> TEXT_DISPLAY_BACKGROUND = get("text_display_background", Color.class);
    public static final PropertyType<Integer> TEXT_DISPLAY_LINE_WIDTH = get("text_display_line_width", Integer.class);
    public static final PropertyType<Boolean> TEXT_DISPLAY_SEE_THROUGH = get("text_display_see_through", Boolean.class);
    public static final PropertyType<Boolean> TEXT_DISPLAY_SHADOW = get("text_display_shadow", Boolean.class);
    public static final PropertyType<Component> TEXT_DISPLAY_TEXT = get("text_display_text", Component.class);

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, Class<T> type) {
        return REGISTRY.get(Key.key("easyarmorstands", name), type);
    }
}
