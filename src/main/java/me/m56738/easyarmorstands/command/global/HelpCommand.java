package me.m56738.easyarmorstands.command.global;

import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.permission.Permissions;
import org.incendo.cloud.annotation.specifier.Greedy;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.annotations.processing.CommandContainer;
import org.incendo.cloud.minecraft.extras.MinecraftHelp;
import org.jspecify.annotations.Nullable;

@CommandContainer
public class HelpCommand {
    @Command("eas help [query]")
    @Permission(Permissions.HELP)
    @CommandDescription("easyarmorstands.command.description.help")
    public void help(
            EasCommandSender sender,
            MinecraftHelp<EasCommandSender> help,
            @Argument(value = "query", suggestions = "help_queries") @Greedy @Nullable String query) {
        help.queryCommands(query != null ? query : "", sender);
    }
}
