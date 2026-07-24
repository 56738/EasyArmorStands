package me.m56738.easyarmorstands.platform.fabric;

import me.lucko.fabric.api.permissions.v0.Permissions;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.fabricmc.fabric.api.util.TriState;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.kyori.adventure.platform.modcommon.MinecraftServerAudiences;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class FabricPlatform extends ModdedPlatform {
    public FabricPlatform(MinecraftServer server, MinecraftServerAudiences adventure, ComponentLogger logger) {
        super(server, adventure, logger);
    }

    @Override
    public String getName() {
        return FabricLoader.getInstance().getModContainer("fabricloader")
                .map(ModContainer::getMetadata)
                .map(ModMetadata::getName)
                .orElse("Fabric");
    }

    @Override
    public String getVersion() {
        return FabricLoader.getInstance().getModContainer("fabricloader")
                .map(ModContainer::getMetadata)
                .map(ModMetadata::getVersion)
                .map(Version::getFriendlyString)
                .orElse("Unknown");
    }

    public TriState getPermission(ServerPlayer player, String permission) {
        return Permissions.getPermissionValue(player, permission);
    }

    @Override
    public boolean hasPermission(ServerPlayer player, String permission) {
        return getPermission(player, permission).orElse(false);
    }

    @Override
    public boolean isPermissionSet(ServerPlayer player, String permission) {
        return getPermission(player, permission) != TriState.DEFAULT;
    }
}
