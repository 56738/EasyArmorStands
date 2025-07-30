package me.m56738.easyarmorstands.common.element;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementReference;
import me.m56738.easyarmorstands.api.element.EntityElementType;
import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.world.Location;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public class EntityElementReference implements ElementReference {
    private final Platform platform;
    private final EntityElementType type;
    private final Location location;
    private UUID id;

    public EntityElementReference(Platform platform, EntityElementType type, Entity entity) {
        this.platform = platform;
        this.type = type;
        this.location = entity.getLocation();
        this.id = entity.getUniqueId();
    }

    @Override
    public EntityElementType getType() {
        return type;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public @Nullable Element getElement() {
        Entity entity = platform.getEntity(id, location);
        if (entity == null || !entity.getType().equals(type.getEntityType())) {
            return null;
        }
        return type.getElement(entity);
    }

    @Override
    public void onEntityReplaced(UUID oldId, UUID newId) {
        if (id.equals(oldId)) {
            id = newId;
        }
    }
}
