package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.EasConversion;
import me.m56738.easyarmorstands.api.util.EasFormat;
import me.m56738.easyarmorstands.lib.joml.Quaterniondc;
import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class LocationMoveTool implements MoveTool {
    private final ToolContext context;
    private final PropertyContainer properties;
    private final Property<Location> locationProperty;

    LocationMoveTool(ToolContext context, PropertyContainer properties, Property<Location> locationProperty) {
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
    public @NotNull MoveToolSession start() {
        return new SessionImpl();
    }

    @Override
    public boolean canUse(@NotNull Player player) {
        return locationProperty.canChange(player);
    }

    private class SessionImpl implements MoveToolSession {
        private final Location originalLocation;
        private final Vector3dc originalPosition;
        private final Vector3d offset = new Vector3d();

        public SessionImpl() {
            this.originalLocation = locationProperty.getValue().clone();
            this.originalPosition = EasConversion.fromBukkit(originalLocation.toVector());
        }

        @Override
        public void setChange(@NotNull Vector3dc change) {
            this.offset.set(change);
            Location location = originalLocation.clone();
            location.add(change.x(), change.y(), change.z());
            locationProperty.setValue(location);
        }

        @Override
        public void snapChange(@NotNull Vector3d change, @NotNull Snapper context) {
            change.add(originalPosition);
            context.snapPosition(change);
            change.sub(originalPosition);
        }

        @Override
        public @NotNull Vector3dc getPosition() {
            return EasConversion.fromBukkit(locationProperty.getValue().toVector());
        }

        @Override
        public void setPosition(@NotNull Vector3dc position) {
            setChange(position.sub(originalPosition, offset));
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
            return EasFormat.formatLocation(locationProperty.getValue());
        }

        @Override
        public @Nullable Component getDescription() {
            Component value = EasFormat.formatLocation(locationProperty.getValue());
            return Component.translatable("easyarmorstands.history.move", value);
        }

        @Override
        public boolean canSetPosition(Player player) {
            return player.hasPermission("easyarmorstands.position");
        }
    }
}
