package me.m56738.easyarmorstands.property.v1_19_4.display;

import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.property.ResettableEntityProperty;
import me.m56738.easyarmorstands.util.Util;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class DisplayScaleProperty implements ResettableEntityProperty<Display, Vector3fc> {
    private final JOMLMapper mapper;

    public DisplayScaleProperty(JOMLMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Vector3fc getValue(Display entity) {
        return mapper.getScale(entity.getTransformation());
    }

    @Override
    public TypeToken<Vector3fc> getValueType() {
        return TypeToken.get(Vector3fc.class);
    }

    @Override
    public void setValue(Display entity, Vector3fc value) {
        Transformation transformation = entity.getTransformation();
        entity.setTransformation(mapper.getTransformation(
                mapper.getTranslation(transformation),
                mapper.getLeftRotation(transformation),
                value,
                mapper.getRightRotation(transformation)));
    }

    @Override
    public @NotNull String getName() {
        return "scale";
    }

    @Override
    public @NotNull Class<Display> getEntityType() {
        return Display.class;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("scale");
    }

    @Override
    public @NotNull Component getValueName(Vector3fc value) {
        return Util.formatOffset(new Vector3d(value));
    }

    @Override
    public @NotNull String getValueClipboardContent(Vector3fc value) {
        return value.x() + " " + value.y() + " " + value.z();
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.display.scale";
    }

    @Override
    public Vector3fc getResetValue() {
        return new Vector3f(1);
    }
}
