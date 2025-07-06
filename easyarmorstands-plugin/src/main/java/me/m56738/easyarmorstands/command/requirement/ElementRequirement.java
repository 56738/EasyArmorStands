package me.m56738.easyarmorstands.command.requirement;

import me.m56738.easyarmorstands.command.processor.GroupProcessor;
import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.paper.util.sender.Source;
import me.m56738.easyarmorstands.message.Message;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collections;
import java.util.List;

import static me.m56738.easyarmorstands.command.processor.ElementProcessor.elementKey;

public class ElementRequirement implements CommandRequirement {
    @Override
    public boolean evaluateRequirement(@NonNull CommandContext<Source> commandContext) {
        return commandContext.contains(elementKey());
    }

    @Override
    public void handle(CommandContext<Source> context) {
        CommandSender sender = context.sender().source();
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
