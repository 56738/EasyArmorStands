package me.m56738.easyarmorstands.fabric.platform.entity;

import me.lucko.fabric.api.permissions.v0.Permissions;
import me.m56738.easyarmorstands.modded.platform.entity.ModdedPlayerImpl;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;

public class FabricPlayerImpl extends ModdedPlayerImpl {
    public FabricPlayerImpl(Player player) {
        super(player);
    }

    @Override
    public boolean hasPermission(String permission) {
        MinecraftServer server = getNative().getServer();
        if (server != null) {
            return Permissions.check(getNative(), permission, server.getOperatorUserPermissionLevel());
        } else {
            return Permissions.check(getNative(), permission);
        }
    }
}
