package me.m56738.easyarmorstands.editor.box;

import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractBoundingBoxEditorSession implements BoundingBoxEditorSession {
    private final PropertyContainer properties;

    protected AbstractBoundingBoxEditorSession(PropertyContainer properties) {
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
