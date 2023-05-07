package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.property.ToggleEntityProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Entity;
import org.bukkit.event.inventory.ClickType;

public class TogglePropertySlot<E extends Entity, T> implements InventorySlot {
    private final EntityMenu<E> menu;
    private final ToggleEntityProperty<? super E, T> property;

    public TogglePropertySlot(EntityMenu<E> menu, ToggleEntityProperty<? super E, T> property) {
        this.menu = menu;
        this.property = property;
    }

    @Override
    public void initialize(int slot) {
        menu.getInventory().setItem(slot, property.createToggleButton(menu.getEntity()));
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, ClickType type) {
        if (click) {
            String permission = property.getPermission();
            if (permission != null && !menu.getSession().getPlayer().hasPermission(permission)) {
                menu.getSession().sendMessage(Component.text("You don't have permission to edit this property.", NamedTextColor.RED));
                return false;
            }
            property.toggle(menu.getSession(), menu.getEntity());
            initialize(slot);
        }
        return false;
    }
}
