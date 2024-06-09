package me.m56738.easyarmorstands.editor.armorstand.tool;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateToolSession;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.editor.tool.AbstractToolSession;
import me.m56738.easyarmorstands.util.EasMath;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandPoseTool implements AxisRotateTool {
    private final PropertyContainer properties;
    private final Property<Location> locationProperty;
    private final Property<EulerAngle> poseProperty;
    private final PositionProvider positionProvider;
    private final RotationProvider rotationProvider;
    private final Axis axis;

    public ArmorStandPoseTool(PropertyContainer properties, ArmorStandPart part, PositionProvider positionProvider, RotationProvider rotationProvider, Axis axis) {
        this.properties = properties;
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.poseProperty = properties.get(ArmorStandPropertyTypes.POSE.get(part));
        this.axis = axis;
        this.positionProvider = positionProvider;
        this.rotationProvider = rotationProvider;
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return positionProvider.getPosition();
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return rotationProvider.getRotation();
    }

    @Override
    public @NotNull Axis getAxis() {
        return axis;
    }

    @Override
    public @NotNull AxisRotateToolSession start() {
        return new SessionImpl();
    }

    @Override
    public boolean canUse(@NotNull Player player) {
        return poseProperty.canChange(player);
    }

    private class SessionImpl extends AbstractToolSession implements AxisRotateToolSession {
        private final Vector3dc direction;
        private final EulerAngle originalAngle;
        private final Quaterniondc originalRotation;
        private final Quaterniond currentRotation = new Quaterniond();
        private double change;

        public SessionImpl() {
            super(properties);
            originalAngle = poseProperty.getValue();
            originalRotation = Util.fromEuler(originalAngle, new Quaterniond());
            Location location = locationProperty.getValue();
            direction = axis.getDirection().rotate(
                    EasMath.getInverseEntityYawRotation(location.getYaw(), new Quaterniond())
                            .mul(getRotation()),
                    new Vector3d());
        }

        @Override
        public void setChange(double change) {
            this.change = change;
            currentRotation.setAngleAxis(change, direction).mul(originalRotation);
            poseProperty.setValue(Util.toEuler(currentRotation));
        }

        @Override
        public double snapChange(double change, @NotNull Snapper context) {
            return context.snapAngle(change);
        }

        @Override
        public void revert() {
            poseProperty.setValue(originalAngle);
        }

        @Override
        public @Nullable Component getStatus() {
            return Util.formatAngle(change);
        }

        @Override
        public @Nullable Component getDescription() {
            return null;
        }
    }
}
