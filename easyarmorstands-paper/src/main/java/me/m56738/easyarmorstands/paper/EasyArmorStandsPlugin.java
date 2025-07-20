package me.m56738.easyarmorstands.paper;

import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.paper.message.TranslationParser;
import me.m56738.easyarmorstands.paper.platform.PaperPlatformImpl;
import me.m56738.easyarmorstands.paper.platform.command.PaperCommandSource;
import me.m56738.easyarmorstands.paper.platform.command.PaperSenderMapper;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;

public class EasyArmorStandsPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        GlobalTranslator.translator().addSource(TranslationParser.read(getResource("assets/easyarmorstands/lang/en_us.json"), Key.key("easyarmorstands", "translation")));

        PaperPlatformImpl platform = new PaperPlatformImpl(this);

        PaperCommandManager<PaperCommandSource> commandManager = PaperCommandManager.builder(new PaperSenderMapper(platform))
                .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
                .buildOnEnable(this);

        EasyArmorStandsCommon.registerCommands(commandManager, PaperCommandSource.class, platform);
    }
}
