package me.m56738.easyarmorstands.display.api.property.type;

import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import org.bukkit.Color;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.entity.Display.Brightness;
import org.joml.Quaternionfc;
import org.joml.Vector3fc;

import java.util.Optional;

public class DisplayPropertyTypes {
    public static final PropertyType<Billboard> BILLBOARD = get("display/billboard", Billboard.class);
    public static final PropertyType<Float> BOX_HEIGHT = get("display/box/height", Float.class);
    public static final PropertyType<Float> BOX_WIDTH = get("display/box/width", Float.class);
    public static final PropertyType<Optional<Brightness>> BRIGHTNESS = get("display/brightness", new TypeToken<Optional<Brightness>>() {
    });
    public static final PropertyType<Quaternionfc> LEFT_ROTATION = get("display/left_rotation", Quaternionfc.class);
    public static final PropertyType<Quaternionfc> RIGHT_ROTATION = get("display/right_rotation", Quaternionfc.class);
    public static final PropertyType<Vector3fc> SCALE = get("display/scale", Vector3fc.class);
    public static final PropertyType<Vector3fc> TRANSLATION = get("display/translation", Vector3fc.class);
    public static final PropertyType<Optional<Color>> GLOW_COLOR = get("display/glowing/color", new TypeToken<Optional<Color>>() {
    });
    public static final PropertyType<Float> VIEW_RANGE = get("display/view_range", Float.class);

    private DisplayPropertyTypes() {
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, TypeToken<T> type) {
        return EasyArmorStands.get().propertyTypeRegistry().get(Key.key("easyarmorstands", name), type);
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, Class<T> type) {
        return get(name, TypeToken.get(type));
    }
}
