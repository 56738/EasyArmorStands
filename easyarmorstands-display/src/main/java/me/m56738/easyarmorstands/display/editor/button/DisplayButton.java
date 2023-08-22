package me.m56738.easyarmorstands.display.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.node.AxisAlignedBoxButton;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Quaternionfc;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3fc;

public class DisplayButton extends AxisAlignedBoxButton {
    private final Property<Location> locationProperty;
    private final Property<Vector3fc> translationProperty;
    private final Property<Quaternionfc> rotationProperty;
    private final Property<Float> widthProperty;
    private final Property<Float> heightProperty;

    public DisplayButton(Session session, PropertyContainer container) {
        super(session);
        this.locationProperty = container.get(EntityPropertyTypes.LOCATION);
        this.translationProperty = container.get(DisplayPropertyTypes.TRANSLATION);
        this.rotationProperty = container.get(DisplayPropertyTypes.LEFT_ROTATION);
        this.widthProperty = container.get(DisplayPropertyTypes.BOX_WIDTH);
        this.heightProperty = container.get(DisplayPropertyTypes.BOX_HEIGHT);
    }

    private Quaterniond getLocationRotation() {
        return Util.getRoundedYawPitchRotation(locationProperty.getValue(), new Quaterniond());
    }

    @Override
    protected Vector3dc getPosition() {
        return Util.toVector3d(locationProperty.getValue())
                .add(new Vector3d(translationProperty.getValue())
                        .rotate(getLocationRotation()));
    }

    @Override
    public Quaterniondc getRotation() {
        return getLocationRotation().mul(new Quaterniond(rotationProperty.getValue()));
    }

    @Override
    protected Vector3dc getCenter() {
        Location location = locationProperty.getValue();
        return new Vector3d(location.getX(), location.getY() + heightProperty.getValue() / 2, location.getZ());
    }

    @Override
    protected Vector3dc getSize() {
        double width = widthProperty.getValue();
        double height = heightProperty.getValue();
        return new Vector3d(width, height, width);
    }
}
