package me.m56738.easyarmorstands.platform.entity;

import me.m56738.easyarmorstands.platform.color.ARGBColor;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

public interface TextDisplay extends Display {
    boolean isDefaultBackground();

    void setDefaultBackground(boolean value);

    @Nullable ARGBColor getBackgroundColor();

    void setBackgroundColor(@Nullable ARGBColor color);

    TextAlignment getAlignment();

    void setAlignment(TextAlignment alignment);

    int getLineWidth();

    void setLineWidth(int width);

    boolean isSeeThrough();

    void setSeeThrough(boolean seeThrough);

    boolean isShadowed();

    void setShadowed(boolean shadow);

    Component getText();

    void setText(Component text);

    enum TextAlignment {
        CENTER,
        LEFT,
        RIGHT,
    }
}
