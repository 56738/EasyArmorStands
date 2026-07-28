package me.m56738.easyarmorstands.platform.neoforge.event;

import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.event.entity.EntityEvent;

public class EntityPlaceEvent extends EntityEvent {
    private final Entity sourceEntity;

    public EntityPlaceEvent(Entity entity, Entity sourceEntity) {
        super(entity);
        this.sourceEntity = sourceEntity;
    }

    public Entity getSourceEntity() {
        return sourceEntity;
    }
}
