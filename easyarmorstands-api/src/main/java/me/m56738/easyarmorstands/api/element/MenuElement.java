package me.m56738.easyarmorstands.api.element;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * An element which is able to show a menu.
 */
public interface MenuElement extends EditableElement {
    /**
     * Opens the menu.
     *
     * @param player the player
     */
    void openMenu(@NotNull Player player);
}
