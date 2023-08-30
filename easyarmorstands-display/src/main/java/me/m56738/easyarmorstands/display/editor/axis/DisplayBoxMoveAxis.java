package me.m56738.easyarmorstands.display.editor.axis;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.axis.MoveAxis;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.display.DisplayBox;
import me.m56738.easyarmorstands.util.Util;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class DisplayBoxMoveAxis implements MoveAxis {
    private final PropertyContainer container;
    private final Axis axis;
    private final DisplayBox box;
    private double initialValue;

    public DisplayBoxMoveAxis(PropertyContainer container, Axis axis) {
        this.container = container;
        this.axis = axis;
        this.box = new DisplayBox(container);
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return box.getPosition();
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return Util.IDENTITY;
    }

    @Override
    public @NotNull Axis getAxis() {
        return axis;
    }

    @Override
    public double start() {
        box.saveOriginal();
        initialValue = axis.getValue(box.getOriginalPosition());
        return initialValue;
    }

    @Override
    public void set(double value) {
        box.setPositionDelta(axis.getDirection().mul(value - initialValue, new Vector3d()));
    }

    @Override
    public void revert() {
        box.restoreOriginal();
    }

    @Override
    public void commit() {
        container.commit();
    }

    @Override
    public boolean isValid() {
        return container.isValid();
    }
}
