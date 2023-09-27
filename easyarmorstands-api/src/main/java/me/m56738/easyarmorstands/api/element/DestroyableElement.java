package me.m56738.easyarmorstands.api.element;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface DestroyableElement extends Element {
    void destroy();

    @Contract(pure = true)
    boolean canDestroy(@NotNull Player player);
}
