package me.m56738.easyarmorstands.neoforge.platform.entity;

import me.m56738.easyarmorstands.modded.platform.entity.ModdedPlayerImpl;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;

public class NeoForgePlayerImpl extends ModdedPlayerImpl {
    public NeoForgePlayerImpl(Player player) {
        super(player);
    }

    @Override
    public boolean hasPermission(String permission) {
        MinecraftServer server = getNative().getServer();
        if (server != null) {
            return getNative().hasPermissions(server.getOperatorUserPermissionLevel());
        } else {
            return false;
        }
    }
}
