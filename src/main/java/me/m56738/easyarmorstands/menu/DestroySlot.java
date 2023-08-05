package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.history.action.EntityDestroyAction;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.session.EntitySpawner;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class DestroySlot implements InventorySlot {
    private final EntityMenu<?> menu;

    public DestroySlot(EntityMenu<?> menu) {
        this.menu = menu;
    }

    @Override
    public void initialize(int slot) {
        ItemStack item = Util.createItem(
                ItemType.BARRIER,
                Component.text("Destroy", NamedTextColor.BLUE),
                Collections.singletonList(
                        Component.text("Deletes this entity.", NamedTextColor.GRAY)
                )
        );
        menu.getInventory().setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, ClickType type) {
        if (!click) {
            return false;
        }
        menu.queueTask(() -> {
            Session session = menu.getSession();
            Player player = session.getPlayer();
            Entity entity = menu.getEntity();
            EntityDestroyAction<?> action = new EntityDestroyAction<>(entity);
            if (!EntitySpawner.tryRemove(entity, player)) {
                return;
            }
            EasyArmorStands.getInstance().getHistory(player).push(action);
            menu.close(player);
        });
        return false;
    }
}
