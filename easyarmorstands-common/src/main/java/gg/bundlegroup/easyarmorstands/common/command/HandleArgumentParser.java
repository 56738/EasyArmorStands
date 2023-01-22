package gg.bundlegroup.easyarmorstands.common.command;

import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.exceptions.parsing.NoInputProvidedException;
import gg.bundlegroup.easyarmorstands.common.handle.Handle;
import gg.bundlegroup.easyarmorstands.common.platform.EasCommandSender;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class HandleArgumentParser implements ArgumentParser<EasCommandSender, Handle> {
    @Override
    public @NonNull ArgumentParseResult<@NonNull Handle> parse(
            @NonNull CommandContext<@NonNull EasCommandSender> context,
            @NonNull Queue<@NonNull String> inputQueue
    ) {
        String input = inputQueue.peek();
        if (input == null) {
            return ArgumentParseResult.failure(
                    new NoInputProvidedException(HandleArgumentParser.class, context));
        }

        Session session;
        try {
            session = SessionPreprocessor.getSession(context);
        } catch (NoSessionException e) {
            return ArgumentParseResult.failure(e);
        }
        Handle handle = session.getHandles().get(input);
        if (handle == null) {
            return ArgumentParseResult.failure(new IllegalArgumentException("Handle not found: " + input));
        }
        inputQueue.remove();
        return ArgumentParseResult.success(handle);
    }

    @Override
    public @NonNull List<@NonNull String> suggestions(
            @NonNull CommandContext<EasCommandSender> context,
            @NonNull String input
    ) {
        Session session = SessionPreprocessor.getSessionOrNull(context);
        if (session == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(session.getHandles().keySet());
    }
}
