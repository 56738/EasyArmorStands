package me.m56738.easyarmorstands.fabric;

import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.modded.platform.ModdedPlatformImpl;
import me.m56738.easyarmorstands.modded.platform.command.ModdedCommandSource;
import me.m56738.easyarmorstands.modded.platform.command.ModdedSenderMapper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.fabric.FabricServerCommandManager;

import java.util.NoSuchElementException;

public class EasyArmorStandsMod implements ModInitializer {
    @Override
    public void onInitialize() {
        String version = FabricLoader.getInstance().getModContainer("easyarmorstands")
                .orElseThrow(NoSuchElementException::new)
                .getMetadata().getVersion().getFriendlyString();
        ModdedPlatformImpl platform = new ModdedPlatformImpl(version);
        FabricServerCommandManager<ModdedCommandSource> commandManager = new FabricServerCommandManager<>(
                ExecutionCoordinator.simpleCoordinator(), new ModdedSenderMapper());
        EasyArmorStandsCommon.registerCommands(commandManager, ModdedCommandSource.class, platform);
    }
}
