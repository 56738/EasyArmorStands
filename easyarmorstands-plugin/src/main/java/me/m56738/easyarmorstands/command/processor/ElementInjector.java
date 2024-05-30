package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.injection.ParameterInjector;
import org.incendo.cloud.util.annotation.AnnotationAccessor;

import static me.m56738.easyarmorstands.command.processor.ElementProcessor.elementKey;

public class ElementInjector implements ParameterInjector<EasCommandSender, Element> {
    @Override
    public @Nullable Element create(@NonNull CommandContext<EasCommandSender> context, @NonNull AnnotationAccessor annotationAccessor) {
        return context.getOrDefault(elementKey(), null);
    }
}
