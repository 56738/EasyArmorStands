package me.m56738.easyarmorstands.common.command.processor;

import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.injection.ParameterInjector;
import org.incendo.cloud.util.annotation.AnnotationAccessor;

import java.util.function.Supplier;

public class ObjectInjector<C, T> implements ParameterInjector<C, T> {
    private final Supplier<T> supplier;

    private ObjectInjector(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <C, T> ObjectInjector<C, T> injector(Supplier<T> supplier) {
        return new ObjectInjector<>(supplier);
    }

    @Override
    public T create(CommandContext<C> context, AnnotationAccessor annotationAccessor) {
        return supplier.get();
    }
}
