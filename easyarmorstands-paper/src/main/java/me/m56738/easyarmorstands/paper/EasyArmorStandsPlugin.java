package me.m56738.easyarmorstands.paper;

import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.paper.platform.PaperPlatformImpl;
import me.m56738.easyarmorstands.paper.platform.command.PaperCommandSource;
import me.m56738.easyarmorstands.paper.platform.command.PaperSenderMapper;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;

public class EasyArmorStandsPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        PaperPlatformImpl platform = new PaperPlatformImpl(this);

        PaperCommandManager<PaperCommandSource> commandManager = PaperCommandManager.builder(new PaperSenderMapper())
                .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
                .buildOnEnable(this);

        EasyArmorStandsCommon.registerCommands(commandManager, PaperCommandSource.class, platform);
    }
}
