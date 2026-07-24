package me.m56738.easyarmorstands.platform.fabric;

import me.m56738.easyarmorstands.platform.block.BlockData;
import me.m56738.easyarmorstands.platform.dialog.DialogFactory;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.EntityType;
import me.m56738.easyarmorstands.platform.entity.Pose;
import me.m56738.easyarmorstands.platform.inventory.InventoryFactory;
import me.m56738.easyarmorstands.platform.inventory.ItemType;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.scheduler.Scheduler;
import me.m56738.easyarmorstands.platform.world.World;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.modcommon.MinecraftServerAudiences;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.minecraft.server.MinecraftServer;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

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
}
