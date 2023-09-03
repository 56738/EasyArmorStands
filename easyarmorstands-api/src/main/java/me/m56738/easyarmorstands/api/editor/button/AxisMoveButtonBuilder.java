package me.m56738.easyarmorstands.api.editor.button;

import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface AxisMoveButtonBuilder {
    @Contract(value = "_ -> this", pure = true)
    @NotNull AxisMoveButtonBuilder setTool(@NotNull AxisMoveTool moveAxis);

    @Contract(value = "_ -> this", pure = true)
    @NotNull AxisMoveButtonBuilder setLength(double length);

    @Contract(value = "_ -> this", pure = true)
    @NotNull AxisMoveButtonBuilder setName(@NotNull Component name);

    @Contract(value = "_ -> this", pure = true)
    @NotNull AxisMoveButtonBuilder setColor(@NotNull ParticleColor color);

    @Contract(value = "-> new", pure = true)
    @NotNull AxisMoveButton build();
}
