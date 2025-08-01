package me.m56738.easyarmorstands.neoforge.platform.entity;

import me.m56738.easyarmorstands.modded.platform.entity.ModdedPlayerImpl;
import me.m56738.easyarmorstands.neoforge.EasyArmorStandsMod;
import me.m56738.easyarmorstands.neoforge.platform.NeoForgePlatformImpl;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.server.permission.PermissionAPI;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;

public class NeoForgePlayerImpl extends ModdedPlayerImpl {
    private final NeoForgePlatformImpl platform;

    public NeoForgePlayerImpl(NeoForgePlatformImpl platform, ServerPlayer player) {
        super(platform, player);
        this.platform = platform;
    }

    @Override
    public boolean hasPermission(String permission) {
        PermissionNode<Boolean> node = platform.getPermissions().getNode(permission);
        if (node == null) {
            EasyArmorStandsMod.LOGGER.warn("Permission not registered to NeoForgePlatform: {}", permission);
            return false;
        }
        return PermissionAPI.getPermission(getNative(), node);
    }
}
