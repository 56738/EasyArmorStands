package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public abstract class SimpleButton implements Button {
    private final Session session;
    private int priority = 0;
    private Vector3dc lookTarget;

    public SimpleButton(Session session) {
        this.session = session;
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
    public void update(Vector3dc eyes, Vector3dc target) {
        Vector3dc position = getPosition();
        if (session.isLookingAtPoint(eyes, target, position)) {
            lookTarget = position;
        } else {
            lookTarget = null;
        }
    }

    @Override
    public @Nullable Vector3dc getLookTarget() {
        return lookTarget;
    }

    @Override
    public void showPreview(boolean focused) {
        session.showPoint(getPosition(), focused ? NamedTextColor.YELLOW : NamedTextColor.WHITE);
    }
}
