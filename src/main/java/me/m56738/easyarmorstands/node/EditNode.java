package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.session.Session;
import org.joml.Vector3dc;

public abstract class EditNode implements Node {
    private final Session session;

    public EditNode(Session session) {
        this.session = session;
    }

    @Override
    public void onExit() {
        session.commit();
    }

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
