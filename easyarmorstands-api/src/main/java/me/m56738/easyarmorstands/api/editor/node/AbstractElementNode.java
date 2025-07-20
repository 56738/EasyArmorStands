package me.m56738.easyarmorstands.api.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

@NullMarked
public abstract class AbstractElementNode<E extends Element> extends AbstractChangeContextNode implements ElementNode {
    private final E element;
    private final PropertyContainer properties;

    public AbstractElementNode(Session session, E element) {
        super(session);
        this.element = element;
        this.properties = getContext().getProperties(element);
    }

    @Override
    public final @NonNull E getElement() {
        return element;
    }

    public final PropertyContainer getProperties() {
        return properties;
    }

    @Override
    public final boolean isValid() {
        return element.isValid();
    }
}
