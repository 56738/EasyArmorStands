package me.m56738.easyarmorstands.command.processor;

import cloud.commandframework.annotations.AnnotationAccessor;
import cloud.commandframework.annotations.injection.InjectionService;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.types.tuples.Triplet;
import org.bukkit.entity.Entity;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class EntityInjectionService<C> implements InjectionService<C> {
    @Override
    public @Nullable Object handle(@NonNull Triplet<CommandContext<C>, Class<?>, AnnotationAccessor> triplet) {
        CommandContext<C> context = triplet.getFirst();
        Class<?> type = triplet.getSecond();
        if (!Entity.class.isAssignableFrom(type)) {
            return null;
        }
        Entity entity = EntityPreprocessor.getEntityOrNull(context);
        if (entity == null || !type.isAssignableFrom(entity.getClass())) {
            return null;
        }
        return entity;
    }
}
