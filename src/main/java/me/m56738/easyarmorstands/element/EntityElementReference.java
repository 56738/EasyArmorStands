package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.lookup.LookupCapability;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class EntityElementReference<E extends Entity> implements ElementReference {
    private final EntityElementType<E> type;
    private UUID id;

    public EntityElementReference(EntityElementType<E> type, UUID id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public EntityElementType<E> getType() {
        return type;
    }

    @Override
    public EntityElement<E> getElement() {
        Entity entity = EasyArmorStands.getInstance().getCapability(LookupCapability.class).getEntity(id);
        if (entity == null) {
            return null;
        }
        return type.getElement(type.getEntityType().cast(entity));
    }

    @Override
    public void onEntityReplaced(UUID oldId, UUID newId) {
        if (id.equals(oldId)) {
            id = newId;
        }
    }

    public UUID getId() {
        return id;
    }
}
