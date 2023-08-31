package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.group.GroupRotateTool;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class SimpleEntityGroupYawTool extends SimpleEntityGroupTool implements GroupRotateTool {
    private final Axis axis;
    private final float originalYaw;
    private final Vector3dc originalOffset;

    public SimpleEntityGroupYawTool(PropertyContainer properties, Vector3dc anchor, Axis axis) {
        super(properties);
        Location originalLocation = getOriginalLocation();
        this.axis = axis;
        this.originalYaw = originalLocation.getYaw();
        this.originalOffset = Util.toVector3d(originalLocation).sub(anchor);
    }

    @Override
    public void setAngle(double angle) {
        Vector3dc direction = axis.getDirection();
        Vector3dc offset = originalOffset.rotateAxis(angle, direction.x(), direction.y(), direction.z(), new Vector3d())
                .sub(originalOffset);

        Location location = getOriginalLocation();
        location.add(offset.x(), offset.y(), offset.z());
        location.setYaw(originalYaw - (float) Math.toDegrees(angle));
        setLocation(location);
    }
}
