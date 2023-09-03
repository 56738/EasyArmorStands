package me.m56738.easyarmorstands.editor.tool;

import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.MoveToolSession;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class EntityMoveTool implements MoveTool {
    private final PropertyContainer properties;
    private final Property<Location> locationProperty;
    private final PositionProvider positionProvider;
    private final RotationProvider rotationProvider;

    public EntityMoveTool(PropertyContainer properties, PositionProvider positionProvider, RotationProvider rotationProvider) {
        this.properties = properties;
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
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
    public @NotNull MoveToolSession start() {
        return new SessionImpl();
    }

    private class SessionImpl extends AbstractToolSession implements MoveToolSession {
        private final Location originalLocation;
        private final Vector3d offset = new Vector3d();

        public SessionImpl() {
            super(properties);
            this.originalLocation = locationProperty.getValue().clone();
        }

        @Override
        public void setOffset(@NotNull Vector3dc offset) {
            this.offset.set(offset);
            Location location = originalLocation.clone();
            location.add(offset.x(), offset.y(), offset.z());
            locationProperty.setValue(location);
        }

        @Override
        public @NotNull Vector3dc getPosition() {
            return Util.toVector3d(locationProperty.getValue());
        }

        @Override
        public void setPosition(@NotNull Vector3dc position) {
            setOffset(position.sub(originalLocation.getX(), originalLocation.getY(), originalLocation.getZ(), offset));
        }

        @Override
        public void revert() {
            locationProperty.setValue(originalLocation);
        }

        @Override
        public @Nullable Component getStatus() {
            return Util.formatOffset(offset);
        }

        @Override
        public @Nullable Component getDescription() {
            Component value = Util.formatOffset(offset);
            return Message.component("easyarmorstands.history.move", value);
        }
    }
}
