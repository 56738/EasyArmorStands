package me.m56738.easyarmorstands.fabric;

import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommonProvider;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.fabric.platform.FabricPlatformImpl;
import me.m56738.easyarmorstands.fabric.platform.command.FabricSenderMapper;
import me.m56738.easyarmorstands.modded.platform.ModdedPlatformImpl;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.fabric.FabricServerCommandManager;
import org.jspecify.annotations.Nullable;

import java.util.NoSuchElementException;
import java.util.Objects;

public class EasyArmorStandsMod implements ModInitializer, EasyArmorStandsCommonProvider {
    private @Nullable EasyArmorStandsCommon eas;

    @Override
    public void onInitialize() {
        FabricServerCommandManager<CommandSource> commandManager = new FabricServerCommandManager<>(
                ExecutionCoordinator.simpleCoordinator(), new FabricSenderMapper(this));

        EasyArmorStandsCommon.registerCommands(commandManager, this);

        ServerLifecycleEvents.SERVER_STARTED.register(this::onStarted);
        ServerLifecycleEvents.SERVER_STOPPED.register(this::onStopped);
    }

    private void onStarted(MinecraftServer server) {
        String version = FabricLoader.getInstance().getModContainer("easyarmorstands")
                .orElseThrow(NoSuchElementException::new)
                .getMetadata().getVersion().getFriendlyString();
        ModdedPlatformImpl platform = new FabricPlatformImpl(server);
        eas = new EasyArmorStandsCommon(platform, version);
    }

    private void onStopped(MinecraftServer server) {
        if (eas != null) {
            eas.platform().close();
            eas = null;
        }
    }

    @Override
    public EasyArmorStandsCommon getEasyArmorStands() {
        return Objects.requireNonNull(eas);
    }
}
