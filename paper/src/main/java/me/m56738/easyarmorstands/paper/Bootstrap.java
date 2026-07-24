package me.m56738.easyarmorstands.paper;

import com.mojang.brigadier.arguments.StringArgumentType;
import io.leangen.geantyref.TypeToken;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import me.m56738.easyarmorstands.EasyArmorStandsHolder;
import me.m56738.easyarmorstands.command.ClipboardCommands;
import me.m56738.easyarmorstands.command.DisplayCommands;
import me.m56738.easyarmorstands.command.HistoryCommands;
import me.m56738.easyarmorstands.command.PropertyCommands;
import me.m56738.easyarmorstands.command.SessionCommands;
import me.m56738.easyarmorstands.command.annotation.PropertyPermission;
import me.m56738.easyarmorstands.command.parser.ArgumentParserProvider;
import me.m56738.easyarmorstands.command.parser.BlockDataArgumentParser;
import me.m56738.easyarmorstands.command.parser.LayerValueArgumentParser;
import me.m56738.easyarmorstands.command.processor.PropertyPermissionBuilderModifier;
import me.m56738.easyarmorstands.command.requirement.CommandRequirementBuilderModifier;
import me.m56738.easyarmorstands.command.requirement.ElementRequirement;
import me.m56738.easyarmorstands.command.requirement.ElementSelectionRequirement;
import me.m56738.easyarmorstands.command.requirement.RequireElement;
import me.m56738.easyarmorstands.command.requirement.RequireElementSelection;
import me.m56738.easyarmorstands.command.requirement.RequireSession;
import me.m56738.easyarmorstands.command.requirement.SessionRequirement;
import me.m56738.easyarmorstands.command.sender.CommandSenderMapper;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.message.TranslationManager;
import me.m56738.easyarmorstands.paper.command.CommandSourceStackMapper;
import me.m56738.easyarmorstands.paper.command.PaperArgumentParserProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.block.data.BlockData;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.description.Description;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.help.result.CommandEntry;
import org.incendo.cloud.minecraft.extras.RichDescription;
import org.incendo.cloud.minecraft.extras.parser.TextColorParser;
import org.incendo.cloud.minecraft.extras.suggestion.ComponentTooltipSuggestion;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.suggestion.Suggestion;
import org.incendo.cloud.suggestion.SuggestionProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
@NullMarked
public class Bootstrap implements PluginBootstrap {
    private @Nullable BootstrapResult result;

    private static Suggestion createSuggestion(CommandEntry<EasCommandSender> entry) {
        Description description = entry.command().commandDescription().description();
        String syntax = entry.syntax();
        Component tooltip;
        if (description instanceof RichDescription richDescription) {
            tooltip = richDescription.contents();
        } else if (!description.isEmpty()) {
            tooltip = Component.text(description.textDescription());
        } else {
            return Suggestion.suggestion(syntax);
        }
        return ComponentTooltipSuggestion.suggestion(syntax, tooltip);
    }

    @Override
    public void bootstrap(BootstrapContext context) {
        EasyArmorStandsHolder holder = new EasyArmorStandsHolder();

        TranslationManager translationManager = new TranslationManager();
        translationManager.load(context.getDataDirectory(), context.getLogger());

        MainThreadExecutor executor = new MainThreadExecutor(null);
        PaperCommandManager.Bootstrapped<EasCommandSender> commandManager = PaperCommandManager.builder(new CommandSourceStackMapper(new CommandSenderMapper(holder)))
                .executionCoordinator(ExecutionCoordinator.coordinatorFor(executor))
                .buildBootstrapped(context);

        commandManager.parserRegistry().registerSuggestionProvider("help_queries",
                SuggestionProvider.blocking((ctx, input) -> commandManager.createHelpHandler()
                        .queryRootIndex(ctx.sender())
                        .entries()
                        .stream()
                        .map(Bootstrap::createSuggestion)
                        .toList()));
        commandManager.parserRegistry().registerNamedParserSupplier("node_value",
                p -> new LayerValueArgumentParser<>());
        commandManager.parserRegistry().registerParserSupplier(TypeToken.get(TextColor.class),
                p -> new TextColorParser<>());
        commandManager.parserRegistry().registerParserSupplier(TypeToken.get(BlockData.class),
                p -> new BlockDataArgumentParser<>());

        ArgumentParserProvider parserProvider = new PaperArgumentParserProvider();
        commandManager.parserRegistry().registerParser(parserProvider.locationParser());
        commandManager.parserRegistry().registerParser(parserProvider.singleEntitySelector());
        commandManager.parserRegistry().registerParser(parserProvider.multipleEntitySelector());
        commandManager.parserRegistry().registerParser(parserProvider.multiplePlayerSelector());

        commandManager.brigadierManager().registerMapping(new TypeToken<BlockDataArgumentParser<EasCommandSender>>() {
        }, builder -> builder.to(a -> ArgumentTypes.blockState()));
        commandManager.brigadierManager().registerMapping(new TypeToken<TextColorParser<EasCommandSender>>() {
        }, builder -> builder.cloudSuggestions().toConstant(StringArgumentType.greedyString()));

        AnnotationParser<EasCommandSender> annotationParser = new AnnotationParser<>(commandManager, EasCommandSender.class);
        annotationParser.descriptionMapper(RichDescription::translatable);

        annotationParser.registerBuilderModifier(RequireSession.class, new CommandRequirementBuilderModifier<>(a -> new SessionRequirement()));
        annotationParser.registerBuilderModifier(RequireElement.class, new CommandRequirementBuilderModifier<>(a -> new ElementRequirement()));
        annotationParser.registerBuilderModifier(RequireElementSelection.class, new CommandRequirementBuilderModifier<>(a -> new ElementSelectionRequirement()));

        annotationParser.registerBuilderModifier(PropertyPermission.class, new PropertyPermissionBuilderModifier(holder));
        annotationParser.parse(new SessionCommands());
        annotationParser.parse(new HistoryCommands());
        annotationParser.parse(new ClipboardCommands());
        annotationParser.parse(new DisplayCommands());
        try {
            annotationParser.parseContainers(getClass().getClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        PropertyCommands.register(commandManager, parserProvider);

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
