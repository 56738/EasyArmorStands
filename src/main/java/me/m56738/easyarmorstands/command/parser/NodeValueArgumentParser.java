package me.m56738.easyarmorstands.command.parser;

import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.context.CommandContext;
import me.m56738.easyarmorstands.node.ValueNode;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

public class NodeValueArgumentParser<C> implements ArgumentParser<C, Object> {
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public @NonNull ArgumentParseResult<@NonNull Object> parse(@NonNull CommandContext<@NonNull C> commandContext, @NonNull Queue<@NonNull String> inputQueue) {
        Optional<ValueNode> optionalNode = commandContext.inject(ValueNode.class);
        if (!optionalNode.isPresent()) {
            return ArgumentParseResult.failure(new IllegalStateException("No supported tool is selected"));
        }
        ValueNode node = optionalNode.get();
        return node.getParser().parse(commandContext, inputQueue);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public @NonNull List<@NonNull String> suggestions(@NonNull CommandContext<C> commandContext, @NonNull String input) {
        Optional<ValueNode> optionalNode = commandContext.inject(ValueNode.class);
        if (!optionalNode.isPresent()) {
            return Collections.emptyList();
        }
        ValueNode node = optionalNode.get();
        return node.getParser().suggestions(commandContext, input);
    }
}
