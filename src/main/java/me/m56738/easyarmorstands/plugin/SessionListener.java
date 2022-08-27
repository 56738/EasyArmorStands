package me.m56738.easyarmorstands.plugin;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SessionListener implements Listener {
    private final SessionManager manager;
    private final BukkitAudiences adventure;

    public SessionListener(SessionManager manager, BukkitAudiences adventure) {
        this.manager = manager;
        this.adventure = adventure;
    }

    private boolean handleRightClickWhileEditing(Player player) {
        final EasSession session = manager.getSession(player);
        if (session != null) {
            session.handleRightClick();
            return true;
        }
        return false;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRightClickWhileEditing(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (handleRightClickWhileEditing(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRightClickWhileEditing(PlayerInteractEntityEvent event) {
        if (handleRightClickWhileEditing(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRightClickWhileEditing(PlayerInteractAtEntityEvent event) {
        if (handleRightClickWhileEditing(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onRightClickArmorStand(PlayerInteractAtEntityEvent event) {
        final Player player = event.getPlayer();
        final Entity entity = event.getRightClicked();
        if (!(entity instanceof ArmorStand)) {
            return;
        }

        if (player.getInventory().getItemInHand().getType() != Material.STICK) {
            return;
        }

        if (!player.hasPermission("easyarmorstands.edit")) {
            return;
        }

        final ArmorStand armorStand = (ArmorStand) entity;
        final EasSession session = new EasSession(manager, player, adventure.player(player), armorStand);
        session.start();
        event.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final EasSession session = manager.getSession(event.getPlayer());
        if (session != null) {
            session.stop();
        }
    }

    @EventHandler
    public void onSwitchItem(PlayerItemHeldEvent event) {
        final EasSession session = manager.getSession(event.getPlayer());
        if (session != null) {
            session.stop();
        }
    }
}
