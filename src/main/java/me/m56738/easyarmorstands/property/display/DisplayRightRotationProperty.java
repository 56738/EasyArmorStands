package me.m56738.easyarmorstands.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.platform.entity.Display;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionfc;

public class DisplayRightRotationProperty implements Property<Quaternionfc> {
    private final Display entity;

    public DisplayRightRotationProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Quaternionfc> getType() {
        return DisplayPropertyTypes.RIGHT_ROTATION;
    }

    @Override
    public @NotNull Quaternionfc getValue() {
        return entity.getRightRotation();
    }

    @Override
    public boolean setValue(@NotNull Quaternionfc value) {
        entity.setRightRotation(value);
        return true;
    }
}
