package me.m56738.easyarmorstands.editor.layer;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.layer.MenuLayer;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PropertyMenuLayer extends MenuLayer {
    private final PropertyContainer properties;

    public PropertyMenuLayer(Session session, PropertyContainer properties) {
        super(session);
        this.properties = properties;
    }

    @Override
    public boolean isValid() {
        return properties.isValid();
    }

    @Contract(pure = true)
    public @NotNull PropertyContainer properties() {
        return properties;
    }
}
