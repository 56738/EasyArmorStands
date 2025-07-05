package me.m56738.easyarmorstands.command.parser;

import me.m56738.easyarmorstands.editor.node.ValueNode;
import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.context.CommandInput;
import me.m56738.easyarmorstands.lib.cloud.parser.ArgumentParseResult;
import me.m56738.easyarmorstands.lib.cloud.parser.ArgumentParser;
import me.m56738.easyarmorstands.lib.cloud.suggestion.Suggestion;
import me.m56738.easyarmorstands.lib.cloud.suggestion.SuggestionProvider;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.util.ComponentMessageThrowable;
import me.m56738.easyarmorstands.message.Message;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class NodeValueArgumentParser<C> implements ArgumentParser<C, Object>, SuggestionProvider<C> {
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public @NonNull ArgumentParseResult<@NonNull Object> parse(@NonNull CommandContext<@NonNull C> context, @NonNull CommandInput input) {
        Optional<ValueNode> optionalNode = context.inject(ValueNode.class);
        if (!optionalNode.isPresent()) {
            return ArgumentParseResult.failure(new UnsupportedToolException());
        }
        ValueNode node = optionalNode.get();
        return node.getParser().parse(context, input);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public @NonNull CompletableFuture<@NonNull Iterable<@NonNull Suggestion>> suggestionsFuture(@NonNull CommandContext<C> context, @NonNull CommandInput input) {
        Optional<ValueNode> optionalNode = context.inject(ValueNode.class);
        if (!optionalNode.isPresent()) {
            return CompletableFuture.completedFuture(Collections.emptyList());
        }
        ValueNode node = optionalNode.get();
        return node.getParser().suggestionProvider().suggestionsFuture(context, input);
    }

    private static class UnsupportedToolException extends IllegalStateException implements ComponentMessageThrowable {
        @Override
        public @Nullable Component componentMessage() {
            return Message.component("easyarmorstands.error.set-unsupported");
        }
    }
}
