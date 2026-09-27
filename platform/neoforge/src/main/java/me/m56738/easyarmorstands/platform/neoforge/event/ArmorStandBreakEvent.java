package me.m56738.easyarmorstands.platform.neoforge.event;

import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.event.entity.EntityEvent;

public class ArmorStandBreakEvent extends EntityEvent {
    private final Entity attributedTo;

    public ArmorStandBreakEvent(Entity entity, Entity attributedTo) {
        super(entity);
        this.attributedTo = attributedTo;
    }

    public Entity getAttributedTo() {
        return attributedTo;
    }
}
