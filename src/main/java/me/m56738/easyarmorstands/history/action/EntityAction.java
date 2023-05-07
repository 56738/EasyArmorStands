package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.Entity;

import java.lang.ref.WeakReference;
import java.util.UUID;

public abstract class EntityAction<T extends Entity> implements Action {
    private final Class<T> type;
    private UUID uuid;
    private WeakReference<T> cache;

    protected EntityAction(Class<T> type, UUID uuid) {
        this.type = type;
        this.uuid = uuid;
    }

    @SuppressWarnings("unchecked")
    protected EntityAction(T entity) {
        this((Class<T>) entity.getClass(), entity.getUniqueId());
        this.cache = new WeakReference<>(entity);
    }

    public Class<T> getEntityType() {
        return type;
    }

    public UUID getEntityId() {
        return uuid;
    }

    public T findEntity() {
        if (cache != null) {
            T entity = cache.get();
            if (entity != null && entity.isValid()) {
                return entity;
            }
        }

        T entity = Util.getEntity(uuid, type);
        if (entity == null) {
            throw new IllegalStateException("Entity not found");
        }

        cache = new WeakReference<>(entity);
        return entity;
    }

    @Override
    public void onEntityReplaced(UUID oldId, UUID newId) {
        if (uuid.equals(oldId)) {
            uuid = newId;
        }
    }
}
