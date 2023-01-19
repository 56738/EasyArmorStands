package gg.bundlegroup.easyarmorstands;

import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import net.kyori.adventure.text.Component;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.awt.*;

public class PositionAxisManipulator extends AxisManipulator {
    private final PositionHandle handle;
    private final Component component;
    private final Vector3d offset = new Vector3d();
    private final Vector3d position = new Vector3d();

    public PositionAxisManipulator(PositionHandle handle, Component component, Vector3dc axis, Color color) {
        super(handle.getSession(), axis, color);
        this.handle = handle;
        this.component = component;
    }

    @Override
    protected void start(Vector3d origin, Vector3d axisDirection) {
        origin.set(handle.getPosition());
        axisDirection.set(getAxis());
        updateAxisPoint();
        handle.getSession().getEntity().getPosition().sub(getAxisPoint(), offset);
    }

    @Override
    public void update() {
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
    public Component getComponent() {
        return component;
    }
}
