package me.m56738.easyarmorstands.display.element;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.BoundingBoxButton;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.display.editor.DisplayOffsetProvider;
import me.m56738.easyarmorstands.display.editor.DisplayRotationProvider;
import me.m56738.easyarmorstands.display.editor.node.DisplayRootNode;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.element.SimpleEntityElement;
import me.m56738.easyarmorstands.element.SimpleEntityElementType;
import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.Display;
import org.jetbrains.annotations.NotNull;

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
    public @NotNull Node createNode(@NotNull Session session) {
        return new DisplayRootNode(session, this);
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
