package me.m56738.easyarmorstands.api.editor.button;

import me.m56738.easyarmorstands.api.editor.axis.MoveAxis;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface MoveButtonBuilder {
    @Contract(value = "_ -> this", pure = true)
    @NotNull MoveButtonBuilder setAxis(@NotNull MoveAxis moveAxis);

    @Contract(value = "_ -> this", pure = true)
    @NotNull MoveButtonBuilder setLength(double length);

    @Contract(value = "_ -> this", pure = true)
    @NotNull MoveButtonBuilder setName(@NotNull Component name);

    @Contract(value = "_ -> this", pure = true)
    @NotNull MoveButtonBuilder setColor(@NotNull ParticleColor color);

    @Contract(value = "-> new", pure = true)
    @NotNull MoveButton build();
}
