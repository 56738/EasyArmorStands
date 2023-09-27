package me.m56738.easyarmorstands.api.element;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface MenuElement extends Element {
    void openMenu(@NotNull Player player);

    @Contract(pure = true)
    boolean canEdit(@NotNull Player player);
}
