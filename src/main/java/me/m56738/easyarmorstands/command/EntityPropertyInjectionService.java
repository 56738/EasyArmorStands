package me.m56738.easyarmorstands.command;

import cloud.commandframework.annotations.AnnotationAccessor;
import cloud.commandframework.annotations.injection.InjectionService;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.types.tuples.Triplet;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.property.EntityProperty;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class EntityPropertyInjectionService<C> implements InjectionService<C> {
    @Override
    public @Nullable Object handle(@NonNull Triplet<CommandContext<C>, Class<?>, AnnotationAccessor> triplet) {
        Class<?> type = triplet.getSecond();
        for (EntityProperty<?, ?> property : EasyArmorStands.getInstance().getEntityPropertyRegistry().getProperties().values()) {
            if (type.isAssignableFrom(property.getClass())) {
                return property;
            }
        }
        return null;
    }
}
