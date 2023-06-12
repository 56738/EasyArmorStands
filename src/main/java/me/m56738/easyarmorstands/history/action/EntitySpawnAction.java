package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.session.CloneSpawner;
import me.m56738.easyarmorstands.session.EntitySpawner;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class EntitySpawnAction<E extends Entity> implements Action {
    private Location location;
    private EntitySpawner<E> spawner;
    private UUID uuid;

    public EntitySpawnAction(E entity) {
        this(entity.getLocation(), new CloneSpawner<>(entity), entity.getUniqueId());
    }

    public EntitySpawnAction(Location location, EntitySpawner<E> spawner, UUID uuid) {
        this.location = location;
        this.spawner = spawner;
        this.uuid = uuid;
    }

    @Override
    public void execute() {
        E entity = spawner.spawn(location);
        if (uuid != null) {
            EasyArmorStands.getInstance().getHistoryManager().onEntityReplaced(uuid, entity.getUniqueId());
        } else {
            uuid = entity.getUniqueId();
        }
    }

    @Override
    public void undo() {
        E entity = findEntity();
        if (entity == null) {
            throw new IllegalStateException();
        }
        location = entity.getLocation();
        spawner = new CloneSpawner<>(entity);
        entity.remove();
    }

    @Override
    public Component describe() {
        EntityTypeCapability entityTypeCapability = EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class);
        return Component.text("Spawned ").append(entityTypeCapability.getName(getEntityType()));
    }

    @Override
    public void onEntityReplaced(UUID oldId, UUID newId) {
        if (uuid.equals(oldId)) {
            uuid = newId;
        }
    }

    public EntityType getEntityType() {
        return spawner.getEntityType();
    }

    public Class<E> getType() {
        return spawner.getType();
    }

    public UUID getEntityId() {
        return uuid;
    }

    public @Nullable E findEntity() {
        return Util.getEntity(uuid, getType());
    }
}
