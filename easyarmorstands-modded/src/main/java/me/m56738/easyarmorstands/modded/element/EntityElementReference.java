package me.m56738.easyarmorstands.modded.element;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementReference;
import me.m56738.easyarmorstands.modded.api.element.EntityElementType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public class EntityElementReference<E extends Entity> implements ElementReference {
    private final EntityElementType<E> type;
    private final Level level;
    private final ChunkPos chunkPos;
    private UUID id;

    public EntityElementReference(EntityElementType<E> type, Entity entity) {
        this.type = type;
        this.level = entity.level();
        this.chunkPos = entity.chunkPosition();
        this.id = entity.getUUID();
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
        level.getChunk(chunkPos.x, chunkPos.z);
        Entity entity = level.getEntity(id);
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
