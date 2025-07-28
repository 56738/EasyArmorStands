package me.m56738.easyarmorstands.display.editor.tool;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateToolSession;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.PendingChange;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class DisplayAxisRotateTool implements AxisRotateTool {
    private final ToolContext context;
    private final ChangeContext changeContext;
    private final Property<Location> locationProperty;
    private final Property<Vector3fc> translationProperty;
    private final Property<Quaternionfc> rotationProperty;
    private final Property<Float> heightProperty;
    private final Axis axis;
    private final RotationProvider parentRotationProvider;

    public DisplayAxisRotateTool(ToolContext context, ChangeContext changeContext, PropertyContainer properties, PropertyType<Quaternionfc> type, Axis axis, RotationProvider parentRotationProvider) {
        this.context = context;
        this.changeContext = changeContext;
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.translationProperty = properties.get(DisplayPropertyTypes.TRANSLATION);
        this.heightProperty = properties.get(DisplayPropertyTypes.BOX_HEIGHT);
        this.rotationProperty = properties.get(type);
        this.axis = axis;
        this.parentRotationProvider = parentRotationProvider;
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
        return rotationProperty.canChange(player) &&
                locationProperty.canChange(player) &&
                translationProperty.canChange(player);
    }

    private class SessionImpl implements AxisRotateToolSession {
        private final Location originalLocation;
        private final Vector3fc originalTranslation;
        private final Quaternionfc originalRotation;
        private final Vector3dc originalOffset;
        private final Vector3dc translationOffset;
        private final Vector3dc direction;
        private final Vector3dc localDirection;
        private final Quaternionf currentRotation = new Quaternionf();
        private final Vector3f currentTranslation = new Vector3f();
        private final Vector3d offsetChange = new Vector3d();
        private double change;

        public SessionImpl() {
            float height = heightProperty.getValue();
            Quaterniondc rotation = getRotation();
            Quaterniondc localRotation = parentRotationProvider.getRotation().conjugate(new Quaterniond())
                    .mul(rotation);
            this.originalLocation = locationProperty.getValue();
            this.originalTranslation = new Vector3f(translationProperty.getValue());
            this.originalRotation = new Quaternionf(rotationProperty.getValue());
            this.originalOffset = originalLocation.position()
                    .add(0, height / 2, 0, new Vector3d())
                    .sub(getPosition());
            this.translationOffset = new Vector3d(originalTranslation)
                    .sub(0, height / 2, 0);
            this.direction = axis.getDirection().rotate(rotation, new Vector3d());
            this.localDirection = axis.getDirection().rotate(localRotation, new Vector3d());
        }

        @Override
        public void setChange(double change) {
            this.change = change;

            originalOffset.rotateAxis(change,
                            direction.x(),
                            direction.y(),
                            direction.z(), offsetChange)
                    .sub(originalOffset);

            Location location = originalLocation.withOffset(offsetChange);

            translationOffset.rotateAxis(change,
                            direction.x(),
                            direction.y(),
                            direction.z(), offsetChange)
                    .sub(translationOffset);
            originalTranslation.add(offsetChange.get(new Vector3f()), currentTranslation);

            currentRotation.setAngleAxis(
                            change,
                            localDirection.x(),
                            localDirection.y(),
                            localDirection.z())
                    .mul(originalRotation);

            PendingChange locationChange = locationProperty.prepareChange(location);
            if (locationChange != null) {
                PendingChange translationChange = translationProperty.prepareChange(currentTranslation);
                if (translationChange != null) {
                    PendingChange rotationChange = rotationProperty.prepareChange(currentRotation);
                    if (rotationChange != null) {
                        if (locationChange.execute()) {
                            if (translationChange.execute()) {
                                rotationChange.execute();
                            }
                        }
                    }
                }
            }
        }

        @Override
        public double snapChange(double change, @NotNull Snapper context) {
            return context.snapAngle(change);
        }

        @Override
        public void revert() {
            locationProperty.setValue(originalLocation);
            translationProperty.setValue(originalTranslation);
            rotationProperty.setValue(originalRotation);
        }

        @Override
        public void commit(@Nullable Component description) {
            changeContext.commit(description);
        }

        @Override
        public boolean isValid() {
            return locationProperty.isValid() && translationProperty.isValid() && rotationProperty.isValid();
        }

        @Override
        public @Nullable Component getStatus() {
            return Util.formatAngle(change);
        }

        @Override
        public @Nullable Component getDescription() {
            Component axisName = Component.text(axis.getName(), TextColor.color(axis.getColor()));
            return Message.component("easyarmorstands.history.rotate-axis", axisName);
        }
    }
}
