package me.m56738.easyarmorstands.api.property.type;

import com.google.common.reflect.TypeToken;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.formatter.BooleanFormatter;
import me.m56738.easyarmorstands.api.formatter.ColorFormatter;
import me.m56738.easyarmorstands.api.formatter.OptionalFormatter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Color;
import org.bukkit.entity.TextDisplay.TextAlignment;
import org.jspecify.annotations.NullMarked;

import java.util.Locale;
import java.util.Optional;

import static me.m56738.easyarmorstands.api.EasyArmorStands.key;
import static net.kyori.adventure.text.Component.translatable;

@NullMarked
public final class TextDisplayPropertyTypes {
    public static final PropertyType<TextAlignment> ALIGNMENT = PropertyType.builder(key("text_display/alignment"), TextAlignment.class)
            .name(translatable("easyarmorstands.property.text-display.alignment.name"))
            .formatter(value -> translatable("easyarmorstands.property.text-display.alignment." + value.name().toLowerCase(Locale.ROOT)))
            .permission("easyarmorstands.property.display.text.alignment")
            .build();
    public static final PropertyType<Optional<Color>> BACKGROUND = PropertyType.<Optional<Color>>builder(key("text_display/background"))
            .name(translatable("easyarmorstands.property.text-display.background.name"))
            .formatter(new OptionalFormatter<>(
                    new ColorFormatter(Component.translatable("easyarmorstands.property.text-display.background.none")),
                    Component.translatable("easyarmorstands.property.text-display.background.default", NamedTextColor.DARK_GRAY)))
            .permission("easyarmorstands.property.display.text.background")
            .build();
    public static final PropertyType<Integer> LINE_WIDTH = PropertyType.builder(key("text_display/line_width"), Integer.class)
            .name(translatable("easyarmorstands.property.text-display.line-width.name"))
            .permission("easyarmorstands.property.display.text.linewidth")
            .build();
    public static final PropertyType<Boolean> SEE_THROUGH = PropertyType.builder(key("text_display/see_through"), Boolean.class)
            .name(translatable("easyarmorstands.property.text-display.see-through.name"))
            .formatter(BooleanFormatter.toggle())
            .permission("easyarmorstands.property.display.text.seethrough")
            .build();
    public static final PropertyType<Boolean> SHADOW = PropertyType.builder(key("text_display/shadow"), Boolean.class)
            .name(translatable("easyarmorstands.property.text-display.shadow.name"))
            .formatter(BooleanFormatter.toggle())
            .permission("easyarmorstands.property.display.text.shadow")
            .build();
    public static final PropertyType<Component> TEXT = PropertyType.builder(key("text_display/text"), Component.class)
            .name(translatable("easyarmorstands.property.text-display.text.name"))
            .formatter(value -> value)
            .permission("easyarmorstands.property.display.text")
            .build();

    private TextDisplayPropertyTypes() {
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, TypeToken<T> type) {
        return EasyArmorStands.get().propertyTypeRegistry().get(Key.key("easyarmorstands", name), type);
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, Class<T> type) {
        return get(name, TypeToken.of(type));
    }
}
