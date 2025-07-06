package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.injection.ParameterInjector;
import me.m56738.easyarmorstands.lib.cloud.paper.util.sender.Source;
import me.m56738.easyarmorstands.lib.cloud.util.annotation.AnnotationAccessor;
import me.m56738.easyarmorstands.session.SessionImpl;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import static me.m56738.easyarmorstands.command.processor.SessionProcessor.sessionKey;

public class SessionInjector implements ParameterInjector<Source, SessionImpl> {
    @Override
    public @Nullable SessionImpl create(@NonNull CommandContext<Source> context, @NonNull AnnotationAccessor annotationAccessor) {
        if (context.contains(sessionKey())) {
            return context.get(sessionKey());
        } else {
            return null;
        }
    }
}
