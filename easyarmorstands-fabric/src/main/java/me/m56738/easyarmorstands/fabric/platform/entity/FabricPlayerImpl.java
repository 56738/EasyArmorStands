package me.m56738.easyarmorstands.fabric.platform.entity;

import me.lucko.fabric.api.permissions.v0.Permissions;
import me.m56738.easyarmorstands.fabric.platform.FabricPlatformImpl;
import me.m56738.easyarmorstands.modded.platform.entity.ModdedPlayerImpl;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class FabricPlayerImpl extends ModdedPlayerImpl {
    public FabricPlayerImpl(FabricPlatformImpl platform, ServerPlayer player) {
        super(platform, player);
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
