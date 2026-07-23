package me.m56738.easyarmorstands.platform.paper.entity;

import org.bukkit.entity.Player;

record PaperPlayerImpl(Player player) implements PaperPlayer {
    @Override
    public Player getNative() {
        return player;
    }
}
