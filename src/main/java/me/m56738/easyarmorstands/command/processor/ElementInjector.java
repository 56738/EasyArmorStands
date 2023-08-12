package me.m56738.easyarmorstands.command.processor;

import cloud.commandframework.annotations.AnnotationAccessor;
import cloud.commandframework.annotations.injection.ParameterInjector;
import cloud.commandframework.context.CommandContext;
import me.m56738.easyarmorstands.element.Element;
import me.m56738.easyarmorstands.session.Session;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ElementInjector<C> implements ParameterInjector<C, Element> {
    @Override
    public @Nullable Element create(@NonNull CommandContext<C> context, @NonNull AnnotationAccessor annotationAccessor) {
        Session session = SessionPreprocessor.getSessionOrNull(context);
        if (session != null) {
            return session.getElement();
        }
        return null;
    }
}
