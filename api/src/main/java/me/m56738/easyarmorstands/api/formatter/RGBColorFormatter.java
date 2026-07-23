package me.m56738.easyarmorstands.api.formatter;

import me.m56738.easyarmorstands.platform.color.RGBColor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class RGBColorFormatter implements ValueFormatter<RGBColor> {
    @Override
    public Component format(RGBColor value) {
        TextColor textColor = TextColor.color(value.value());
        return Component.text(textColor.asHexString(), textColor);
    }
}
