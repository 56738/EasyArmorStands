package me.m56738.easyarmorstands.fabric;

import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommonProvider;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.fabric.element.FabricEntityElementListener;
import me.m56738.easyarmorstands.fabric.platform.FabricPlatformImpl;
import me.m56738.easyarmorstands.fabric.session.FabricSessionListener;
import me.m56738.easyarmorstands.modded.platform.ModdedPlatformImpl;
import me.m56738.easyarmorstands.modded.platform.command.MainThreadExecutor;
import me.m56738.easyarmorstands.modded.platform.command.ModdedSenderMapper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.fabric.FabricServerCommandManager;
import org.jspecify.annotations.Nullable;

import java.util.NoSuchElementException;
import java.util.Objects;

public class EasyArmorStandsMod implements ModInitializer, EasyArmorStandsCommonProvider {
    public static final String MODID = "easyarmorstands";
    private final MainThreadExecutor executor = new MainThreadExecutor();
    private @Nullable EasyArmorStandsCommon eas;

    @Override
    public void onInitialize() {
        FabricServerCommandManager<CommandSource> commandManager = new FabricServerCommandManager<>(
                ExecutionCoordinator.coordinatorFor(executor),
                new ModdedSenderMapper(this));

        EasyArmorStandsCommon.registerCommands(commandManager, this);

        ServerLifecycleEvents.SERVER_STARTED.register(this::onStarted);
        ServerLifecycleEvents.SERVER_STOPPED.register(this::onStopped);
        ServerTickEvents.END_SERVER_TICK.register(this::onTick);

        new FabricSessionListener(this).register();
        new FabricEntityElementListener(this).register();
    }

    private void onStarted(MinecraftServer server) {
        executor.setServer(server);
        String version = FabricLoader.getInstance().getModContainer(MODID)
                .orElseThrow(NoSuchElementException::new)
                .getMetadata().getVersion().getFriendlyString();
        ModdedPlatformImpl platform = new FabricPlatformImpl(server);
        eas = new EasyArmorStandsCommon(platform, version);
    }

    private void onStopped(MinecraftServer server) {
        if (eas != null) {
            eas.close();
            eas = null;
        }
        executor.setServer(null);
    }

    private void onTick(MinecraftServer server) {
        if (eas != null) {
            eas.getSessionListener().update();
            eas.getSessionManager().update();
        }
    }

    @Override
    public boolean hasEasyArmorStands() {
        return eas != null;
    }

    @Override
    public EasyArmorStandsCommon getEasyArmorStands() {
        return Objects.requireNonNull(eas);
    }
}
