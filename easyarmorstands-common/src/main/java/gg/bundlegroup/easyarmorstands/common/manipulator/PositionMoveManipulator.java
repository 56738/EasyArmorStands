package gg.bundlegroup.easyarmorstands.common.manipulator;

import gg.bundlegroup.easyarmorstands.common.handle.PositionHandle;
import gg.bundlegroup.easyarmorstands.common.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.common.util.Cursor3D;
import net.kyori.adventure.util.RGBLike;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class PositionMoveManipulator extends Manipulator {
    private final PositionHandle handle;
    private final Cursor3D cursor;
    private final Vector3d offset = new Vector3d();
    private final Vector3d position = new Vector3d();

    public PositionMoveManipulator(PositionHandle handle, String name, RGBLike color) {
        super(name, color);
        this.handle = handle;
        this.cursor = new Cursor3D(handle.getSession().getPlayer());
    }

    @Override
    public void start(Vector3dc cursor) {
        this.cursor.start(cursor, false);
        handle.getSession().getEntity().getPosition().sub(getCursor(), offset);
    }

    @Override
    public void update(boolean freeLook) {
        cursor.update(freeLook);
        getCursor().add(offset, position);
        EasArmorStand entity = handle.getSession().getEntity();
        float yaw = entity.getYaw();
        entity.teleport(position, yaw, 0);
    }

    @Override
    public Vector3dc getCursor() {
        return cursor.get();
    }
}
