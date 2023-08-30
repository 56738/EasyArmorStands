package me.m56738.easyarmorstands.display.editor.axis;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.axis.CarryAxis;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.display.DisplayBox;
import me.m56738.easyarmorstands.util.Util;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class DisplayBoxCarryAxis implements CarryAxis {
    private final PropertyContainer properties;
    private final Vector3d relativePosition = new Vector3d();
    private final Vector3d temp = new Vector3d();
    private final DisplayBox box;

    public DisplayBoxCarryAxis(PropertyContainer properties) {
        this.properties = properties;
        this.box = new DisplayBox(properties);
    }

    @Override
    public Vector3dc getPosition() {
        return box.getPosition();
    }

    @Override
    public Quaterniondc getRotation() {
        return Util.IDENTITY;
    }

    @Override
    public void start(EyeRay eyeRay) {
        box.saveOriginal();
        eyeRay.inverseMatrix().transformPosition(box.getPosition(), relativePosition);
    }

    @Override
    public void update(EyeRay eyeRay) {
        box.setPosition(eyeRay.matrix().transformPosition(relativePosition, temp));
    }

    @Override
    public void revert() {
        box.restoreOriginal();
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
