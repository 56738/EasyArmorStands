package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.editor.node.ValueNode;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.injection.ParameterInjector;
import org.incendo.cloud.util.annotation.AnnotationAccessor;

@SuppressWarnings("rawtypes")
public class ValueNodeInjector implements ParameterInjector<EasCommandSender, ValueNode> {
    @Override
    public @Nullable ValueNode create(@NonNull CommandContext<EasCommandSender> context, @NonNull AnnotationAccessor annotationAccessor) {
        EasCommandSender sender = context.sender();
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
