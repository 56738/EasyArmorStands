package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.platform.entity.display.TextAlignment;
import me.m56738.easyarmorstands.api.util.Color;
import net.kyori.adventure.text.Component;

import java.util.Optional;

import static me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes.get;

public final class TextDisplayPropertyTypes {
    public static final PropertyType<TextAlignment> ALIGNMENT = get("text_display/alignment");
    public static final PropertyType<Optional<Color>> BACKGROUND = get("text_display/background");
    public static final PropertyType<Integer> LINE_WIDTH = get("text_display/line_width");
    public static final PropertyType<Boolean> SEE_THROUGH = get("text_display/see_through");
    public static final PropertyType<Boolean> SHADOW = get("text_display/shadow");
    public static final PropertyType<Component> TEXT = get("text_display/text");

    private TextDisplayPropertyTypes() {
    }
}
