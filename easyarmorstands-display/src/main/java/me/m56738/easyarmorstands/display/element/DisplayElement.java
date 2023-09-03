package me.m56738.easyarmorstands.display.element;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.display.editor.button.DisplayButton;
import me.m56738.easyarmorstands.display.editor.node.DisplayRootNode;
import me.m56738.easyarmorstands.element.SimpleEntityElement;
import me.m56738.easyarmorstands.element.SimpleEntityElementType;
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
    public Button createButton(Session session) {
        return new DisplayButton(session, getProperties());
    }

    @Override
    public ElementNode createNode(Session session) {
        return new DisplayRootNode(session, this);
    }

    @Override
    public @NotNull DisplayToolProvider getTools(PropertyContainer properties) {
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
