package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.platform.EasListener;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;

public class BukkitListener implements Listener {
    private final BukkitPlatform platform;
    private final EasListener listener;

    public BukkitListener(BukkitPlatform platform, EasListener listener) {
        this.platform = platform;
        this.listener = listener;
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            if (listener.onLeftClick(platform.getPlayer(event.getPlayer()))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLeftClickEntity(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager();
        if (attacker instanceof Player) {
            Player player = (Player) attacker;
            Entity entity = event.getEntity();
            if (entity instanceof ArmorStand) {
                ArmorStand armorStand = (ArmorStand) entity;
                if (listener.onLeftClickArmorStand(platform.getPlayer(player), platform.getArmorStand(armorStand))) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (listener.onRightClick(platform.getPlayer(event.getPlayer()))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onRightClickEntity(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (entity instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) entity;
            if (listener.onRightClickArmorStand(platform.getPlayer(event.getPlayer()), platform.getArmorStand(armorStand))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onRightClickAtEntity(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (entity instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) entity;
            if (listener.onRightClickArmorStand(platform.getPlayer(event.getPlayer()), platform.getArmorStand(armorStand))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onScroll(PlayerItemHeldEvent event) {
        listener.onScroll(platform.getPlayer(event.getPlayer()), event.getPreviousSlot(), event.getNewSlot());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        listener.onJoin(platform.getPlayer(event.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        listener.onQuit(platform.getPlayer(event.getPlayer()));
    }
}
