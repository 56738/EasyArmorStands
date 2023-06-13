package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class EntityPropertyReference<T> implements PropertyReference<T> {
    private final UUID uuid;
    private final EntityPropertyType<T> type;

    public EntityPropertyReference(UUID uuid, EntityPropertyType<T> type) {
        this.uuid = uuid;
        this.type = type;
    }

    @Override
    public Property<T> restore() {
        Entity entity = Util.getEntity(uuid, Entity.class);
        if (entity == null) {
            return null;
        }
        return type.bind(entity);
    }

    @Override
    public PropertyReference<T> replaceEntity(UUID oldId, UUID newId) {
        if (uuid.equals(oldId)) {
            return new EntityPropertyReference<>(newId, type);
        }
        return this;
    }
}
