package me.m56738.easyarmorstands.common.command.processor;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.injection.ParameterInjector;
import org.incendo.cloud.util.annotation.AnnotationAccessor;

import static me.m56738.easyarmorstands.common.command.processor.ElementProcessor.elementKey;

public class ElementInjector implements ParameterInjector<CommandSource, Element> {
    @Override
    public @Nullable Element create(@NonNull CommandContext<CommandSource> context, @NonNull AnnotationAccessor annotationAccessor) {
        if (context.contains(elementKey())) {
            return context.get(elementKey());
        } else {
            return null;
        }
    }
}
