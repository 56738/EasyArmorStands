package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;

public class PropertyMenuNode extends MenuNode {
    private final PropertyContainer container;

    public PropertyMenuNode(Session session, Component name, PropertyContainer container) {
        super(session, name);
        this.container = container;
    }

    @Override
    public boolean isValid() {
        return container.isValid();
    }

    public PropertyContainer properties() {
        return container;
    }
}
