package me.m56738.easyarmorstands.paper;

import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.platform.PlatformHolder;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.common.property.type.PropertyTypeRegistryImpl;
import me.m56738.easyarmorstands.paper.message.TranslationParser;
import me.m56738.easyarmorstands.paper.platform.PaperPlatformImpl;
import me.m56738.easyarmorstands.paper.platform.command.PaperSenderMapper;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;

public class EasyArmorStandsPlugin extends JavaPlugin {
    private final PlatformHolder platformHolder = new PlatformHolder();

    @Override
    public void onEnable() {
        PaperPlatformImpl platform = new PaperPlatformImpl(this);
        platformHolder.setPlatform(platform);

        GlobalTranslator.translator().addSource(TranslationParser.read(getResource("assets/easyarmorstands/lang/en_us.json"), Key.key("easyarmorstands", "translation")));

        PaperCommandManager<CommandSource> commandManager = PaperCommandManager.builder(new PaperSenderMapper())
                .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
                .buildOnEnable(this);

        PropertyTypeRegistry propertyTypeRegistry = new PropertyTypeRegistryImpl();

        EasyArmorStandsCommon.registerCommands(commandManager, propertyTypeRegistry, platformHolder);
    }
}
