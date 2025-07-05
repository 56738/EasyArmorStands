package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.lib.joml.Vector3f;
import me.m56738.easyarmorstands.lib.joml.Vector3fc;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.util.Util;
import org.jetbrains.annotations.NotNull;

public class Vector3fcPropertyType extends ConfigurablePropertyType<Vector3fc> {
    public Vector3fcPropertyType(@NotNull Key key) {
        super(key, Vector3fc.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull Vector3fc value) {
        return Util.formatOffset(new Vector3d(value));
    }

    @Override
    public @NotNull Vector3fc cloneValue(@NotNull Vector3fc value) {
        return new Vector3f(value);
    }
}
