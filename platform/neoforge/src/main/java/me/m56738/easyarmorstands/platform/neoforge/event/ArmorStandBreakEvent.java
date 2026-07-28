package me.m56738.easyarmorstands.platform.neoforge.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.event.entity.EntityEvent;

public class ArmorStandBreakEvent extends EntityEvent {
    private final DamageSource source;

    public ArmorStandBreakEvent(Entity entity, DamageSource source) {
        super(entity);
        this.source = source;
    }

    public DamageSource getSource() {
        return source;
    }
}
