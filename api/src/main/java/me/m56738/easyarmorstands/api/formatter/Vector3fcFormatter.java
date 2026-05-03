package me.m56738.easyarmorstands.api.formatter;

import me.m56738.easyarmorstands.api.util.EasFormat;
import net.kyori.adventure.text.Component;
import org.joml.Vector3d;
import org.joml.Vector3fc;

public class Vector3fcFormatter implements ValueFormatter<Vector3fc> {
    @Override
    public Component format(Vector3fc value) {
        return EasFormat.formatPosition(new Vector3d(value));
    }

    @Override
    public String formatAsString(Vector3fc value) {
        return value.x() + "," + value.y() + "," + value.z();
    }
}
