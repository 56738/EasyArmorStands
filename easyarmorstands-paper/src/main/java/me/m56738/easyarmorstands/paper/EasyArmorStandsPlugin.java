package me.m56738.easyarmorstands.paper;

import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.paper.platform.PaperPlatformImpl;
import me.m56738.easyarmorstands.paper.platform.command.PaperCommandSource;
import me.m56738.easyarmorstands.paper.platform.command.PaperSenderMapper;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationStore;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class EasyArmorStandsPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        TranslationStore.StringBased<MessageFormat> registry = TranslationStore.messageFormat(Key.key("easyarmorstands", "translation"));
        ResourceBundle bundle = ResourceBundle.getBundle("me.m56738.easyarmorstands.messages", Locale.US, UTF8ResourceBundleControl.get());
        registry.registerAll(Locale.US, bundle, false);
        GlobalTranslator.translator().addSource(registry);

        PaperPlatformImpl platform = new PaperPlatformImpl(this);

        PaperCommandManager<PaperCommandSource> commandManager = PaperCommandManager.builder(new PaperSenderMapper(platform))
                .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
                .buildOnEnable(this);

        EasyArmorStandsCommon.registerCommands(commandManager, PaperCommandSource.class, platform);
    }
}
