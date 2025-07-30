package me.m56738.easyarmorstands.common.command.requirement;

import me.m56738.easyarmorstands.api.platform.entity.CommandSender;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;

import static me.m56738.easyarmorstands.common.command.processor.SessionProcessor.sessionKey;

public class SessionRequirement implements CommandRequirement {
    @Override
    public void handle(CommandContext<CommandSource> context) {
        CommandSender sender = context.sender().source();
        sender.sendMessage(Message.error("easyarmorstands.error.not-using-editor"));
        if (sender.hasPermission(Permissions.GIVE)) {
            sender.sendMessage(Message.hint("easyarmorstands.hint.give-tool", Message.command("/eas give")));
        }
    }

    @Override
    public boolean evaluateRequirement(@NonNull CommandContext<CommandSource> commandContext) {
        return commandContext.contains(sessionKey());
    }
}
