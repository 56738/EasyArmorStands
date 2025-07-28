package me.m56738.easyarmorstands.fabric;

import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.platform.PlatformHolder;
import me.m56738.easyarmorstands.fabric.platform.FabricPlatformImpl;
import me.m56738.easyarmorstands.fabric.platform.command.FabricSenderMapper;
import me.m56738.easyarmorstands.modded.platform.ModdedPlatformImpl;
import me.m56738.easyarmorstands.modded.platform.command.ModdedCommandSource;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.fabric.FabricServerCommandManager;

import java.util.NoSuchElementException;

public class EasyArmorStandsMod implements ModInitializer {
    private final PlatformHolder platformHolder = new PlatformHolder();

    @Override
    public void onInitialize() {
        FabricServerCommandManager<ModdedCommandSource> commandManager = new FabricServerCommandManager<>(
                ExecutionCoordinator.simpleCoordinator(), new FabricSenderMapper());

        EasyArmorStandsCommon.registerCommands(commandManager, ModdedCommandSource.class, platformHolder);

        ServerLifecycleEvents.SERVER_STARTED.register(this::onSetup);
    }

    private void onSetup(MinecraftServer server) {
        String version = FabricLoader.getInstance().getModContainer("easyarmorstands")
                .orElseThrow(NoSuchElementException::new)
                .getMetadata().getVersion().getFriendlyString();
        ModdedPlatformImpl platform = new FabricPlatformImpl(server, version);
        platformHolder.setPlatform(platform);
    }
}
