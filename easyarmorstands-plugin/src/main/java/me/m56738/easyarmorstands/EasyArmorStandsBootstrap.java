package me.m56738.easyarmorstands;

import io.leangen.geantyref.TypeToken;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import me.m56738.easyarmorstands.command.ClipboardCommands;
import me.m56738.easyarmorstands.command.CommandSourceStackMapper;
import me.m56738.easyarmorstands.command.DisplayCommands;
import me.m56738.easyarmorstands.command.HistoryCommands;
import me.m56738.easyarmorstands.command.SessionCommands;
import me.m56738.easyarmorstands.command.annotation.PropertyPermission;
import me.m56738.easyarmorstands.command.parser.BlockDataArgumentParser;
import me.m56738.easyarmorstands.command.parser.NodeValueArgumentParser;
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
import me.m56738.easyarmorstands.util.MainThreadExecutor;
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
public class EasyArmorStandsBootstrap implements PluginBootstrap {
    private @Nullable BootstrapResult result;

    @Override
    public void bootstrap(BootstrapContext context) {
        MainThreadExecutor executor = new MainThreadExecutor(null);
        PaperCommandManager.Bootstrapped<EasCommandSender> commandManager = PaperCommandManager.builder(new CommandSourceStackMapper(new CommandSenderMapper()))
                .executionCoordinator(ExecutionCoordinator.coordinatorFor(executor))
                .buildBootstrapped(context);

        commandManager.parserRegistry().registerSuggestionProvider("help_queries",
                SuggestionProvider.blocking((ctx, _) -> commandManager.createHelpHandler()
                        .queryRootIndex(ctx.sender())
                        .entries()
                        .stream()
                        .map(EasyArmorStandsBootstrap::createSuggestion)
                        .toList()));
        commandManager.parserRegistry().registerNamedParserSupplier("node_value",
                p -> new NodeValueArgumentParser<>());
        commandManager.parserRegistry().registerParserSupplier(TypeToken.get(TextColor.class),
                p -> new TextColorParser<>());
        commandManager.parserRegistry().registerParserSupplier(TypeToken.get(BlockData.class),
                p -> new BlockDataArgumentParser<>());

        AnnotationParser<EasCommandSender> annotationParser = new AnnotationParser<>(commandManager, EasCommandSender.class);
        annotationParser.descriptionMapper(RichDescription::translatable);

        annotationParser.registerBuilderModifier(RequireSession.class, new CommandRequirementBuilderModifier<>(a -> new SessionRequirement()));
        annotationParser.registerBuilderModifier(RequireElement.class, new CommandRequirementBuilderModifier<>(a -> new ElementRequirement()));
        annotationParser.registerBuilderModifier(RequireElementSelection.class, new CommandRequirementBuilderModifier<>(a -> new ElementSelectionRequirement()));

        annotationParser.registerBuilderModifier(PropertyPermission.class, new PropertyPermissionBuilderModifier());
        annotationParser.parse(new SessionCommands());
        annotationParser.parse(new HistoryCommands());
        annotationParser.parse(new ClipboardCommands());
        // TODO
//        annotationParser.parse(new DisplayCommands(itemDisplayType));
        try {
            annotationParser.parseContainers(getClass().getClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        PropertyCommands.register(commandManager);

        result = new BootstrapResult(executor, commandManager);
    }

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
    public JavaPlugin createPlugin(PluginProviderContext context) {
        if (result == null) {
            throw new IllegalStateException("Bootstrap was not successful");
        }

        return new EasyArmorStandsPlugin(result.executor, result.commandManager);
    }

    private record BootstrapResult(
            MainThreadExecutor executor,
            PaperCommandManager.Bootstrapped<EasCommandSender> commandManager) {
    }
}
