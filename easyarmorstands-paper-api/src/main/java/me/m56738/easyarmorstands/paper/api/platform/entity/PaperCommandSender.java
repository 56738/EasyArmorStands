package me.m56738.easyarmorstands.paper.api.platform.entity;

import me.m56738.easyarmorstands.api.platform.entity.CommandSender;

public interface PaperCommandSender extends CommandSender {
    org.bukkit.command.CommandSender getNative();
}
