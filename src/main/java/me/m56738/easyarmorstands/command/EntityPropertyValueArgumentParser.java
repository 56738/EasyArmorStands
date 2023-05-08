package me.m56738.easyarmorstands.command;

import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.context.CommandContext;
import me.m56738.easyarmorstands.property.EntityProperty;
import org.bukkit.entity.Entity;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Queue;

@SuppressWarnings({"unchecked", "rawtypes"})
public class EntityPropertyValueArgumentParser implements ArgumentParser<EasCommandSender, Object> {
    @Override
    public @NonNull ArgumentParseResult<@NonNull Object> parse(@NonNull CommandContext<@NonNull EasCommandSender> commandContext, @NonNull Queue<@NonNull String> inputQueue) {
        Entity entity = EntityPreprocessor.getEntityOrNull(commandContext);
        if (entity == null) {
            return ArgumentParseResult.failure(new IllegalStateException("No entity selected"));
        }

        EntityProperty property = commandContext.getOrDefault("property", null);
        if (property == null) {
            return ArgumentParseResult.failure(new IllegalStateException("No property provided"));
        }

        ArgumentParser parser = property.getArgumentParser();
        if (parser == null) {
            return ArgumentParseResult.failure(new IllegalStateException("Unsupported property"));
        }

        return parser.parse(commandContext, inputQueue);
    }

    @Override
    public @NonNull List<@NonNull String> suggestions(@NonNull CommandContext<EasCommandSender> commandContext, @NonNull String input) {
        Entity entity = EntityPreprocessor.getEntityOrNull(commandContext);
        if (entity == null) {
            return Collections.emptyList();
        }

        EntityProperty property = commandContext.getOrDefault("property", null);
        if (property == null) {
            return Collections.emptyList();
        }

        ArgumentParser parser = property.getArgumentParser();
        if (parser == null) {
            return Collections.emptyList();
        }

        return parser.suggestions(commandContext, input);
    }
}
