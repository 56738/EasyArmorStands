package me.m56738.easyarmorstands.common;

import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.component.DefaultValue;
import org.incendo.cloud.help.result.CommandEntry;
import org.incendo.cloud.minecraft.extras.MinecraftHelp;
import org.incendo.cloud.minecraft.extras.RichDescription;
import org.incendo.cloud.parser.standard.StringParser;
import org.incendo.cloud.suggestion.Suggestion;
import org.incendo.cloud.suggestion.SuggestionProvider;

public class EasyArmorStandsCommon {
    public static <C extends CommandSource> void registerCommands(CommandManager<C> commandManager, Class<C> commandSenderClass, Platform platform) {
        commandManager.parameterInjectorRegistry().registerInjector(Platform.class,
                (context, annotationAccessor) -> platform);

        MinecraftHelp<C> help = MinecraftHelp.create("/eas help", commandManager, CommandSource::source);
        commandManager.command(commandManager.commandBuilder("eas")
                .literal("help")
                .optional("query", StringParser.greedyStringParser(), DefaultValue.constant(""),
                        SuggestionProvider.blocking((context, input) ->
                                commandManager.createHelpHandler()
                                        .queryRootIndex(context.sender())
                                        .entries()
                                        .stream()
                                        .map(CommandEntry::syntax)
                                        .map(Suggestion::suggestion)
                                        .toList()))
                .commandDescription(RichDescription.translatable("easyarmorstands.command.description.help"))
                .permission(Permissions.HELP)
                .handler(context -> help.queryCommands(context.get("query"), context.sender())));

        AnnotationParser<C> annotationParser = new AnnotationParser<>(commandManager, commandSenderClass);
        try {
            annotationParser.parseContainers(EasyArmorStandsCommon.class.getClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
