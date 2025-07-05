package me.m56738.easyarmorstands.display.property.type;

import me.m56738.easyarmorstands.lib.geantyref.TypeToken;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.property.type.ConfigurablePropertyType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class GlowColorPropertyType extends ConfigurablePropertyType<Optional<Color>> {
    public GlowColorPropertyType(@NotNull Key key) {
        super(key, new TypeToken<Optional<Color>>() {
        });
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull Optional<Color> value) {
        if (value.isPresent()) {
            Color color = value.get();
            TextColor textColor = TextColor.color(color.asRGB());
            return Component.text(textColor.asHexString(), textColor);
        } else {
            return Message.component("easyarmorstands.property.display.glow.color.default").color(NamedTextColor.WHITE);
        }
    }
}
