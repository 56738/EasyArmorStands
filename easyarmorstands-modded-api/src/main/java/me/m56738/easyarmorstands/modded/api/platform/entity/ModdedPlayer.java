package me.m56738.easyarmorstands.modded.api.platform.entity;

import me.m56738.easyarmorstands.api.platform.entity.Player;

public interface ModdedPlayer extends ModdedCommandSender, Player {
    net.minecraft.world.entity.player.Player getNative();
}
