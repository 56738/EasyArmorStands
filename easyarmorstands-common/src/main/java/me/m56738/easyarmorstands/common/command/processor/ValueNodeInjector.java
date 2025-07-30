package me.m56738.easyarmorstands.common.command.processor;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.SessionManager;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.common.editor.node.ValueNode;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.common.platform.command.PlayerCommandSource;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.injection.ParameterInjector;
import org.incendo.cloud.util.annotation.AnnotationAccessor;

@SuppressWarnings("rawtypes")
public class ValueNodeInjector implements ParameterInjector<CommandSource, ValueNode> {
    @Override
    public @Nullable ValueNode create(@NonNull CommandContext<CommandSource> context, @NonNull AnnotationAccessor annotationAccessor) {
        if (!(context.sender() instanceof PlayerCommandSource playerSource)) {
            return null;
        }
        SessionManager sessionManager = context.inject(SessionManager.class).orElseThrow();
        Session session = sessionManager.getSession(playerSource.source());
        if (session == null) {
            return null;
        }
        Node node = session.getNode();
        if (!(node instanceof ValueNode valueNode)) {
            return null;
        }
        if (!valueNode.canSetValue()) {
            return null;
        }
        return valueNode;
    }
}
