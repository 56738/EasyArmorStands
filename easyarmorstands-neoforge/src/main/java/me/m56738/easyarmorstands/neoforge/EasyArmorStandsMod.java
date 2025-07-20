package me.m56738.easyarmorstands.neoforge;

import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.modded.platform.ModdedPlatformImpl;
import me.m56738.easyarmorstands.modded.platform.command.ModdedCommandSource;
import me.m56738.easyarmorstands.neoforge.platform.command.NeoForgeSenderMapper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.neoforge.NeoForgeServerCommandManager;

@Mod(EasyArmorStandsMod.MODID)
public class EasyArmorStandsMod {
    public static final String MODID = "easyarmorstands";
    private final ModContainer modContainer;

    public EasyArmorStandsMod(IEventBus modEventBus, ModContainer modContainer) {
        this.modContainer = modContainer;
        modEventBus.addListener(this::onSetup);
    }

    private void onSetup(FMLCommonSetupEvent event) {
        ModdedPlatformImpl platform = new ModdedPlatformImpl(modContainer.getModInfo().getVersion().toString());
        NeoForgeServerCommandManager<ModdedCommandSource> commandManager = new NeoForgeServerCommandManager<>(ExecutionCoordinator.simpleCoordinator(), new NeoForgeSenderMapper());
        EasyArmorStandsCommon.registerCommands(commandManager, ModdedCommandSource.class, platform);
    }
}
