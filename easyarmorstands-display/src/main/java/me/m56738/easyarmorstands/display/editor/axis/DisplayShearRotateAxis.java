package me.m56738.easyarmorstands.display.editor.axis;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Quaternionfc;

public class DisplayShearRotateAxis extends DisplayLocalRotateAxis {
    private final Property<Quaternionfc> shearingProperty;

    public DisplayShearRotateAxis(PropertyContainer properties, Axis axis) {
        super(properties, axis);
        this.shearingProperty = properties.get(DisplayPropertyTypes.RIGHT_ROTATION);
    }

    @Override
    public @NotNull Quaterniond getRotation() {
        return super.getRotation()
                .mul(new Quaterniond(shearingProperty.getValue()));
    }

    @Override
    protected Quaternionfc get() {
        return shearingProperty.getValue();
    }

    @Override
    protected void set(Quaternionfc value) {
        shearingProperty.setValue(value);
    }
}
