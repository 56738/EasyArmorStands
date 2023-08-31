package me.m56738.easyarmorstands.display.element;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.group.GroupRotateTool;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.element.SimpleEntityGroupTool;
import me.m56738.easyarmorstands.util.EasMath;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.joml.Quaterniond;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class DisplayGroupRotateTool extends SimpleEntityGroupTool implements GroupRotateTool {
    private final Axis axis;
    private final Property<Quaternionfc> rotationProperty;
    private final Quaternionfc originalRotation;
    private final Vector3dc originalOffset;
    private final Vector3d axisDirection = new Vector3d();
    private final Quaternionf currentRotation = new Quaternionf();

    public DisplayGroupRotateTool(PropertyContainer properties, Vector3dc anchor, Axis axis) {
        super(properties);
        this.axis = axis;
        Location originalLocation = getOriginalLocation();
        this.rotationProperty = properties.get(DisplayPropertyTypes.LEFT_ROTATION);
        this.originalRotation = new Quaternionf(rotationProperty.getValue());
        this.originalOffset = Util.toVector3d(originalLocation)
                .add(properties.get(DisplayPropertyTypes.TRANSLATION).getValue())
                .sub(anchor);
        axis.getDirection().rotate(
                EasMath.getInverseEntityRotation(
                        originalLocation.getYaw(), originalLocation.getPitch(), new Quaterniond()),
                axisDirection);
    }

    @Override
    public void setAngle(double angle) {
        Vector3dc direction = axis.getDirection();
        Vector3dc offset = originalOffset.rotateAxis(angle, direction.x(), direction.y(), direction.z(), new Vector3d())
                .sub(originalOffset);

        Location location = getOriginalLocation();
        location.add(offset.x(), offset.y(), offset.z());
        setLocation(location);

        currentRotation.setAngleAxis(angle, axisDirection.x, axisDirection.y, axisDirection.z).mul(originalRotation);
        rotationProperty.setValue(currentRotation);
    }

    @Override
    public void revert() {
        super.revert();
        rotationProperty.setValue(originalRotation);
    }
}
