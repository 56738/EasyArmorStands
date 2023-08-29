package me.m56738.easyarmorstands.display.editor.axis;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.axis.RotateAxis;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.display.editor.DisplayOffsetProvider;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.util.EasMath;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.joml.Quaterniond;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class DisplayLocalRotateAxis implements RotateAxis {
    private final PropertyContainer properties;
    private final Property<Location> locationProperty;
    private final Property<Quaternionfc> rotationProperty;
    private final Axis axis;
    private final OffsetProvider offsetProvider;
    private final Vector3d axisDirection = new Vector3d();
    private final Quaternionf currentRotation = new Quaternionf();
    private final Quaternionf originalRotation = new Quaternionf();

    public DisplayLocalRotateAxis(PropertyContainer properties, Axis axis) {
        this.properties = properties;
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.rotationProperty = properties.get(DisplayPropertyTypes.LEFT_ROTATION);
        this.axis = axis;
        this.offsetProvider = new DisplayOffsetProvider(properties);
    }

    @Override
    public Vector3dc getPosition() {
        return Util.toVector3d(locationProperty.getValue())
                .add(offsetProvider.getOffset());
    }

    @Override
    public Quaterniond getRotation() {
        Location location = locationProperty.getValue();
        return EasMath.getEntityRotation(location.getYaw(), location.getPitch(), new Quaterniond())
                .mul(new Quaterniond(rotationProperty.getValue()));
    }

    @Override
    public Axis getAxis() {
        return axis;
    }

    protected Quaternionfc get() {
        return rotationProperty.getValue();
    }

    protected void set(Quaternionfc value) {
        rotationProperty.setValue(value);
    }

    @Override
    public double start() {
        originalRotation.set(get());
        axis.getDirection().rotate(new Quaterniond(originalRotation), axisDirection);
        return 0;
    }

    @Override
    public void set(double value) {
        currentRotation.setAngleAxis(value, axisDirection.x, axisDirection.y, axisDirection.z).mul(originalRotation);
        set(currentRotation);
    }

    @Override
    public void revert() {
        set(originalRotation);
    }

    @Override
    public void commit() {
        properties.commit();
    }

    @Override
    public boolean isValid() {
        return properties.isValid();
    }
}
