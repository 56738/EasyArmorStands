package me.m56738.easyarmorstands.api.editor.button;

import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface AxisScaleButtonBuilder {
    @Contract(value = "_ -> this", pure = true)
    @NotNull AxisScaleButtonBuilder setTool(@NotNull AxisScaleTool tool);

    @Contract(value = "_ -> this", pure = true)
    @NotNull AxisScaleButtonBuilder setLength(double length);

    @Contract(value = "_ -> this", pure = true)
    @NotNull AxisScaleButtonBuilder setName(@NotNull Component name);

    @Contract(value = "_ -> this", pure = true)
    @NotNull AxisScaleButtonBuilder setColor(@NotNull ParticleColor color);

    @Contract(value = "-> new", pure = true)
    @NotNull AxisScaleButton build();
}
