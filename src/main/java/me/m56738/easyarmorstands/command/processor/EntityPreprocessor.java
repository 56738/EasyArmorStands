package me.m56738.easyarmorstands.command.processor;

import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessingContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessor;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.Entity;
import org.checkerframework.checker.nullness.qual.NonNull;

public class EntityPreprocessor<C> implements CommandPreprocessor<C> {
    public static Entity getEntityOrNull(CommandContext<?> context) {
        return context.getOrDefault(Keys.ENTITY, null);
    }

    @Override
    public void accept(@NonNull CommandPreprocessingContext<C> context) {
        Session session = SessionPreprocessor.getSessionOrNull(context.getCommandContext());
        if (session == null) {
            return;
        }
        context.getCommandContext().set(Keys.ENTITY, session.getEntity());
    }
}
