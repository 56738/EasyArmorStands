package me.m56738.easyarmorstands.editor.tool;

import me.m56738.easyarmorstands.api.editor.tool.ToolSession;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractToolSession implements ToolSession {
    private final PropertyContainer properties;

    public AbstractToolSession(PropertyContainer properties) {
        this.properties = properties;
    }

    @Override
    public void commit(@Nullable Component description) {
        properties.commit(description);
    }

    @Override
    public boolean isValid() {
        return properties.isValid();
    }
}
