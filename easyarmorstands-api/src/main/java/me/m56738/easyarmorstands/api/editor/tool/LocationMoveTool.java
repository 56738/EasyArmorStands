package me.m56738.easyarmorstands.api.editor.tool;

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

class LocationMoveTool implements MoveTool {
    private final ToolContext context;
    private final ChangeContext changeContext;
    private final Property<Location> locationProperty;

    LocationMoveTool(ToolContext context, ChangeContext changeContext, Property<Location> locationProperty) {
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
    public @NotNull MoveToolSession start() {
        return new SessionImpl();
    }

    @Override
    public boolean canUse(@NotNull Player player) {
        return locationProperty.canChange(player);
    }

    private class SessionImpl implements MoveToolSession {
        private final Location originalLocation;
        private final Vector3d offset = new Vector3d();

        public SessionImpl() {
            this.originalLocation = locationProperty.getValue();
        }

        @Override
        public void setChange(@NotNull Vector3dc change) {
            this.offset.set(change);
            locationProperty.setValue(originalLocation.withOffset(change));
        }

        @Override
        public void snapChange(@NotNull Vector3d change, @NotNull Snapper context) {
            change.add(originalLocation.position());
            context.snapPosition(change);
            change.sub(originalLocation.position());
        }

        @Override
        public @NotNull Vector3dc getPosition() {
            return locationProperty.getValue().position();
        }

        @Override
        public void setPosition(@NotNull Vector3dc position) {
            setChange(position.sub(originalLocation.position(), offset));
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
