package gg.bundlegroup.easyarmorstands.manipulator;

import gg.bundlegroup.easyarmorstands.util.Cursor3D;
import gg.bundlegroup.easyarmorstands.handle.PositionHandle;
import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import net.kyori.adventure.util.RGBLike;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class PositionAxisManipulator extends AxisManipulator {
    private final PositionHandle handle;
    private final Cursor3D cursor;
    private final Vector3d offset = new Vector3d();
    private final Vector3d position = new Vector3d();

    public PositionAxisManipulator(PositionHandle handle, String name, RGBLike color, Vector3dc axis) {
        super(handle.getSession(), name, color, axis);
        this.handle = handle;
        this.cursor = new Cursor3D(handle.getSession().getPlayer());
    }

    @Override
    protected void start(Vector3dc cursor, Vector3d origin, Vector3d axisDirection) {
        this.cursor.start(cursor, false);
        origin.set(handle.getPosition());
        axisDirection.set(getAxis());
        updateAxisPoint(cursor);
        handle.getSession().getEntity().getPosition().sub(getAxisPoint(), offset);
    }

    @Override
    public void update() {
        cursor.update();
        getOrigin().set(handle.getPosition());
        super.update();

        EasArmorStand entity = handle.getSession().getEntity();
        EasArmorStand skeleton = handle.getSession().getSkeleton();
        float yaw = entity.getYaw();
        getAxisPoint().add(offset, position);
        entity.teleport(position, yaw, 0);
        if (skeleton != null) {
            skeleton.teleport(position, yaw, 0);
        }
    }

    @Override
    public Vector3dc getCursor() {
        return cursor.get();
    }
}