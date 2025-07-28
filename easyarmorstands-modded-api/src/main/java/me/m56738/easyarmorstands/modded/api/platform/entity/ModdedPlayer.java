package me.m56738.easyarmorstands.modded.api.platform.entity;

import me.m56738.easyarmorstands.api.platform.entity.Player;
import net.minecraft.server.level.ServerPlayer;

public interface ModdedPlayer extends ModdedCommandSender, ModdedEntity, Player {
    static ServerPlayer toNative(Player player) {
        return ((ModdedPlayer) player).getNative();
    }

    ServerPlayer getNative();
}
