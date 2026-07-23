package me.m56738.easyarmorstands.platform.paper.entity;

import me.m56738.easyarmorstands.platform.color.ARGBColor;
import me.m56738.easyarmorstands.platform.entity.TextDisplay;
import me.m56738.easyarmorstands.platform.paper.PaperAdapter;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.jspecify.annotations.Nullable;

public interface PaperTextDisplay extends TextDisplay, PaperDisplay {
    static PaperTextDisplay fromNative(org.bukkit.entity.TextDisplay entity) {
        return new PaperTextDisplayImpl(entity);
    }

    org.bukkit.entity.TextDisplay getNative();

    static org.bukkit.entity.TextDisplay toNative(TextDisplay entity) {
        return ((PaperTextDisplay) entity).getNative();
    }

    @Override
    default boolean isDefaultBackground() {
        return getNative().isDefaultBackground();
    }

    @Override
    default void setDefaultBackground(boolean value) {
        getNative().setDefaultBackground(value);
    }

    @Override
    default @Nullable ARGBColor getBackgroundColor() {
        Color color = getNative().getBackgroundColor();
        if (color == null) {
            return null;
        }
        return ARGBColor.of(color.asARGB());
    }

    @Override
    default void setBackgroundColor(@Nullable ARGBColor color) {
        getNative().setBackgroundColor(color != null ? Color.fromARGB(color.value()) : null);
    }

    @Override
    default TextAlignment getAlignment() {
        return PaperAdapter.fromNative(getNative().getAlignment());
    }

    @Override
    default void setAlignment(TextAlignment alignment) {
        getNative().setAlignment(PaperAdapter.toNative(alignment));
    }

    @Override
    default int getLineWidth() {
        return getNative().getLineWidth();
    }

    @Override
    default void setLineWidth(int width) {
        getNative().setLineWidth(width);
    }

    @Override
    default boolean isSeeThrough() {
        return getNative().isSeeThrough();
    }

    @Override
    default void setSeeThrough(boolean seeThrough) {
        getNative().setSeeThrough(seeThrough);
    }

    @Override
    default boolean isShadowed() {
        return getNative().isShadowed();
    }

    @Override
    default void setShadowed(boolean shadow) {
        getNative().setShadowed(shadow);
    }

    @Override
    default Component getText() {
        return getNative().text();
    }

    @Override
    default void setText(Component text) {
        getNative().text(text);
    }
}
