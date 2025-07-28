package me.m56738.easyarmorstands.neoforge;

import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.platform.PlatformHolder;
import me.m56738.easyarmorstands.modded.platform.ModdedPlatformImpl;
import me.m56738.easyarmorstands.modded.platform.command.ModdedCommandSource;
import me.m56738.easyarmorstands.neoforge.platform.NeoForgePlatformImpl;
import me.m56738.easyarmorstands.neoforge.platform.command.NeoForgeSenderMapper;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.neoforge.NeoForgeServerCommandManager;

@Mod(EasyArmorStandsMod.MODID)
public class EasyArmorStandsMod {
    public static final String MODID = "easyarmorstands";
    private final ModContainer modContainer;
    private final PlatformHolder platformHolder = new PlatformHolder();

    public EasyArmorStandsMod(ModContainer modContainer) {
        this.modContainer = modContainer;
        NeoForgeServerCommandManager<ModdedCommandSource> commandManager = new NeoForgeServerCommandManager<>(ExecutionCoordinator.simpleCoordinator(), new NeoForgeSenderMapper());
        EasyArmorStandsCommon.registerCommands(commandManager, ModdedCommandSource.class, platformHolder);
        NeoForge.EVENT_BUS.addListener(this::onSetup);
    }

    private void onSetup(ServerStartingEvent event) {
        String version = modContainer.getModInfo().getVersion().toString();
        ModdedPlatformImpl platform = new NeoForgePlatformImpl(event.getServer(), version);
        platformHolder.setPlatform(platform);
    }
}
