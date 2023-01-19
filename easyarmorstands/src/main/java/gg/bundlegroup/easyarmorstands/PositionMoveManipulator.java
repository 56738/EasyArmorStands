package gg.bundlegroup.easyarmorstands;

import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.joml.Vector3d;

public class PositionMoveManipulator implements Manipulator {
    private final PositionHandle handle;
    private final Vector3d offset = new Vector3d();
    private final Vector3d position = new Vector3d();

    public PositionMoveManipulator(PositionHandle handle) {
        this.handle = handle;
    }

    @Override
    public void start() {
        handle.getSession().getEntity().getPosition().sub(handle.getSession().getCursor().get(), offset);
    }

    @Override
    public void update() {
        Session session = handle.getSession();
        session.getCursor().get().add(offset, position);
        EasArmorStand entity = handle.getSession().getEntity();
        EasArmorStand skeleton = session.getSkeleton();
        float yaw = entity.getYaw();
        entity.teleport(position, yaw, 0);
        if (skeleton != null) {
            skeleton.teleport(position, yaw, 0);
        }
    }

    @Override
    public Component getComponent() {
        return Component.text("Move", NamedTextColor.YELLOW);
    }
}
