package me.m56738.easyarmorstands.api.editor.button;

import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface MoveButtonBuilder {
    @Contract(value = "_ -> this", pure = true)
    @NotNull MoveButtonBuilder setTool(@NotNull MoveTool tool);

    @Contract(value = "_ -> this", pure = true)
    @NotNull MoveButtonBuilder setName(@NotNull Component name);

    @Contract(value = "_ -> this", pure = true)
    @NotNull MoveButtonBuilder setColor(@NotNull ParticleColor color);

    @Contract(value = "_ -> this", pure = true)
    @NotNull MoveButtonBuilder setPriority(int priority);

    @Contract(value = "-> new", pure = true)
    @NotNull MoveButton build();
}
