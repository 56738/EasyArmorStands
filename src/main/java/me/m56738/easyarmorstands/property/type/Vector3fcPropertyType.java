package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class Vector3fcPropertyType extends ConfigurablePropertyType<Vector3fc> {
    public Vector3fcPropertyType(String key) {
        super(key);
    }

    @Override
    public Component getValueComponent(Vector3fc value) {
        return Util.formatOffset(new Vector3d(value));
    }

    @Override
    public Vector3fc cloneValue(Vector3fc value) {
        return new Vector3f(value);
    }
}
