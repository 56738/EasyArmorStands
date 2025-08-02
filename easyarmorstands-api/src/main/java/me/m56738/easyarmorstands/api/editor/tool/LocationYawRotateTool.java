package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.util.EasFormat;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

class LocationYawRotateTool implements AxisRotateTool {
    private final ToolContext context;
    private final ChangeContext changeContext;
    private final Property<Location> locationProperty;

    LocationYawRotateTool(ToolContext context, ChangeContext changeContext, Property<Location> locationProperty) {
        this.context = context;
        this.changeContext = changeContext;
        this.locationProperty = locationProperty;
    }

    @Override
    public @NotNull Vector3dc position() {
        return context.position().position();
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return context.rotation().getRotation();
    }

    @Override
    public @NotNull Axis getAxis() {
        return Axis.Y;
    }

    @Override
    public @NotNull AxisRotateToolSession start() {
        return new SessionImpl();
    }

    @Override
    public boolean canUse(@NotNull Player player) {
        return locationProperty.canChange(player);
    }

    private class SessionImpl implements AxisRotateToolSession {
        private final Location originalLocation;
        private final Vector3dc originalOffset;
        private final Vector3d offsetChange = new Vector3d();

        public SessionImpl() {
            this.originalLocation = locationProperty.getValue();
            this.originalOffset = originalLocation.position().sub(position(), new Vector3d());
        }

        @Override
        public void setChange(double change) {
            originalOffset.rotateY(change, offsetChange).sub(originalOffset);

            Location location = originalLocation
                    .withOffset(offsetChange)
                    .withYaw(originalLocation.yaw() - (float) Math.toDegrees(change));
            locationProperty.setValue(location);
        }

        @Override
        public double snapChange(double change, @NotNull Snapper context) {
            double original = Math.toRadians(originalLocation.yaw());
            change -= original;
            change = context.snapAngle(change);
            change += original;
            return change;
        }

        @Override
        public void setValue(double value) {
            setChange(Math.toRadians(originalLocation.yaw()) - value);
        }

        @Override
        public void revert() {
            locationProperty.setValue(originalLocation);
        }

        @Override
        public void commit(@Nullable Component description) {
            changeContext.commit(description);
        }

        @Override
        public boolean isValid() {
            return locationProperty.isValid();
        }

        @Override
        public @Nullable Component getStatus() {
            return EasFormat.formatDegrees(locationProperty.getValue().yaw());
        }

        @Override
        public @Nullable Component getDescription() {
            return Component.translatable("easyarmorstands.history.rotate-yaw");
        }
    }
}
