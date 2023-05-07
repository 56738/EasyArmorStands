package me.m56738.easyarmorstands.property.v1_19_4.display;

import cloud.commandframework.arguments.CommandArgument;
import me.m56738.easyarmorstands.command.EasCommandSender;
import me.m56738.easyarmorstands.property.EntityProperty;
import me.m56738.easyarmorstands.util.Util;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3d;
import org.joml.Vector3d;

public class DisplayTransformationProperty implements EntityProperty<Display, Transformation> {
    private final JOMLMapper mapper;

    public DisplayTransformationProperty(JOMLMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Transformation getValue(Display entity) {
        return entity.getTransformation();
    }

    @Override
    public void setValue(Display entity, Transformation value) {
        entity.setTransformation(value);
    }

    @Override
    public @NotNull String getName() {
        return "transformation";
    }

    @Override
    public @NotNull Class<Display> getEntityType() {
        return Display.class;
    }

    @Override
    public @Nullable CommandArgument<EasCommandSender, Transformation> getArgument() {
        return null;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("transformation");
    }

    @Override
    public @NotNull Component getValueName(Transformation value) {
        return Component.text()
                .append(Component.text("Rot: "))
                .append(Util.formatAngle(Util.toEuler(mapper.getLeftRotation(value).get(new Matrix3d()))))
                .append(Component.text(", "))
                .append(Component.text("Pos: "))
                .append(Util.formatOffset(new Vector3d(mapper.getTranslation(value))))
                .append(Component.text(", "))
                .append(Component.text("Rot: "))
                .append(Util.formatAngle(Util.toEuler(mapper.getRightRotation(value).get(new Matrix3d()))))
                .append(Component.text(", "))
                .append(Component.text("Scale: "))
                .append(Util.formatOffset(new Vector3d(mapper.getScale(value))))
                .build();
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.transformation";
    }
}
