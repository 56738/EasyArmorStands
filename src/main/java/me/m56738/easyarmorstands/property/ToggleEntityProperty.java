package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.menu.EntityMenu;
import me.m56738.easyarmorstands.menu.TogglePropertySlot;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

@Deprecated
public abstract class ToggleEntityProperty<E extends Entity, T> implements ButtonEntityProperty<E, T> {
    public abstract T getNextValue(E entity);

    public abstract ItemStack createToggleButton(E entity);

    public void toggle(Session session, E entity) {
        bind(entity).setValue(getNextValue(entity));
        session.commit();
    }

    @Override
    public InventorySlot createSlot(EntityMenu<? extends E> menu) {
        return new TogglePropertySlot<>(menu, this);
    }
}
