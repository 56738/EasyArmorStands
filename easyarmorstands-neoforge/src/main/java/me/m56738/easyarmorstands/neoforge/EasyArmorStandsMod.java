package me.m56738.easyarmorstands.neoforge;

import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommonProvider;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.modded.platform.ModdedPlatformImpl;
import me.m56738.easyarmorstands.neoforge.platform.NeoForgePlatformImpl;
import me.m56738.easyarmorstands.neoforge.platform.command.NeoForgeSenderMapper;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.neoforge.NeoForgeServerCommandManager;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@Mod(EasyArmorStandsMod.MODID)
public class EasyArmorStandsMod implements EasyArmorStandsCommonProvider {
    public static final String MODID = "easyarmorstands";
    private final ModContainer modContainer;
    private @Nullable EasyArmorStandsCommon eas;

    public EasyArmorStandsMod(ModContainer modContainer) {
        this.modContainer = modContainer;
        NeoForgeServerCommandManager<CommandSource> commandManager = new NeoForgeServerCommandManager<>(ExecutionCoordinator.simpleCoordinator(), new NeoForgeSenderMapper(this));
        EasyArmorStandsCommon.registerCommands(commandManager, this);
        NeoForge.EVENT_BUS.addListener(this::onStarting);
        NeoForge.EVENT_BUS.addListener(this::onStopped);
    }

    private void onStarting(ServerStartingEvent event) {
        String version = modContainer.getModInfo().getVersion().toString();
        ModdedPlatformImpl platform = new NeoForgePlatformImpl(event.getServer());
        eas = new EasyArmorStandsCommon(platform, version);
    }

    private void onStopped(ServerStoppedEvent event) {
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
