package me.m56738.easyarmorstands.session.v1_19_4;

import me.m56738.easyarmorstands.addon.display.DisplayAddon;
import me.m56738.easyarmorstands.api.event.menu.SpawnMenuInitializeEvent;
import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.menu.slot.SpawnSlot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTeleportEvent;


public class DisplaySessionListener implements Listener {
    private final DisplayAddon addon;

    public DisplaySessionListener(DisplayAddon addon) {
        this.addon = addon;
    }

    @EventHandler
    public void onSpawnMenuBuild(SpawnMenuInitializeEvent event) {
        MenuBuilder builder = event.getMenuBuilder();
        Player player = event.getPlayer();
        if (player.hasPermission("easyarmorstands.spawn.itemdisplay")) {
            builder.addButton(new SpawnSlot(
                    addon.getItemDisplayType(),
                    addon.getItemDisplayType().getButtonTemplate()));
        }
        if (player.hasPermission("easyarmorstands.spawn.blockdisplay")) {
            builder.addButton(new SpawnSlot(
                    addon.getBlockDisplayType(),
                    addon.getBlockDisplayType().getButtonTemplate()));
        }
        if (player.hasPermission("easyarmorstands.spawn.textdisplay")) {
            builder.addButton(new SpawnSlot(
                    addon.getTextDisplayType(),
                    addon.getTextDisplayType().getButtonTemplate()));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSpawn(EntitySpawnEvent event) {
        if (event.getEntity().hasMetadata("easyarmorstands_force")) {
            event.setCancelled(false);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTeleport(EntityTeleportEvent event) {
        if (event.getEntity().hasMetadata("easyarmorstands_force")) {
            event.setCancelled(false);
        }
    }
}
