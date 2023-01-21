package gg.bundlegroup.easyarmorstands.common.command;

import cloud.commandframework.annotations.AnnotationAccessor;
import cloud.commandframework.annotations.injection.ParameterInjector;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.exceptions.InvalidCommandSenderException;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import gg.bundlegroup.easyarmorstands.common.session.SessionManager;
import gg.bundlegroup.easyarmorstands.common.platform.EasPlayer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collections;

public class SessionInjector<C> implements ParameterInjector<C, Session> {
    private final SessionManager sessionManager;

    public SessionInjector(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public @Nullable Session create(@NonNull CommandContext<C> context, @NonNull AnnotationAccessor annotationAccessor) {
        C sender = context.getSender();
        if (!(sender instanceof EasPlayer)) {
            throw new InvalidCommandSenderException(sender, EasPlayer.class, Collections.emptyList());
        }
        EasPlayer player = (EasPlayer) sender;
        Session session = sessionManager.getSession(player);
        if (session == null) {
            throw new NoSessionException();
        }
        return session;
    }
}
