package me.m56738.easyarmorstands.paper;

import com.mojang.brigadier.arguments.StringArgumentType;
import io.leangen.geantyref.TypeToken;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommonProvider;
import me.m56738.easyarmorstands.common.command.parser.BlockDataParser;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.paper.clipboard.PaperClipboardListener;
import me.m56738.easyarmorstands.paper.editor.PaperSessionListener;
import me.m56738.easyarmorstands.paper.element.PaperDisplayElementListener;
import me.m56738.easyarmorstands.paper.element.PaperEntityElementListener;
import me.m56738.easyarmorstands.paper.history.PaperHistoryListener;
import me.m56738.easyarmorstands.paper.message.TranslationParser;
import me.m56738.easyarmorstands.paper.permission.PaperPermissions;
import me.m56738.easyarmorstands.paper.platform.PaperPlatformImpl;
import me.m56738.easyarmorstands.paper.platform.command.PaperSenderMapper;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.minecraft.extras.parser.TextColorParser;
import org.incendo.cloud.paper.PaperCommandManager;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class EasyArmorStandsPlugin extends JavaPlugin implements EasyArmorStandsCommonProvider {
    private @Nullable EasyArmorStandsCommon eas;

    @Override
    public void onEnable() {
        new Metrics(this, 17911);

        PaperPlatformImpl platform = new PaperPlatformImpl(this);

        PaperPermissions.registerAll(platform);

        eas = new EasyArmorStandsCommon(platform, getPluginMeta().getVersion());
        getServer().getPluginManager().registerEvents(new PaperSessionListener(this, eas, eas.sessionListener()), this);
        getServer().getPluginManager().registerEvents(new PaperHistoryListener(eas.historyManager()), this);
        getServer().getPluginManager().registerEvents(new PaperClipboardListener(this, eas.clipboardManager()), this);
        getServer().getPluginManager().registerEvents(new PaperEntityElementListener(), this);
        getServer().getPluginManager().registerEvents(new PaperDisplayElementListener(), this);
        getServer().getScheduler().runTaskTimer(this, eas.sessionManager()::update, 0, 1);
        getServer().getScheduler().runTaskTimer(this, eas.sessionListener()::update, 0, 1);

        GlobalTranslator.translator().addSource(TranslationParser.read(getResource("assets/easyarmorstands/lang/en_us.json"), Key.key("easyarmorstands", "translation")));

        PaperCommandManager<CommandSource> commandManager = PaperCommandManager.builder(new PaperSenderMapper())
                .executionCoordinator(ExecutionCoordinator.coordinatorFor(new MainThreadExecutor(this)))
                .buildOnEnable(this);

        commandManager.brigadierManager().registerMapping(new TypeToken<TextColorParser<CommandSource>>() {
        }, builder -> builder.cloudSuggestions().toConstant(StringArgumentType.greedyString()));

        commandManager.brigadierManager().registerMapping(new TypeToken<BlockDataParser>() {
        }, builder -> builder.toConstant(ArgumentTypes.blockState()));

        EasyArmorStandsCommon.registerCommands(commandManager, this);
    }

    @Override
    public void onDisable() {
        if (eas != null) {
            eas.close();
            eas = null;
        }
    }

    @Override
    public EasyArmorStandsCommon getEasyArmorStands() {
        return Objects.requireNonNull(eas);
    }
}
