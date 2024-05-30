package me.m56738.easyarmorstands.command.requirement;

import me.m56738.easyarmorstands.command.processor.GroupProcessor;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.message.Message;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;

import java.util.Collections;
import java.util.List;

import static me.m56738.easyarmorstands.command.processor.ElementProcessor.elementKey;

public class ElementRequirement implements CommandRequirement {
    @Override
    public boolean evaluateRequirement(@NonNull CommandContext<EasCommandSender> commandContext) {
        return commandContext.contains(elementKey());
    }

    @Override
    public void handle(CommandContext<EasCommandSender> context) {
        EasCommandSender sender = context.sender();
        if (context.contains(GroupProcessor.groupKey())) {
            sender.sendMessage(Message.error("easyarmorstands.error.group-selected"));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.nothing-selected"));
            sender.sendMessage(Message.hint("easyarmorstands.hint.select-entity"));
        }
    }

    @Override
    public @NonNull List<@NonNull CommandRequirement> parents() {
        return Collections.singletonList(new SessionRequirement());
    }
}
