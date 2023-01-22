package gg.bundlegroup.easyarmorstands.common.command;

import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.exceptions.parsing.NoInputProvidedException;
import gg.bundlegroup.easyarmorstands.common.handle.Handle;
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

        Handle handle = context.get("handle");
        Manipulator manipulator = handle.getManipulators().get(input);
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
        Handle handle = context.get("handle");
        return new ArrayList<>(handle.getManipulators().keySet());
    }
}
