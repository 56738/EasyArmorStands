package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.platform.entity.display.TextAlignment;
import me.m56738.easyarmorstands.api.util.Color;
import net.kyori.adventure.text.Component;

import java.util.Optional;

import static net.kyori.adventure.key.Key.key;
import static net.kyori.adventure.text.Component.translatable;

public final class TextDisplayPropertyTypes {
    public static final PropertyType<TextAlignment> ALIGNMENT = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "text_display/alignment"))
            .name(translatable("easyarmorstands.property.text-display.alignment.name"))
            .permission("easyarmorstands.property.display.text.alignment"));
    public static final PropertyType<Optional<Color>> BACKGROUND = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "text_display/background"))
            .name(translatable("easyarmorstands.property.text-display.background.name"))
            .permission("easyarmorstands.property.display.text.background"));
    public static final PropertyType<Integer> LINE_WIDTH = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "text_display/line_width"))
            .name(translatable("easyarmorstands.property.text-display.line-width.name"))
            .permission("easyarmorstands.property.display.text.linewidth"));
    public static final PropertyType<Boolean> SEE_THROUGH = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "text_display/see_through"))
            .name(translatable("easyarmorstands.property.text-display.see-through.name"))
            .permission("easyarmorstands.property.display.text.seethrough"));
    public static final PropertyType<Boolean> SHADOW = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "text_display/shadow"))
            .name(translatable("easyarmorstands.property.text-display.shadow.name"))
            .permission("easyarmorstands.property.display.text.shadow"));
    public static final PropertyType<Component> TEXT = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "text_display/text"))
            .name(translatable("easyarmorstands.property.text-display.text.name"))
            .permission("easyarmorstands.property.display.text"));

    private TextDisplayPropertyTypes() {
    }
}
