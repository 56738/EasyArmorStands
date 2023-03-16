package me.m56738.easyarmorstands.command;

import cloud.commandframework.annotations.AnnotationAccessor;
import cloud.commandframework.annotations.injection.ParameterInjector;
import cloud.commandframework.context.CommandContext;
import me.m56738.easyarmorstands.node.Node;
import me.m56738.easyarmorstands.node.ValueNode;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings("rawtypes")
public class ValueNodeInjector implements ParameterInjector<CommandSender, ValueNode> {
    @Override
    public @Nullable ValueNode create(@NonNull CommandContext<CommandSender> context, @NonNull AnnotationAccessor annotationAccessor) {
        Session session = SessionPreprocessor.getSession(context);
        Node node = session.getNode();
        if (!(node instanceof ValueNode)) {
            return null;
        }
        return (ValueNode) node;
    }
}
