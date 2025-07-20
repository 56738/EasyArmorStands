package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.editor.node.ValueNode;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.injection.ParameterInjector;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.incendo.cloud.paper.util.sender.Source;
import org.incendo.cloud.util.annotation.AnnotationAccessor;

@SuppressWarnings("rawtypes")
public class ValueNodeInjector implements ParameterInjector<Source, ValueNode> {
    @Override
    public @Nullable ValueNode create(@NonNull CommandContext<Source> context, @NonNull AnnotationAccessor annotationAccessor) {
        if (!(context.sender() instanceof PlayerSource playerSource)) {
            return null;
        }
        Session session = EasyArmorStandsPlugin.getInstance().sessionManager().getSession(PaperPlayer.fromNative(playerSource.source()));
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
