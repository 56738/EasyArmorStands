package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.EasConversion;
import me.m56738.easyarmorstands.api.util.EasFormat;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

class LocationYawRotateTool implements AxisRotateTool {
    private final ToolContext context;
    private final PropertyContainer properties;
    private final Property<Location> locationProperty;

    LocationYawRotateTool(ToolContext context, PropertyContainer properties, Property<Location> locationProperty) {
        this.context = context;
        this.properties = properties;
        this.locationProperty = locationProperty;
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
            this.originalLocation = locationProperty.getValue().clone();
            this.originalOffset = EasConversion.fromBukkit(originalLocation.toVector()).sub(getPosition());
        }

        @Override
        public void setChange(double change) {
            originalOffset.rotateY(change, offsetChange).sub(originalOffset);

            Location location = originalLocation.clone();
            location.add(offsetChange.x(), offsetChange.y(), offsetChange.z());
            location.setYaw(location.getYaw() - (float) Math.toDegrees(change));
            locationProperty.setValue(location);
        }

        @Override
        public double snapChange(double change, @NotNull Snapper context) {
            double original = Math.toRadians(originalLocation.getYaw());
            change -= original;
            change = context.snapAngle(change);
            change += original;
            return change;
        }

        @Override
        public void setValue(double value) {
            setChange(Math.toRadians(originalLocation.getYaw()) - value);
        }

        @Override
        public void revert() {
            locationProperty.setValue(originalLocation);
        }

        @Override
        public void commit(@Nullable Component description) {
            properties.commit(description);
        }

        @Override
        public boolean isValid() {
            return properties.isValid();
        }

        @Override
        public @Nullable Component getStatus() {
            return EasFormat.formatDegrees(locationProperty.getValue().getYaw());
        }

        @Override
        public @Nullable Component getDescription() {
            return Component.translatable("easyarmorstands.history.rotate-yaw");
        }
    }
}
