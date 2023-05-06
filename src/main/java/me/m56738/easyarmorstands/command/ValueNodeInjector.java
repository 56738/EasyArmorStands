package me.m56738.easyarmorstands.command;

import cloud.commandframework.annotations.AnnotationAccessor;
import cloud.commandframework.annotations.injection.ParameterInjector;
import cloud.commandframework.context.CommandContext;
import me.m56738.easyarmorstands.node.Node;
import me.m56738.easyarmorstands.node.ValueNode;
import me.m56738.easyarmorstands.session.Session;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings("rawtypes")
public class ValueNodeInjector<C> implements ParameterInjector<C, ValueNode> {
    @Override
    public @Nullable ValueNode create(@NonNull CommandContext<C> context, @NonNull AnnotationAccessor annotationAccessor) {
        Session session = SessionPreprocessor.getSessionOrNull(context);
        if (session == null) {
            return null;
        }
        Node node = session.getNode();
        if (!(node instanceof ValueNode)) {
            return null;
        }
        ValueNode valueNode = (ValueNode) node;
        String permission = valueNode.getValuePermission();
        if (permission != null && !context.hasPermission(permission)) {
            return null;
        }
        return valueNode;
    }
}
