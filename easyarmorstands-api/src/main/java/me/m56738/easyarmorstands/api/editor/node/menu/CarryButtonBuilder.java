package me.m56738.easyarmorstands.api.editor.node.menu;

import me.m56738.easyarmorstands.api.editor.axis.CarryAxis;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface CarryButtonBuilder {
    @Contract(value = "_ -> this", pure = true)
    @NotNull CarryButtonBuilder setAxis(@NotNull CarryAxis axis);

    @Contract(value = "_ -> this", pure = true)
    @NotNull CarryButtonBuilder setName(@NotNull Component name);

    @Contract(value = "_ -> this", pure = true)
    @NotNull CarryButtonBuilder setColor(@NotNull ParticleColor color);

    @Contract(value = "_ -> this", pure = true)
    @NotNull CarryButtonBuilder setPriority(int priority);

    @Contract(value = "-> new", pure = true)
    @NotNull CarryButton build();
}
