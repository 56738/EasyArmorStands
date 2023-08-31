package me.m56738.easyarmorstands.api.element;

import org.bukkit.entity.Player;

public interface EditableElement extends Element {
    boolean canEdit(Player player);
}
