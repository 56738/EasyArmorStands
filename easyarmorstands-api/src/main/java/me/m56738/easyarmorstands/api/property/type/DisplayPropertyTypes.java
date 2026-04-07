package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.formatter.NumberFormatter;
import me.m56738.easyarmorstands.api.formatter.QuaternionfcFormatter;
import me.m56738.easyarmorstands.api.formatter.Vector3fcFormatter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.entity.Display.Brightness;
import org.joml.Quaternionfc;
import org.joml.Vector3fc;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

import static me.m56738.easyarmorstands.api.EasyArmorStands.key;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;

@NullMarked
public final class DisplayPropertyTypes {
    public static final PropertyType<Billboard> BILLBOARD = PropertyType.builder(key("display/billboard"), Billboard.class)
            .name(translatable("easyarmorstands.property.display.billboard.name"))
            .formatter(value -> switch (value) {
                case FIXED -> translatable("easyarmorstands.property.display.billboard.fixed");
                case VERTICAL -> translatable("easyarmorstands.property.display.billboard.vertical");
                case HORIZONTAL -> translatable("easyarmorstands.property.display.billboard.horizontal");
                case CENTER -> translatable("easyarmorstands.property.display.billboard.center");
            })
            .permission("easyarmorstands.property.display.billboard")
            .build();
    public static final PropertyType<Float> BOX_HEIGHT = PropertyType.builder(key("display/box/height"), Float.class)
            .name(translatable("easyarmorstands.property.display.box.height.name"))
            .formatter(NumberFormatter.pattern("0.0"))
            .permission("easyarmorstands.property.display.size")
            .build();
    public static final PropertyType<Float> BOX_WIDTH = PropertyType.builder(key("display/box/width"), Float.class)
            .name(translatable("easyarmorstands.property.display.box.width.name"))
            .formatter(NumberFormatter.pattern("0.0"))
            .permission("easyarmorstands.property.display.size")
            .build();
    public static final PropertyType<Optional<Brightness>> BRIGHTNESS = PropertyType.<Optional<Brightness>>builder(key("display/brightness"))
            .name(translatable("easyarmorstands.property.display.brightness.name"))
            .formatter(value -> value
                    .<Component>map(brightness -> text()
                            .append(translatable("easyarmorstands.property.display.brightness.sky", NamedTextColor.GOLD, text(brightness.getSkyLight())))
                            .append(text(", "))
                            .append(translatable("easyarmorstands.property.display.brightness.block", NamedTextColor.YELLOW, text(brightness.getBlockLight())))
                            .build())
                    .orElseGet(() -> translatable("easyarmorstands.property.display.brightness.default", NamedTextColor.GRAY, TextDecoration.ITALIC)))
            .permission("easyarmorstands.property.display.brightness")
            .build();
    public static final PropertyType<Quaternionfc> LEFT_ROTATION = PropertyType.builder(key("display/left_rotation"), Quaternionfc.class)
            .name(translatable("easyarmorstands.property.display.rotation.name"))
            .formatter(new QuaternionfcFormatter())
            .permission("easyarmorstands.property.display.rotation")
            .build();
    public static final PropertyType<Quaternionfc> RIGHT_ROTATION = PropertyType.builder(key("display/right_rotation"), Quaternionfc.class)
            .name(translatable("easyarmorstands.property.display.shearing.name"))
            .formatter(new QuaternionfcFormatter())
            .permission("easyarmorstands.property.display.shearing")
            .build();
    public static final PropertyType<Vector3fc> SCALE = PropertyType.builder(key("display/scale"), Vector3fc.class)
            .name(translatable("easyarmorstands.property.display.scale.name"))
            .formatter(new Vector3fcFormatter())
            .permission("easyarmorstands.property.display.scale")
            .build();
    public static final PropertyType<Vector3fc> TRANSLATION = PropertyType.builder(key("display/translation"), Vector3fc.class)
            .name(translatable("easyarmorstands.property.display.translation.name"))
            .formatter(new Vector3fcFormatter())
            .permission("easyarmorstands.property.display.translation")
            .build();
    public static final PropertyType<Optional<Color>> GLOW_COLOR = PropertyType.<Optional<Color>>builder(key("display/glowing/color"))
            .name(translatable("easyarmorstands.property.display.glow.color.name"))
            .formatter(value -> value
                    .<Component>map(color -> {
                        TextColor textColor = TextColor.color(color.asRGB());
                        return Component.text(textColor.asHexString(), textColor);
                    })
                    .orElseGet(() -> Component.translatable("easyarmorstands.property.display.glow.color.default", NamedTextColor.WHITE)))
            .permission("easyarmorstands.property.display.glow.color")
            .build();
    public static final PropertyType<Float> VIEW_RANGE = PropertyType.builder(key("display/view_range"), Float.class)
            .name(translatable("easyarmorstands.property.display.view-range.name"))
            .formatter(NumberFormatter.pattern("0.0"))
            .permission("easyarmorstands.property.display.viewrange")
            .build();

    private DisplayPropertyTypes() {
    }
}
