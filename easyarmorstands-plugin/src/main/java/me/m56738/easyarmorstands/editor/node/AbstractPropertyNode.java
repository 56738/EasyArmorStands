package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.AbstractNode;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractPropertyNode extends AbstractNode {
    private final PropertyContainer properties;

    public AbstractPropertyNode(Session session, PropertyContainer properties) {
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
