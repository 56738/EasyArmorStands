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
import org.bukkit.entity.Player;
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
    public boolean execute(Player player) {
        E entity = EntitySpawner.trySpawn(spawner, location, player);
        if (entity == null) {
            return false;
        }
        if (uuid != null) {
            EasyArmorStands.getInstance().getHistoryManager().onEntityReplaced(uuid, entity.getUniqueId());
        } else {
            uuid = entity.getUniqueId();
        }
        return true;
    }

    @Override
    public boolean undo(Player player) {
        E entity = findEntity();
        if (entity == null) {
            throw new IllegalStateException();
        }
        if (!EntitySpawner.canRemove(entity, player)) {
            return false;
        }
        location = entity.getLocation();
        spawner = new CloneSpawner<>(entity);
        entity.remove();
        return true;
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
