package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.BoundingBoxButton;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.display.DisplayOffsetProvider;
import me.m56738.easyarmorstands.editor.display.DisplayRotationProvider;
import me.m56738.easyarmorstands.editor.display.layer.DisplayRootLayer;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.Display;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

public class DisplayElement<T extends Display> extends SimpleEntityElement<T> {
    private final T entity;

    public DisplayElement(T entity, SimpleEntityElementType<T> type) {
        super(entity, type);
        this.entity = entity;
    }

    @Override
    public @NotNull Button createButton(@NotNull Session session) {
        PropertyContainer properties = getProperties();
        return new BoundingBoxButton(session, this,
                new EntityPositionProvider(properties, new DisplayOffsetProvider(properties)),
                new DisplayRotationProvider(properties));
    }

    @Override
    public @NotNull Layer createLayer(@NotNull Session session) {
        return new DisplayRootLayer(session, this);
    }

    @Override
    public @NotNull DisplayToolProvider getTools(@NotNull PropertyContainer properties) {
        return new DisplayToolProvider(properties);
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        Vector3d position = Util.toVector3d(entity.getLocation());
        double width = entity.getDisplayWidth();
        double height = entity.getDisplayHeight();
        return BoundingBox.of(position, width, height);
    }
}
