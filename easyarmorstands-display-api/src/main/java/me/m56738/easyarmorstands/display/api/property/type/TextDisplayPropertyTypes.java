package me.m56738.easyarmorstands.display.api.property.type;

import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.entity.TextDisplay.TextAlignment;

import java.util.Optional;

public class TextDisplayPropertyTypes {
    public static final PropertyType<TextAlignment> ALIGNMENT = get("text_display/alignment", TextAlignment.class);
    public static final PropertyType<Optional<Color>> BACKGROUND = get("text_display/background", new TypeToken<Optional<Color>>() {
    });
    public static final PropertyType<Integer> LINE_WIDTH = get("text_display/line_width", Integer.class);
    public static final PropertyType<Boolean> SEE_THROUGH = get("text_display/see_through", Boolean.class);
    public static final PropertyType<Boolean> SHADOW = get("text_display/shadow", Boolean.class);
    public static final PropertyType<Component> TEXT = get("text_display/text", Component.class);

    private TextDisplayPropertyTypes() {
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, TypeToken<T> type) {
        return EasyArmorStands.get().propertyTypeRegistry().get(Key.key("easyarmorstands", name), type);
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, Class<T> type) {
        return get(name, TypeToken.get(type));
    }
}
