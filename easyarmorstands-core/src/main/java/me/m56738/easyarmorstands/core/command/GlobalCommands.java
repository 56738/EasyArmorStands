package me.m56738.easyarmorstands.core.command;

import cloud.commandframework.CommandHelpHandler;
import cloud.commandframework.CommandManager;
import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Greedy;
import cloud.commandframework.annotations.suggestions.Suggestions;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.minecraft.extras.MinecraftHelp;
import me.m56738.easyarmorstands.core.platform.EasCommandSender;
import me.m56738.easyarmorstands.core.platform.EasPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;
import java.util.stream.Collectors;

@CommandMethod("eas")
public class GlobalCommands {
    private final CommandManager<EasCommandSender> commandManager;
    private final MinecraftHelp<EasCommandSender> help;

    public GlobalCommands(CommandManager<EasCommandSender> commandManager) {
        this.commandManager = commandManager;
        this.help = new MinecraftHelp<>("/eas help", sender -> sender, commandManager);
    }

    @CommandMethod("help [query]")
    @CommandPermission("easyarmorstands.help")
    @CommandDescription("Shows the command help")
    public void help(EasCommandSender sender,
                     @Argument(value = "query", suggestions = "help_queries") @Greedy String query) {
        help.queryCommands(query != null ? query : "", sender);
    }

    @Suggestions("help_queries")
    public List<String> suggestHelpQueries(CommandContext<EasCommandSender> ctx, String input) {
        return commandManager.createCommandHelpHandler().queryRootIndex(ctx.getSender()).getEntries().stream()
                .map(CommandHelpHandler.VerboseHelpEntry::getSyntaxString)
                .collect(Collectors.toList());
    }

    @CommandMethod("give")
    @CommandPermission("easyarmorstands.give")
    @CommandDescription("Gives you the editor tool")
    public void give(EasPlayer sender) {
        sender.giveTool();
        sender.sendMessage(Component.text(
                "Tool added to your inventory\n",
                NamedTextColor.GREEN
        ).append(Component.text(
                "Right click an armor stand to start editing.\n" +
                        "Sneak + right click to spawn an armor stand.\n" +
                        "Drop to stop editing.",
                NamedTextColor.GRAY
        )));
    }
}
