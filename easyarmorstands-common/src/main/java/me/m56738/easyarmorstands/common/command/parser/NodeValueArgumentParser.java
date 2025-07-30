package me.m56738.easyarmorstands.common.command.parser;

import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.editor.node.ValueNode;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.util.ComponentMessageThrowable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.suggestion.Suggestion;
import org.incendo.cloud.suggestion.SuggestionProvider;
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
