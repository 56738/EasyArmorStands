package me.m56738.easyarmorstands.display.editor.axis;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.axis.MoveAxis;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.display.DisplayBox;
import me.m56738.easyarmorstands.util.Util;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class DisplayBoxResizeAxis implements MoveAxis {
    private final PropertyContainer container;
    private final Axis axis;
    private final boolean end;
    private final DisplayBox box;

    public DisplayBoxResizeAxis(PropertyContainer container, Axis axis, boolean end) {
        this.container = container;
        this.axis = axis;
        this.end = end;
        this.box = new DisplayBox(container);
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return box.getSide(axis, end);
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
        return box.getSize(axis);
    }

    @Override
    public void set(double value) {
        box.setSize(axis, end, (float) value);
    }

    @Override
    public double getMinValue() {
        return 0;
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

    @Override
    public boolean isInverted() {
        return !end;
    }
}
