package gg.bundlegroup.easyarmorstands.core.command;

import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.exceptions.parsing.NoInputProvidedException;
import gg.bundlegroup.easyarmorstands.core.bone.Bone;
import gg.bundlegroup.easyarmorstands.core.platform.EasCommandSender;
import gg.bundlegroup.easyarmorstands.core.tool.Tool;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ToolArgumentParser implements ArgumentParser<EasCommandSender, Tool> {
    @Override
    public @NonNull ArgumentParseResult<@NonNull Tool> parse(
            @NonNull CommandContext<@NonNull EasCommandSender> context,
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
            @NonNull CommandContext<EasCommandSender> context,
            @NonNull String input
    ) {
        Bone bone = context.get("bone");
        return new ArrayList<>(bone.getTools().keySet());
    }
}
