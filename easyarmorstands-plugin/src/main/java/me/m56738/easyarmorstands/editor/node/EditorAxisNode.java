package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.EditorAxis;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.node.Node;

public abstract class EditorAxisNode implements Node {
    private final Session session;
    private final EditorAxis editorAxis;

    protected EditorAxisNode(Session session, EditorAxis editorAxis) {
        this.session = session;
        this.editorAxis = editorAxis;
    }

    @Override
    public void onExit(ExitContext context) {
        editorAxis.commit();
    }

    @Override
    public boolean onClick(ClickContext context) {
        if (context.type() == ClickContext.Type.LEFT_CLICK) {
            editorAxis.revert();
            session.popNode();
            return true;
        }
        if (context.type() == ClickContext.Type.RIGHT_CLICK) {
            session.popNode();
            return true;
        }
        return false;
    }

    @Override
    public boolean isValid() {
        return editorAxis.isValid();
    }
}
