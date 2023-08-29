package me.m56738.easyarmorstands.api.editor.button;

import me.m56738.easyarmorstands.api.editor.axis.RotateAxis;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface RotateButtonBuilder {
    @Contract(value = "_ -> this", pure = true)
    @NotNull RotateButtonBuilder setAxis(RotateAxis axis);

    @Contract(value = "_ -> this", pure = true)
    @NotNull RotateButtonBuilder setLength(double length);

    @Contract(value = "_ -> this", pure = true)
    @NotNull RotateButtonBuilder setRadius(double radius);

    @Contract(value = "_ -> this", pure = true)
    @NotNull RotateButtonBuilder setName(@NotNull Component name);

    @Contract(value = "_ -> this", pure = true)
    @NotNull RotateButtonBuilder setColor(@NotNull ParticleColor color);

    @Contract(value = "-> new", pure = true)
    @NotNull RotateButton build();
}
