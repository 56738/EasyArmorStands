package me.m56738.easyarmorstands.command.parser;

import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.exceptions.parsing.NoInputProvidedException;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.command.processor.EntityPreprocessor;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.property.LegacyEntityPropertyType;
import org.bukkit.entity.Entity;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class EntityPropertyArgumentParser<T extends LegacyEntityPropertyType<?, ?>> implements ArgumentParser<EasCommandSender, T> {
    private final Class<T> type;

    public EntityPropertyArgumentParser(Class<T> type) {
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NonNull ArgumentParseResult<@NonNull T> parse(@NonNull CommandContext<@NonNull EasCommandSender> commandContext, @NonNull Queue<@NonNull String> inputQueue) {
        String input = inputQueue.peek();
        if (input == null) {
            return ArgumentParseResult.failure(new NoInputProvidedException(getClass(), commandContext));
        }

        Entity entity = EntityPreprocessor.getEntityOrNull(commandContext);
        if (entity == null) {
            return ArgumentParseResult.failure(new IllegalStateException("No entity selected"));
        }

        LegacyEntityPropertyType<Entity, ?> property = EasyArmorStands.getInstance().getEntityPropertyRegistry().getProperty(entity, input);
        if (!type.isInstance(property)) {
            return ArgumentParseResult.failure(new IllegalArgumentException("Property not found"));
        }

        inputQueue.remove();
        return ArgumentParseResult.success((T) property);
    }

    @Override
    public @NonNull List<@NonNull String> suggestions(@NonNull CommandContext<EasCommandSender> commandContext, @NonNull String input) {
        Entity entity = EntityPreprocessor.getEntityOrNull(commandContext);
        if (entity == null) {
            return Collections.emptyList();
        }

        Map<String, LegacyEntityPropertyType<? super Entity, ?>> properties = EasyArmorStands.getInstance().getEntityPropertyRegistry().getProperties(entity);
        ArrayList<String> result = new ArrayList<>(properties.size());
        for (Map.Entry<String, LegacyEntityPropertyType<? super Entity, ?>> entry : properties.entrySet()) {
            if (type.isInstance(entry.getValue())) {
                result.add(entry.getKey());
            }
        }
        return result;
    }
}
