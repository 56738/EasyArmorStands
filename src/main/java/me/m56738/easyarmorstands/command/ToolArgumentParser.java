package me.m56738.easyarmorstands.command;

import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.exceptions.parsing.NoInputProvidedException;
import me.m56738.easyarmorstands.bone.Bone;
import me.m56738.easyarmorstands.tool.Tool;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ToolArgumentParser implements ArgumentParser<CommandSender, Tool> {
    @Override
    public @NonNull ArgumentParseResult<@NonNull Tool> parse(
            @NonNull CommandContext<@NonNull CommandSender> context,
            @NonNull Queue<@NonNull String> inputQueue
    ) {
        String input = inputQueue.peek();
        if (input == null) {
            return ArgumentParseResult.failure(
                    new NoInputProvidedException(ToolArgumentParser.class, context));
        }

        Bone bone = context.get("bone");
        Tool tool = bone.getTools().get(input);
        if (tool == null) {
            return ArgumentParseResult.failure(new IllegalArgumentException("Tool not found: " + input));
        }
        inputQueue.remove();
        return ArgumentParseResult.success(tool);
    }

    @Override
    public @NonNull List<@NonNull String> suggestions(
            @NonNull CommandContext<CommandSender> context,
            @NonNull String input
    ) {
        Bone bone = context.get("bone");
        return new ArrayList<>(bone.getTools().keySet());
    }
}
