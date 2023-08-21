package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.node.Node;

/**
 * A node which edits a property.
 * <p>
 * Right-clicking confirms the changes, left-clicking {@link #abort() aborts} and reverts them.
 */
public abstract class EditNode implements Node {
    private final Session session;

    public EditNode(Session session) {
        this.session = session;
    }

    /**
     * Reverts the property to the original value it had when this node was {@link Node#onEnter(me.m56738.easyarmorstands.api.editor.context.EnterContext) entered}.
     */
    protected abstract void abort();

    @Override
    public boolean onClick(ClickContext context) {
        if (context.type() == ClickContext.Type.LEFT_CLICK) {
            abort();
        }
        session.popNode();
        return true;
    }
}
