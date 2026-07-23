package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.element.EntityElement;
import me.m56738.easyarmorstands.api.element.EntityElementReference;
import me.m56738.easyarmorstands.api.element.EntityElementType;
import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.util.Location;
import me.m56738.easyarmorstands.platform.world.World;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

import java.util.UUID;

public class EntityElementReferenceImpl<E extends Entity> implements EntityElementReference<E> {
    private final Platform platform;
    private final EntityElementType<E> type;
    private final Key worldKey;
    private final Vector3dc position;
    private UUID id;

    public EntityElementReferenceImpl(Platform platform, EntityElementType<E> type, Entity entity) {
        Location location = entity.location();
        this.platform = platform;
        this.type = type;
        this.worldKey = location.world().key();
        this.position = location.position();
        this.id = entity.uniqueId();
    }

    @Override
    public @NotNull EntityElementType<E> getType() {
        return type;
    }

    @Override
    public @Nullable EntityElement<E> getElement() {
        // Load chunk at the expected position
        World world = platform.getWorld(worldKey);
        if (world != null) {
            world.getChunkAt(Location.of(world, position));
        }

        Entity entity = platform.getEntity(id);
        if (entity == null) {
            return null;
        }

        if (!entity.type().equals(type.getEntityType())) {
            return null;
        }

        return type.getElement(type.getEntityClass().cast(entity));
    }

    @Override
    public void onEntityReplaced(@NotNull UUID oldId, @NotNull UUID newId) {
        if (id.equals(oldId)) {
            id = newId;
        }
    }

    @Override
    public @NotNull UUID getId() {
        return id;
    }
}
