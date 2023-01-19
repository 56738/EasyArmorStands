package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.platform.EasListener;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.HeldItemGetter;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.ToolChecker;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class BukkitListener implements Listener {
    private final BukkitPlatform platform;
    private final EasListener listener;
    private final ToolChecker toolChecker;
    private final HeldItemGetter heldItemGetter;

    public BukkitListener(BukkitPlatform platform, EasListener listener) {
        this.platform = platform;
        this.listener = listener;
        this.toolChecker = platform.toolChecker();
        this.heldItemGetter = platform.heldItemGetter();
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        if (!isTool(event.getItem())) {
            return;
        }

        Action action = event.getAction();
        if (action != Action.LEFT_CLICK_AIR && action != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (listener.onLeftClick(platform.getPlayer(event.getPlayer()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeftClickEntity(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager();
        if (!(attacker instanceof Player)) {
            return;
        }
        Player player = (Player) attacker;

        if (!isHoldingTool(player)) {
            return;
        }

        Entity entity = event.getEntity();
        if (!(entity instanceof ArmorStand)) {
            return;
        }
        ArmorStand armorStand = (ArmorStand) entity;

        if (listener.onLeftClickArmorStand(platform.getPlayer(player), platform.getArmorStand(armorStand))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (!isTool(event.getItem())) {
            return;
        }

        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (listener.onRightClick(platform.getPlayer(event.getPlayer()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClickEntity(PlayerInteractEntityEvent event) {
        if (!isHoldingTool(event.getPlayer())) {
            return;
        }

        Entity entity = event.getRightClicked();
        if (!(entity instanceof ArmorStand)) {
            return;
        }
        ArmorStand armorStand = (ArmorStand) entity;

        if (listener.onRightClickArmorStand(platform.getPlayer(event.getPlayer()), platform.getArmorStand(armorStand))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClickAtEntity(PlayerInteractAtEntityEvent event) {
        onRightClickEntity(event);
    }

    @EventHandler
    public void onScroll(PlayerItemHeldEvent event) {
        listener.onScroll(platform.getPlayer(event.getPlayer()), event.getPreviousSlot(), event.getNewSlot());
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        listener.onLogin(platform.getPlayer(event.getPlayer()));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        listener.onJoin(platform.getPlayer(event.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        listener.onQuit(platform.getPlayer(event.getPlayer()));
    }

    private boolean isTool(ItemStack item) {
        return toolChecker.isTool(item);
    }

    private boolean isHoldingTool(Player player) {
        for (ItemStack item : heldItemGetter.getHeldItems(player)) {
            if (isTool(item)) {
                return true;
            }
        }
        return false;
    }

//        if (!isTool(player.getInventory().getItem(event.getHand()))) {
//            return;
//        }
//
//        if (!player.hasPermission("easyarmorstands.edit")) {
//            return;
//        }
}
