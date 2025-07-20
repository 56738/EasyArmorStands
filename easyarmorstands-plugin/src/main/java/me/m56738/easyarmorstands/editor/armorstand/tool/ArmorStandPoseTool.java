package me.m56738.easyarmorstands.editor.armorstand.tool;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateToolSession;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.util.EasMath;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandPoseTool implements AxisRotateTool {
    private final ToolContext context;
    private final ChangeContext changeContext;
    private final Property<Location> locationProperty;
    private final Property<EulerAngle> poseProperty;
    private final Axis axis;

    public ArmorStandPoseTool(ToolContext context, ChangeContext changeContext, Property<Location> locationProperty, Property<EulerAngle> poseProperty, Axis axis) {
        this.context = context;
        this.changeContext = changeContext;
        this.locationProperty = locationProperty;
        this.poseProperty = poseProperty;
        this.axis = axis;
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return context.position().getPosition();
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return context.rotation().getRotation();
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

    private class SessionImpl implements AxisRotateToolSession {
        private final Vector3dc direction;
        private final EulerAngle originalAngle;
        private final Quaterniondc originalRotation;
        private final Quaterniond currentRotation = new Quaterniond();
        private double change;

        public SessionImpl() {
            originalAngle = poseProperty.getValue();
            originalRotation = Util.fromEuler(originalAngle, new Quaterniond());
            Location location = locationProperty.getValue();
            direction = axis.getDirection().rotate(
                    EasMath.getInverseEntityYawRotation(location.yaw(), new Quaterniond())
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
        public void commit(@Nullable Component description) {
            changeContext.commit(description);
        }

        @Override
        public boolean isValid() {
            return poseProperty.isValid();
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
