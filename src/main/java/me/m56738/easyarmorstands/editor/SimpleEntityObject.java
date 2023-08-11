package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.node.Button;
import me.m56738.easyarmorstands.node.EditableObjectNode;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.Entity;

public class SimpleEntityObject extends AbstractEditableObject implements EntityObject {
    private final Session session;
    private final Entity entity;

    public SimpleEntityObject(Session session, Entity entity) {
        this.session = session;
        this.entity = entity;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public EditableObjectReference asReference() {
        return new EntityObjectReference(entity.getUniqueId());
    }

    @Override
    public Button createButton() {
        return new SimpleEntityButton(session, entity);
    }

    @Override
    public EditableObjectNode createNode() {
        return new SimpleEntityNode(session, this, entity);
    }
}
