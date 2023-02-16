package me.m56738.easyarmorstands.core.command;

import cloud.commandframework.annotations.AnnotationAccessor;
import cloud.commandframework.annotations.injection.ParameterInjector;
import cloud.commandframework.context.CommandContext;
import me.m56738.easyarmorstands.core.session.Session;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SessionInjector<C, T extends Session> implements ParameterInjector<C, T> {
    private final Class<T> type;

    public SessionInjector(Class<T> type) {
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable T create(@NonNull CommandContext<C> context, @NonNull AnnotationAccessor annotationAccessor) {
        Session session = SessionPreprocessor.getSession(context);
        if (!type.isAssignableFrom(session.getClass())) {
            throw new NoSessionException();
        }
        return (T) session;
    }
}
