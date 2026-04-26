package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.tool.AxisMoveToolNodeBuilder;
import me.m56738.easyarmorstands.api.editor.node.tool.AxisRotateToolNodeBuilder;
import me.m56738.easyarmorstands.api.editor.node.tool.AxisScaleToolNodeBuilder;
import me.m56738.easyarmorstands.api.editor.node.NodeProvider;
import me.m56738.easyarmorstands.api.editor.node.tool.MoveToolNodeBuilder;
import me.m56738.easyarmorstands.api.editor.node.tool.ScaleToolNodeBuilder;
import me.m56738.easyarmorstands.editor.tool.node.AxisMoveToolNodeBuilderImpl;
import me.m56738.easyarmorstands.editor.tool.node.AxisRotateToolNodeBuilderImpl;
import me.m56738.easyarmorstands.editor.tool.node.AxisScaleToolNodeBuilderImpl;
import me.m56738.easyarmorstands.editor.tool.node.MoveToolNodeBuilderImpl;
import me.m56738.easyarmorstands.editor.tool.node.ScaleToolNodeBuilderImpl;
import org.jetbrains.annotations.NotNull;

class NodeProviderImpl implements NodeProvider {
    private final Session session;

    NodeProviderImpl(Session session) {
        this.session = session;
    }

    @Override
    public @NotNull AxisMoveToolNodeBuilder axisMove() {
        return new AxisMoveToolNodeBuilderImpl(session);
    }

    @Override
    public @NotNull AxisScaleToolNodeBuilder axisScale() {
        return new AxisScaleToolNodeBuilderImpl(session);
    }

    @Override
    public @NotNull AxisRotateToolNodeBuilder axisRotate() {
        return new AxisRotateToolNodeBuilderImpl(session);
    }

    @Override
    public @NotNull MoveToolNodeBuilder move() {
        return new MoveToolNodeBuilderImpl(session);
    }

    @Override
    public @NotNull ScaleToolNodeBuilder scale() {
        return new ScaleToolNodeBuilderImpl(session);
    }
}
