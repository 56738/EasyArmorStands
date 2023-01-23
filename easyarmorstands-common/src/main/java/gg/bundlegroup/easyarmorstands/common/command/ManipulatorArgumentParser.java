package gg.bundlegroup.easyarmorstands.common.command;

import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.exceptions.parsing.NoInputProvidedException;
import gg.bundlegroup.easyarmorstands.common.bone.Bone;
import gg.bundlegroup.easyarmorstands.common.manipulator.Manipulator;
import gg.bundlegroup.easyarmorstands.common.platform.EasCommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ManipulatorArgumentParser implements ArgumentParser<EasCommandSender, Manipulator> {
    @Override
    public @NonNull ArgumentParseResult<@NonNull Manipulator> parse(
            @NonNull CommandContext<@NonNull EasCommandSender> context,
            @NonNull Queue<@NonNull String> inputQueue
    ) {
        String input = inputQueue.peek();
        if (input == null) {
            return ArgumentParseResult.failure(
                    new NoInputProvidedException(ManipulatorArgumentParser.class, context));
        }

        Bone bone = context.get("bone");
        Manipulator manipulator = bone.getManipulators().get(input);
        if (manipulator == null) {
            return ArgumentParseResult.failure(new IllegalArgumentException("Tool not found: " + input));
        }
        inputQueue.remove();
        return ArgumentParseResult.success(manipulator);
    }

    @Override
    public @NonNull List<@NonNull String> suggestions(
            @NonNull CommandContext<EasCommandSender> context,
            @NonNull String input
    ) {
        Bone bone = context.get("bone");
        return new ArrayList<>(bone.getManipulators().keySet());
    }
}
