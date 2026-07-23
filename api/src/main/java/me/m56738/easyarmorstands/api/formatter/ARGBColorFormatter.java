package me.m56738.easyarmorstands.api.formatter;

import me.m56738.easyarmorstands.platform.color.ARGBColor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class ARGBColorFormatter implements ValueFormatter<ARGBColor> {
    private final Component transparentFallback;

    public ARGBColorFormatter(Component transparentFallback) {
        this.transparentFallback = transparentFallback;
    }

    @Override
    public Component format(ARGBColor value) {
        if (value.alpha() == 0) {
            return transparentFallback;
        }
        TextColor textColor = TextColor.color(value.value());
        return Component.text(textColor.asHexString(), textColor);
    }
}
