package me.m56738.easyarmorstands.paper.api.platform.entity;

import me.m56738.easyarmorstands.api.platform.entity.Player;

public interface PaperPlayer extends PaperCommandSender, Player {
    @Override
    org.bukkit.entity.Player getNative();
}
