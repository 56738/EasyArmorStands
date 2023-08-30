package me.m56738.easyarmorstands.api.editor.button;

import me.m56738.easyarmorstands.api.editor.axis.ScaleAxis;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface ScaleButtonBuilder {
    @Contract(value = "_ -> this", pure = true)
    @NotNull ScaleButtonBuilder setAxis(@NotNull ScaleAxis scaleAxis);

    @Contract(value = "_ -> this", pure = true)
    @NotNull ScaleButtonBuilder setLength(double length);

    @Contract(value = "_ -> this", pure = true)
    @NotNull ScaleButtonBuilder setName(@NotNull Component name);

    @Contract(value = "_ -> this", pure = true)
    @NotNull ScaleButtonBuilder setColor(@NotNull ParticleColor color);

    @Contract(value = "-> new", pure = true)
    @NotNull ScaleButton build();
}
