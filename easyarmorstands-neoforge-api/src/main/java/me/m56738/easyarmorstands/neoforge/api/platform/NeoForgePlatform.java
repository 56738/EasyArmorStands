package me.m56738.easyarmorstands.neoforge.api.platform;

import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;

public interface NeoForgePlatform extends ModdedPlatform {
    void registerPermission(PermissionNode<Boolean> permission);
}
