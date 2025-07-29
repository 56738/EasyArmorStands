package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.common.editor.SessionImpl;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.injection.ParameterInjector;
import org.incendo.cloud.util.annotation.AnnotationAccessor;

import static me.m56738.easyarmorstands.command.processor.SessionProcessor.sessionKey;

public class SessionInjector implements ParameterInjector<CommandSource, SessionImpl> {
    @Override
    public @Nullable SessionImpl create(@NonNull CommandContext<CommandSource> context, @NonNull AnnotationAccessor annotationAccessor) {
        if (context.contains(sessionKey())) {
            return context.get(sessionKey());
        } else {
            return null;
        }
    }
}
