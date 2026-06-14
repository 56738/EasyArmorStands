package me.m56738.easyarmorstands.editor.display.tool;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.tool.OrientedTool;
import me.m56738.easyarmorstands.api.editor.tool.PositionedTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolSession;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.tool.AbstractToolSession;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public abstract class AbstractDisplayScaleTool<S extends ToolSession> implements PositionedTool<S>, OrientedTool<S> {
    private final ToolContext context;
    private final PropertyContainer properties;
    private final Property<Location> locationProperty;
    private final Property<Vector3fc> translationProperty;
    private final Property<Vector3fc> scaleProperty;
    private final Property<Float> heightProperty;

    public AbstractDisplayScaleTool(ToolContext context, PropertyContainer properties) {
        this.context = context;
        this.properties = properties;
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.translationProperty = properties.getOrNull(DisplayPropertyTypes.TRANSLATION);
        this.scaleProperty = properties.get(DisplayPropertyTypes.SCALE);
        this.heightProperty = properties.getOrNull(DisplayPropertyTypes.BOX_HEIGHT);
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
    public boolean canUse(@NotNull Player player) {
        return scaleProperty.canChange(player);
    }

    protected PropertyContainer getProperties() {
        return properties;
    }

    public abstract class AbstractDisplayScaleToolSession extends AbstractToolSession implements ToolSession {
        private final Location originalLocation;
        private final Vector3fc originalTranslation;
        private final Vector3f currentTranslation;
        private final Vector3f originalTranslationOffset;
        private final Quaternionfc originalRotation;
        private final Vector3fc originalScale;
        private final Vector3f currentScale;
        private final Vector3dc originalOffset;
        private final Vector3d offsetChange;
        private final Vector3f currentDelta;

        public AbstractDisplayScaleToolSession(PropertyContainer properties) {
            super(properties);
            float height = heightProperty != null ? heightProperty.getValue() : 0;
            this.originalLocation = locationProperty.getValue().clone();
            this.originalTranslation = translationProperty != null ? new Vector3f(translationProperty.getValue()) : new Vector3f();
            this.currentTranslation = new Vector3f(originalTranslation);
            this.originalTranslationOffset = new Vector3f(originalTranslation)
                    .sub(0, height / 2, 0);
            this.originalRotation = new Quaternionf(getRotation());
            this.originalScale = new Vector3f(scaleProperty.getValue());
            this.currentScale = new Vector3f(originalScale);
            this.originalOffset = Util.toVector3d(originalLocation)
                    .add(0, height / 2, 0)
                    .sub(getPosition());
            this.offsetChange = new Vector3d();
            this.currentDelta = new Vector3f(1);
        }

        public Vector3fc getOriginalScale() {
            return originalScale;
        }

        public Vector3fc getScale() {
            return scaleProperty.getValue();
        }

        public static boolean isCloseToZero(float value) {
            return Math.abs(value) < 1e-6;
        }

        private Vector3fc getDelta() {
            currentDelta.x = getDelta(currentScale.x(), originalScale.x());
            currentDelta.y = getDelta(currentScale.y(), originalScale.y());
            currentDelta.z = getDelta(currentScale.z(), originalScale.z());
            return currentDelta;
        }

        private float getDelta(float value, float original) {
            if (isCloseToZero(original)) {
                return 1;
            } else {
                return value / original;
            }
        }

        public void setScale(Vector3fc scale) {
            currentScale.set(scale);
            EasyArmorStandsPlugin.getInstance().getConfiguration().limits.displayEntity.clampScale(currentScale);
            scaleProperty.setValue(currentScale);

            Vector3fc delta = getDelta();

            offsetChange.set(originalOffset);
            originalRotation.transformInverse(offsetChange);
            offsetChange.mul(delta);
            originalRotation.transform(offsetChange);
            offsetChange.sub(originalOffset);

            Location location = originalLocation.clone();
            location.add(offsetChange.x, offsetChange.y, offsetChange.z);
            locationProperty.setValue(location);

            if (translationProperty != null) {
                offsetChange.set(originalTranslationOffset);
                originalRotation.transformInverse(offsetChange);
                offsetChange.mul(delta);
                originalRotation.transform(offsetChange);
                offsetChange.sub(originalTranslationOffset);

                originalTranslation.add(
                        (float) offsetChange.x,
                        (float) offsetChange.y,
                        (float) offsetChange.z,
                        currentTranslation);
                translationProperty.setValue(currentTranslation);
            }
        }

        @Override
        public void revert() {
            locationProperty.setValue(originalLocation);
            if (translationProperty != null) {
                translationProperty.setValue(originalTranslation);
            }
            scaleProperty.setValue(originalScale);
        }
    }
}
