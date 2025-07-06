package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.injection.ParameterInjector;
import me.m56738.easyarmorstands.lib.cloud.paper.util.sender.Source;
import me.m56738.easyarmorstands.lib.cloud.util.annotation.AnnotationAccessor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

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
