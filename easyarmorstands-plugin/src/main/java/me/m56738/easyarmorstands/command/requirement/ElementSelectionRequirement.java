package me.m56738.easyarmorstands.command.requirement;

import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.paper.util.sender.Source;
import me.m56738.easyarmorstands.message.Message;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collections;
import java.util.List;

import static me.m56738.easyarmorstands.command.processor.ElementSelectionProcessor.elementSelectionKey;

public class ElementSelectionRequirement implements CommandRequirement {
    @Override
    public boolean evaluateRequirement(@NonNull CommandContext<Source> commandContext) {
        return commandContext.contains(elementSelectionKey());
    }

    @Override
    public void handle(CommandContext<Source> context) {
        CommandSender sender = context.sender().source();
        sender.sendMessage(Message.error("easyarmorstands.error.nothing-selected"));
        sender.sendMessage(Message.hint("easyarmorstands.hint.select-entity"));
    }

    @Override
    public @NonNull List<@NonNull CommandRequirement> parents() {
        return Collections.singletonList(new SessionRequirement());
    }
}
