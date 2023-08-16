package me.m56738.easyarmorstands.command.processor;

import cloud.commandframework.annotations.AnnotationAccessor;
import cloud.commandframework.annotations.injection.ParameterInjector;
import cloud.commandframework.context.CommandContext;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.node.Node;
import me.m56738.easyarmorstands.node.ValueNode;
import me.m56738.easyarmorstands.session.Session;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings("rawtypes")
public class ValueNodeInjector implements ParameterInjector<EasCommandSender, ValueNode> {
    @Override
    public @Nullable ValueNode create(@NonNull CommandContext<EasCommandSender> context, @NonNull AnnotationAccessor annotationAccessor) {
        EasCommandSender sender = context.getSender();
        if (!(sender instanceof EasPlayer)) {
            return null;
        }
        EasPlayer player = (EasPlayer) sender;
        Session session = player.session();
        if (session == null) {
            return null;
        }
        Node node = session.getNode();
        if (!(node instanceof ValueNode)) {
            return null;
        }
        return (ValueNode) node;
    }
}
