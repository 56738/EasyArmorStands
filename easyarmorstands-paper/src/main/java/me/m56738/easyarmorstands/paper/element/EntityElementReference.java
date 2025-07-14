package me.m56738.easyarmorstands.paper.element;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementReference;
import me.m56738.easyarmorstands.paper.api.element.EntityElementType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public class EntityElementReference<E extends Entity> implements ElementReference {
    private final EntityElementType<E> type;
    private final Location location;
    private UUID id;

    public EntityElementReference(EntityElementType<E> type, Entity entity) {
        this.type = type;
        this.location = entity.getLocation();
        this.id = entity.getUniqueId();
    }

    @Override
    public EntityElementType<E> getType() {
        return type;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public @Nullable Element getElement() {
        location.getChunk(); // load chunk at expected location
        Entity entity = Bukkit.getEntity(id);
        if (entity == null) {
            return null;
        }
        if (!type.getEntityClass().isInstance(entity)) {
            return null;
        }
        return type.getElement(type.getEntityClass().cast(entity));
    }

    @Override
    public void onEntityReplaced(UUID oldId, UUID newId) {
        if (id.equals(oldId)) {
            id = newId;
        }
    }
}
