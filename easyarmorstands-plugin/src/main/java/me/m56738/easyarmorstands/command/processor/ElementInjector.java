package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.api.element.Element;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.injection.ParameterInjector;
import org.incendo.cloud.paper.util.sender.Source;
import org.incendo.cloud.util.annotation.AnnotationAccessor;

import static me.m56738.easyarmorstands.command.processor.ElementProcessor.elementKey;

public class ElementInjector implements ParameterInjector<Source, Element> {
    @Override
    public @Nullable Element create(@NonNull CommandContext<Source> context, @NonNull AnnotationAccessor annotationAccessor) {
        if (context.contains(elementKey())) {
            return context.get(elementKey());
        } else {
            return null;
        }
    }
}
