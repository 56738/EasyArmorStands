package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class EntityDestroyAction<E extends Entity> extends EntitySpawnAction<E> {
    public EntityDestroyAction(E entity) {
        super(entity);
    }

    @Override
    public boolean execute(Player player) {
        return super.undo(player);
    }

    @Override
    public boolean undo(Player player) {
        return super.execute(player);
    }

    @Override
    public Component describe() {
        EntityTypeCapability entityTypeCapability = EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class);
        return Component.text("Destroyed ").append(entityTypeCapability.getName(getEntityType()));
    }
}
