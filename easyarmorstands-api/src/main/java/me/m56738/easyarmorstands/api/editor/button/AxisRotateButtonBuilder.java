package me.m56738.easyarmorstands.api.editor.button;

import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface AxisRotateButtonBuilder {
    @Contract(value = "_ -> this", pure = true)
    @NotNull AxisRotateButtonBuilder setTool(AxisRotateTool tool);

    @Contract(value = "_ -> this", pure = true)
    @NotNull AxisRotateButtonBuilder setLength(double length);

    @Contract(value = "_ -> this", pure = true)
    @NotNull AxisRotateButtonBuilder setRadius(double radius);

    @Contract(value = "_ -> this", pure = true)
    @NotNull AxisRotateButtonBuilder setName(@NotNull Component name);

    @Contract(value = "_ -> this", pure = true)
    @NotNull AxisRotateButtonBuilder setColor(@NotNull ParticleColor color);

    @Contract(value = "-> new", pure = true)
    @NotNull AxisRotateButton build();
}
