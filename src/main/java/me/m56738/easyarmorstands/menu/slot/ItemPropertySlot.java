package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemPropertySlot implements MenuSlot {
    private final Property<ItemStack> property;
    private final Session session;

    public ItemPropertySlot(Property<ItemStack> property, Session session) {
        this.property = property;
        this.session = session;
    }

    @Override
    public ItemStack getItem() {
        return property.getValue();
    }

    @Override
    public void onClick(MenuClick click) {
        String permission = property.getPermission();
        if (permission != null && !click.player().hasPermission(permission)) {
            click.cancel();
            return;
        }
        click.queueTask(item -> {
            if (item == null) {
                item = new ItemStack(Material.AIR);
            }
            session.tryChange(property, item);
            session.commit();
        });
    }
}
