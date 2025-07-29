package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.platform.entity.display.Billboard;
import me.m56738.easyarmorstands.api.platform.entity.display.Brightness;
import me.m56738.easyarmorstands.api.util.Color;
import org.joml.Quaternionfc;
import org.joml.Vector3fc;

import java.util.Optional;

import static me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes.get;

public final class DisplayPropertyTypes {
    public static final PropertyType<Billboard> BILLBOARD = get("display/billboard");
    public static final PropertyType<Float> BOX_HEIGHT = get("display/box/height");
    public static final PropertyType<Float> BOX_WIDTH = get("display/box/width");
    public static final PropertyType<Optional<Brightness>> BRIGHTNESS = get("display/brightness");
    public static final PropertyType<Quaternionfc> LEFT_ROTATION = get("display/left_rotation");
    public static final PropertyType<Quaternionfc> RIGHT_ROTATION = get("display/right_rotation");
    public static final PropertyType<Vector3fc> SCALE = get("display/scale");
    public static final PropertyType<Vector3fc> TRANSLATION = get("display/translation");
    public static final PropertyType<Optional<Color>> GLOW_COLOR = get("display/glowing/color");
    public static final PropertyType<Float> VIEW_RANGE = get("display/view_range");

    private DisplayPropertyTypes() {
    }
}
