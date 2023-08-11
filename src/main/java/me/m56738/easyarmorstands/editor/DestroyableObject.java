package me.m56738.easyarmorstands.editor;

import org.bukkit.entity.Player;

public interface DestroyableObject extends EntityObject {
    boolean destroy(Player player);
}
