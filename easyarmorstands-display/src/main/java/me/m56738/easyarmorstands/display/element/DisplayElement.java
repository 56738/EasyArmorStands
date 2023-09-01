package me.m56738.easyarmorstands.display.element;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.display.editor.button.DisplayButton;
import me.m56738.easyarmorstands.display.editor.node.DisplayRootNode;
import me.m56738.easyarmorstands.element.SimpleEntityElement;
import me.m56738.easyarmorstands.element.SimpleEntityElementType;
import org.bukkit.entity.Display;
import org.jetbrains.annotations.NotNull;

public class DisplayElement<T extends Display> extends SimpleEntityElement<T> {
    public DisplayElement(T entity, SimpleEntityElementType<T> type) {
        super(entity, type);
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
}
