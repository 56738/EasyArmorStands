package me.m56738.easyarmorstands.menu.inventory;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.Nullable;

public class ElementInventoryListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof ElementInventory elementInventory)) {
            return;
        }
        Plugin plugin = EasyArmorStandsPlugin.getInstance();
        plugin.getServer().getScheduler().runTask(plugin, () -> updateItems(elementInventory));
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getInventory().getHolder() instanceof ElementInventory elementInventory)) {
            return;
        }
        Plugin plugin = EasyArmorStandsPlugin.getInstance();
        plugin.getServer().getScheduler().runTask(plugin, () -> updateItems(elementInventory));
    }

    private void updateItems(ElementInventory inventory) {
        for (Int2ObjectMap.Entry<@Nullable Property<ItemStack>> entry : inventory.getProperties().int2ObjectEntrySet()) {
            int slot = entry.getIntKey();
            Property<ItemStack> property = entry.getValue();
            if (property != null) {
                property.setValue(Util.wrapItem(inventory.getInventory().getItem(slot)));
            }
        }
        inventory.getContainer().commit();
    }
}
