package me.m56738.easyarmorstands.core.command;

import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.exceptions.parsing.NoInputProvidedException;
import me.m56738.easyarmorstands.core.bone.Bone;
import me.m56738.easyarmorstands.core.platform.EasCommandSender;
import me.m56738.easyarmorstands.core.session.Session;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class BoneArgumentParser implements ArgumentParser<EasCommandSender, Bone> {
    @Override
    public @NonNull ArgumentParseResult<@NonNull Bone> parse(
            @NonNull CommandContext<@NonNull EasCommandSender> context,
            @NonNull Queue<@NonNull String> inputQueue
    ) {
        String input = inputQueue.peek();
        if (input == null) {
            return ArgumentParseResult.failure(
                    new NoInputProvidedException(BoneArgumentParser.class, context));
        }

        Session session;
        try {
            session = SessionPreprocessor.getSession(context);
        } catch (NoSessionException e) {
            return ArgumentParseResult.failure(e);
        }
        Bone bone = session.getBones().get(input);
        if (bone == null) {
            return ArgumentParseResult.failure(new IllegalArgumentException("Bone not found: " + input));
        }
        inputQueue.remove();
        return ArgumentParseResult.success(bone);
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
        return new ArrayList<>(session.getBones().keySet());
    }
}
