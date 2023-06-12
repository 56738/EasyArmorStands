package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.property.ChangeContext;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;

public class EntityDestroyAction<E extends Entity> extends EntitySpawnAction<E> {
    public EntityDestroyAction(E entity) {
        super(entity);
    }

    @Override
    public boolean execute(ChangeContext context) {
        return super.undo(context);
    }

    @Override
    public boolean undo(ChangeContext context) {
        return super.execute(context);
    }

    @Override
    public Component describe() {
        EntityTypeCapability entityTypeCapability = EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class);
        return Component.text("Destroyed ").append(entityTypeCapability.getName(getEntityType()));
    }
}
