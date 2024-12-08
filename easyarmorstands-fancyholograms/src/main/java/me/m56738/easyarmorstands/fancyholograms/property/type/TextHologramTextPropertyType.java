package me.m56738.easyarmorstands.fancyholograms.property.type;

import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.property.type.ConfigurablePropertyType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TextHologramTextPropertyType extends ConfigurablePropertyType<List<String>> {
    public static final TextHologramTextPropertyType INSTANCE = new TextHologramTextPropertyType();

    public TextHologramTextPropertyType() {
        super(Key.key("easyarmorstands", "fancyholograms/text"), new TypeToken<>() {
        });
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull List<String> value) {
        return Component.text(String.join("\n", value));
    }

    @Override
    public @NotNull List<String> cloneValue(@NotNull List<String> value) {
        return new ArrayList<>(value);
    }
}
