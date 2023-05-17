package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.session.EntitySpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class SpawnSlot<T extends Entity> implements InventorySlot {
    private final SpawnMenu menu;
    private final EntitySpawner<T> spawner;
    private final ItemStack item;

    public SpawnSlot(SpawnMenu menu, EntitySpawner<T> spawner, ItemStack item) {
        this.menu = menu;
        this.spawner = spawner;
        this.item = item;
    }

    @Override
    public void initialize(int slot) {
        menu.getInventory().setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, ClickType type) {
        if (click) {
            menu.getSession().spawn(spawner);
            menu.queueTask(() -> {
                Player player = menu.getSession().getPlayer();
                if (menu.getInventory().equals(player.getOpenInventory().getTopInventory())) {
                    player.closeInventory();
                }
            });
        }
        return false;
    }
}
