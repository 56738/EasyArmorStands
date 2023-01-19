package gg.bundlegroup.easyarmorstands;

import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import net.kyori.adventure.util.RGBLike;
import org.joml.Vector3d;

public class PositionMoveManipulator extends Manipulator {
    private final PositionHandle handle;
    private final Vector3d offset = new Vector3d();
    private final Vector3d position = new Vector3d();

    public PositionMoveManipulator(PositionHandle handle, String name, RGBLike color) {
        super(name, color);
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
}
