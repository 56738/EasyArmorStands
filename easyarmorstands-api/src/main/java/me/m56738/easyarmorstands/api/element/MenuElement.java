package me.m56738.easyarmorstands.api.element;

import org.bukkit.entity.Player;

public interface MenuElement extends Element {
    void openMenu(Player player);

    boolean canEdit(Player player);
}
