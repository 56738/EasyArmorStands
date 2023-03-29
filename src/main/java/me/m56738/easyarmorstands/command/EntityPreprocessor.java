package me.m56738.easyarmorstands.command;

import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessingContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessor;
import cloud.commandframework.keys.CloudKey;
import cloud.commandframework.keys.SimpleCloudKey;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.Entity;
import org.checkerframework.checker.nullness.qual.NonNull;

public class EntityPreprocessor<C> implements CommandPreprocessor<C> {
    private static final CloudKey<Entity> ENTITY_KEY = SimpleCloudKey.of("entity", TypeToken.get(Entity.class));

    public static Entity getEntityOrNull(CommandContext<?> context) {
        return context.getOrDefault(ENTITY_KEY, null);
    }

    @Override
    public void accept(@NonNull CommandPreprocessingContext<C> context) {
        Session session = SessionPreprocessor.getSessionOrNull(context.getCommandContext());
        if (session == null) {
            return;
        }
        context.getCommandContext().set(ENTITY_KEY, session.getEntity());
    }
}
