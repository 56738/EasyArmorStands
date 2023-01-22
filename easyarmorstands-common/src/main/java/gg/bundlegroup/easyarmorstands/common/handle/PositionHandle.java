package gg.bundlegroup.easyarmorstands.common.handle;

import gg.bundlegroup.easyarmorstands.common.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import net.kyori.adventure.text.Component;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class PositionHandle extends AbstractHandle {
    private final Session session;
    private final Vector3d position = new Vector3d();

    public PositionHandle(Session session) {
        super(session);
        this.session = session;
    }

    @Override
    public void update(boolean active) {
        EasArmorStand entity = session.getEntity();
        double y = 1.25;
        if (entity.isSmall()) {
            y /= 2;
        }
        entity.getPosition().add(0, y, 0, position);
        super.update(active);
    }

    @Override
    public Vector3dc getPosition() {
        return position;
    }

    @Override
    public Component subtitle() {
        return Component.text("Position");
    }

    public Session getSession() {
        return session;
    }
}
