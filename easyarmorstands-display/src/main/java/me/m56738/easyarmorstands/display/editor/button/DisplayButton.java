package me.m56738.easyarmorstands.display.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.display.element.DisplayElement;
import me.m56738.easyarmorstands.editor.button.BoundingBoxButton;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Quaternionfc;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3fc;

public class DisplayButton extends BoundingBoxButton {
    private final DisplayElement<?> element;
    private final Property<Location> locationProperty;
    private final Property<Vector3fc> translationProperty;
    private final Property<Quaternionfc> rotationProperty;

    public DisplayButton(Session session, DisplayElement<?> element) {
        super(session);
        this.element = element;
        PropertyContainer container = element.getProperties();
        this.locationProperty = container.get(EntityPropertyTypes.LOCATION);
        this.translationProperty = container.get(DisplayPropertyTypes.TRANSLATION);
        this.rotationProperty = container.get(DisplayPropertyTypes.LEFT_ROTATION);
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
    protected BoundingBox getBoundingBox() {
        return element.getBoundingBox();
    }
}
