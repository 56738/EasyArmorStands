package me.m56738.easyarmorstands.command;

import cloud.commandframework.annotations.AnnotationAccessor;
import cloud.commandframework.annotations.injection.ParameterInjector;
import cloud.commandframework.context.CommandContext;
import me.m56738.easyarmorstands.session.Session;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SessionInjector<C> implements ParameterInjector<C, Session> {
    @Override
    public @Nullable Session create(@NonNull CommandContext<C> context, @NonNull AnnotationAccessor annotationAccessor) {
        return SessionPreprocessor.getSession(context);
    }
}
