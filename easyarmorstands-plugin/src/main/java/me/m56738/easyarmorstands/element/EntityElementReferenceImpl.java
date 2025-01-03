package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.EntityElement;
import me.m56738.easyarmorstands.api.element.EntityElementReference;
import me.m56738.easyarmorstands.api.element.EntityElementType;
import me.m56738.easyarmorstands.capability.lookup.LookupCapability;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class EntityElementReferenceImpl<E extends Entity> implements EntityElementReference<E> {
    private final EntityElementType<E> type;
    private final UUID worldId;
    private final Vector position;
    private UUID id;

    public EntityElementReferenceImpl(EntityElementType<E> type, Entity entity) {
        this.type = type;
        this.worldId = entity.getWorld().getUID();
        this.position = entity.getLocation().toVector();
        this.id = entity.getUniqueId();
    }

    @Override
    public @NotNull EntityElementType<E> getType() {
        return type;
    }

    @Override
    public @Nullable EntityElement<E> getElement() {
        // Load chunk at the expected position
        World world = Bukkit.getWorld(worldId);
        Chunk chunk = null;
        if (world != null) {
            chunk = world.getChunkAt(position.toLocation(world));
        }

        Entity entity = EasyArmorStandsPlugin.getInstance().getCapability(LookupCapability.class).getEntity(id, chunk);
        if (entity == null) {
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
