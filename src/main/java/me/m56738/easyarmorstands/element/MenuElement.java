package me.m56738.easyarmorstands.element;

import org.bukkit.entity.Player;

public interface MenuElement extends Element {
    void openMenu(Player player);

    boolean hasItemSlots();
}
