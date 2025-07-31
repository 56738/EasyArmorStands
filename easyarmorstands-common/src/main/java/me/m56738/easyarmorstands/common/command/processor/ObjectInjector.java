package me.m56738.easyarmorstands.common.command.processor;

import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.injection.ParameterInjector;
import org.incendo.cloud.util.annotation.AnnotationAccessor;

public class ObjectInjector<C, T> implements ParameterInjector<C, T> {
    private final T value;

    private ObjectInjector(T value) {
        this.value = value;
    }

    public static <C, T> ParameterInjector<C, T> injector(T value) {
        return new ObjectInjector<>(value);
    }

    @Override
    public T create(CommandContext<C> context, AnnotationAccessor annotationAccessor) {
        return value;
    }
}
