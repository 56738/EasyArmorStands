package me.m56738.easyarmorstands.fabric;

import me.m56738.easyarmorstands.EasyArmorStandsHolder;
import me.m56738.easyarmorstands.command.sender.CommandSenderMapper;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.modded.command.ModdedCommandSourceStackMapper;
import me.m56738.easyarmorstands.modded.util.MainThreadExecutor;
import net.fabricmc.api.ModInitializer;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.fabric.FabricServerCommandManager;

public class EasyArmorStandsMod implements ModInitializer {
    private final MainThreadExecutor executor = new MainThreadExecutor();
    private final EasyArmorStandsHolder holder = new EasyArmorStandsHolder();

    @Override
    public void onInitialize() {
        FabricServerCommandManager<EasCommandSender> commandManager = new FabricServerCommandManager<>(
                ExecutionCoordinator.coordinatorFor(executor),
                new ModdedCommandSourceStackMapper(holder, new CommandSenderMapper(holder)));
    }
}
