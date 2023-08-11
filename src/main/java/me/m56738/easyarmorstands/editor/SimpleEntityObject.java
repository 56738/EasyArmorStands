package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.node.Button;
import me.m56738.easyarmorstands.node.EditableObjectNode;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

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
    public boolean hasItemSlots() {
        return entity instanceof LivingEntity;
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
        Component name = EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class).getName(entity.getType());
        return new SimpleEntityNode(session, name, this);
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
