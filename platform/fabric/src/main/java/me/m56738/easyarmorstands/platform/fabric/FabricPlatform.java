package me.m56738.easyarmorstands.platform.fabric;

import me.lucko.fabric.api.permissions.v0.Permissions;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

public class FabricPlatform extends ModdedPlatform {
    public FabricPlatform(ComponentLogger logger) {
        super(logger);
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

    @Override
    public boolean hasPermission(ServerPlayer player, String permission) {
        return Permissions.check(player, permission, getServer().operatorUserPermissions().level());
    }

    @Override
    public boolean hasPermission(CommandSourceStack stack, String permission) {
        return Permissions.check(stack, permission, getServer().operatorUserPermissions().level());
    }
}
