package me.m56738.easyarmorstands.api.element;

import org.bukkit.entity.Player;

public interface DestroyableElement extends Element {
    void destroy();

    boolean canDestroy(Player player);
}
