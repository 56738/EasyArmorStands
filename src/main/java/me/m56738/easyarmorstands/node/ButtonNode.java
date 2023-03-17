package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.format.NamedTextColor;
import org.joml.Vector3dc;

public abstract class ButtonNode implements ClickableNode {
    private final Session session;
    private final Node node;
    private int priority = 0;

    public ButtonNode(Session session, Node node) {
        this.session = session;
        this.node = node;
    }

    protected abstract Vector3dc getPosition();

    @Override
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public Vector3dc updatePreview(Vector3dc eyes, Vector3dc target) {
        Vector3dc position = getPosition();
        if (session.isLookingAtPoint(eyes, target, position)) {
            return position;
        }
        return null;
    }

    @Override
    public void showPreview(boolean focused) {
        session.showPoint(getPosition(), focused ? NamedTextColor.YELLOW : NamedTextColor.WHITE);
    }

    @Override
    public void onEnter() {
        node.onEnter();
    }

    @Override
    public void onExit() {
        node.onExit();
    }

    @Override
    public void onUpdate(Vector3dc eyes, Vector3dc target) {
        node.onUpdate(eyes, target);
    }

    @Override
    public boolean onClick(Vector3dc eyes, Vector3dc target, ClickType type) {
        return node.onClick(eyes, target, type);
    }
}
