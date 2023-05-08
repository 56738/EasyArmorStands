package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;

public class EntityDestroyAction<E extends Entity> extends EntitySpawnAction<E> {
    public EntityDestroyAction(Entity entity) {
        super(entity);
    }

    @Override
    public boolean execute() {
        return super.undo();
    }

    @Override
    public boolean undo() {
        return super.execute();
    }

    @Override
    public Component describe() {
        EntityTypeCapability entityTypeCapability = EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class);
        return Component.text("Destroyed ").append(entityTypeCapability.getName(getEntityType()));
    }
}
