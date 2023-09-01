package me.m56738.easyarmorstands.editor.tool;

import me.m56738.easyarmorstands.api.editor.tool.ToolSession;
import me.m56738.easyarmorstands.api.property.PropertyContainer;

public abstract class AbstractToolSession implements ToolSession {
    private final PropertyContainer properties;

    public AbstractToolSession(PropertyContainer properties) {
        this.properties = properties;
    }

    @Override
    public void commit() {
        properties.commit();
    }

    @Override
    public boolean isValid() {
        return properties.isValid();
    }
}
