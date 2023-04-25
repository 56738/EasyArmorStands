package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.session.Session;
import org.joml.Vector3dc;

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

    @Override
    public void onExit() {
        session.commit();
    }

    /**
     * Reverts the property to the original value it had when this node was {@link #onEnter() entered}.
     */
    protected abstract void abort();

    @Override
    public boolean onClick(Vector3dc eyes, Vector3dc target, ClickContext context) {
        if (context.getType() == ClickType.LEFT_CLICK) {
            abort();
        }
        session.popNode();
        return true;
    }
}
