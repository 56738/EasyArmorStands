package me.m56738.easyarmorstands.command.requirement;

import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import org.checkerframework.checker.nullness.qual.NonNull;

import static me.m56738.easyarmorstands.command.processor.SessionProcessor.sessionKey;

public class SessionRequirement implements CommandRequirement {
    @Override
    public void handle(CommandContext<EasCommandSender> context) {
        EasCommandSender sender = context.sender();
        sender.sendMessage(Message.error("easyarmorstands.error.not-using-editor"));
        if (sender.get().hasPermission(Permissions.GIVE)) {
            sender.sendMessage(Message.hint("easyarmorstands.hint.give-tool", Message.command("/eas give")));
        }
    }

    @Override
    public boolean evaluateRequirement(@NonNull CommandContext<EasCommandSender> commandContext) {
        return commandContext.contains(sessionKey());
    }
}
