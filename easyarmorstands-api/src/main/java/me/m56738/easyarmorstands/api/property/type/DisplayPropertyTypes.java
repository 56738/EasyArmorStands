package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.platform.entity.display.Billboard;
import me.m56738.easyarmorstands.api.platform.entity.display.Brightness;
import me.m56738.easyarmorstands.api.util.Color;
import org.joml.Quaternionfc;
import org.joml.Vector3fc;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

import static net.kyori.adventure.key.Key.key;
import static net.kyori.adventure.text.Component.translatable;

@NullMarked
public final class DisplayPropertyTypes {
    public static final PropertyType<Billboard> BILLBOARD = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "display/billboard"))
            .name(translatable("easyarmorstands.property.display.billboard.name"))
            .permission("easyarmorstands.property.display.billboard"));
    public static final PropertyType<Float> BOX_HEIGHT = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "display/box/height"))
            .name(translatable("easyarmorstands.property.display.box.height.name"))
            .permission("easyarmorstands.property.display.size"));
    public static final PropertyType<Float> BOX_WIDTH = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "display/box/width"))
            .name(translatable("easyarmorstands.property.display.box.width.name"))
            .permission("easyarmorstands.property.display.size"));
    public static final PropertyType<Optional<Brightness>> BRIGHTNESS = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "display/brightness"))
            .name(translatable("easyarmorstands.property.display.brightness.name"))
            .permission("easyarmorstands.property.display.brightness"));
    public static final PropertyType<Quaternionfc> LEFT_ROTATION = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "display/left_rotation"))
            .name(translatable("easyarmorstands.property.display.rotation.name"))
            .permission("easyarmorstands.property.display.rotation"));
    public static final PropertyType<Quaternionfc> RIGHT_ROTATION = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "display/right_rotation"))
            .name(translatable("easyarmorstands.property.display.shearing.name"))
            .permission("easyarmorstands.property.display.shearing"));
    public static final PropertyType<Vector3fc> SCALE = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "display/scale"))
            .name(translatable("easyarmorstands.property.display.scale.name"))
            .permission("easyarmorstands.property.display.scale"));
    public static final PropertyType<Vector3fc> TRANSLATION = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "display/translation"))
            .name(translatable("easyarmorstands.property.display.translation.name"))
            .permission("easyarmorstands.property.display.translation"));
    public static final PropertyType<Optional<Color>> GLOW_COLOR = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "display/glowing/color"))
            .name(translatable("easyarmorstands.property.display.glow.color.name"))
            .permission("easyarmorstands.property.display.glow.color"));
    public static final PropertyType<Float> VIEW_RANGE = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "display/view_range"))
            .name(translatable("easyarmorstands.property.display.view-range.name"))
            .permission("easyarmorstands.property.display.viewrange"));

    private DisplayPropertyTypes() {
    }
}
