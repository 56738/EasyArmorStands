package me.m56738.easyarmorstands.paper;

import com.mojang.brigadier.arguments.StringArgumentType;
import io.leangen.geantyref.TypeToken;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.EasyArmorStandsHolder;
import me.m56738.easyarmorstands.command.parser.BlockDataArgumentParser;
import me.m56738.easyarmorstands.command.sender.CommandSenderMapper;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.message.TranslationManager;
import me.m56738.easyarmorstands.paper.command.CommandSourceStackMapper;
import me.m56738.easyarmorstands.paper.command.PaperArgumentParserProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.minecraft.extras.parser.TextColorParser;
import org.incendo.cloud.paper.PaperCommandManager;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
@NullMarked
public class Bootstrap implements PluginBootstrap {
    private @Nullable BootstrapResult result;

    @Override
    public void bootstrap(BootstrapContext context) {
        EasyArmorStandsHolder holder = new EasyArmorStandsHolder();

        TranslationManager translationManager = new TranslationManager();
        translationManager.load(context.getDataDirectory(), context.getLogger());

        MainThreadExecutor executor = new MainThreadExecutor(null);
        PaperCommandManager.Bootstrapped<EasCommandSender> commandManager = PaperCommandManager.builder(new CommandSourceStackMapper(new CommandSenderMapper(holder)))
                .executionCoordinator(ExecutionCoordinator.coordinatorFor(executor))
                .buildBootstrapped(context);

        commandManager.brigadierManager().registerMapping(new TypeToken<BlockDataArgumentParser<EasCommandSender>>() {
        }, builder -> builder.to(a -> ArgumentTypes.blockState()));
        commandManager.brigadierManager().registerMapping(new TypeToken<TextColorParser<EasCommandSender>>() {
        }, builder -> builder.cloudSuggestions().toConstant(StringArgumentType.greedyString()));

        EasyArmorStandsCommon.registerCommands(commandManager, new PaperArgumentParserProvider(), getClass().getClassLoader(), holder);

        result = new BootstrapResult(holder, executor, translationManager, commandManager);
    }

    @Override
    public JavaPlugin createPlugin(PluginProviderContext context) {
        if (result == null) {
            throw new IllegalStateException("Bootstrap was not successful");
        }

        return new Main(result.holder, result.executor, result.translationManager, result.commandManager);
    }

    private record BootstrapResult(
            EasyArmorStandsHolder holder,
            MainThreadExecutor executor,
            TranslationManager translationManager,
            PaperCommandManager.Bootstrapped<EasCommandSender> commandManager) {
    }
}
