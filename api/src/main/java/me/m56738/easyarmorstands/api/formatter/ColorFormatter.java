package me.m56738.easyarmorstands.api.formatter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;

public class ColorFormatter implements ValueFormatter<Color> {
    private final Component transparentFallback;

    public ColorFormatter(Component transparentFallback) {
        this.transparentFallback = transparentFallback;
    }

    @Override
    public Component format(Color value) {
        if (value.getAlpha() == 0) {
            return transparentFallback;
        }
        TextColor textColor = TextColor.color(value.asRGB());
        return Component.text(textColor.asHexString(), textColor);
    }
}
