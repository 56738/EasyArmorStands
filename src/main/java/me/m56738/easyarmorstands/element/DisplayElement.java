package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.BoundingBoxButton;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.editor.display.BlockDisplayOffsetProvider;
import me.m56738.easyarmorstands.editor.display.DisplayOffsetProvider;
import me.m56738.easyarmorstands.editor.display.DisplayRotationProvider;
import me.m56738.easyarmorstands.editor.display.layer.DisplayRootLayer;
import me.m56738.easyarmorstands.platform.entity.BlockDisplay;
import me.m56738.easyarmorstands.platform.entity.Display;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

public class DisplayElement<T extends Display> extends SimpleEntityElement<T> {
    private final T entity;

    public DisplayElement(EasyArmorStandsCommon eas, T entity, SimpleEntityElementType<T> type) {
        super(eas, entity, type);
        this.entity = entity;
    }

    @Override
    public @NotNull Button createButton(@NotNull Session session) {
        PropertyContainer properties = getProperties();
        return new BoundingBoxButton(session, this,
                new EntityPositionProvider(properties, getOffsetProvider(properties)),
                new DisplayRotationProvider(properties));
    }

    @Override
    public @NotNull Layer createLayer(@NotNull Session session) {
        return new DisplayRootLayer(eas, session, this);
    }

    @Override
    public @NotNull DisplayToolProvider getTools(@NotNull PropertyContainer properties) {
        return new DisplayToolProvider(eas, properties, getOffsetProvider(properties));
    }

    private OffsetProvider getOffsetProvider(PropertyContainer properties) {
        if (entity instanceof BlockDisplay && eas.getConfiguration().editor.centeredPivot) {
            return new BlockDisplayOffsetProvider(properties);
        } else {
            return new DisplayOffsetProvider(properties);
        }
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        Vector3dc position = entity.location().position();
        double width = entity.getDisplayWidth();
        double height = entity.getDisplayHeight();
        return BoundingBox.of(position, width, height);
    }
}
