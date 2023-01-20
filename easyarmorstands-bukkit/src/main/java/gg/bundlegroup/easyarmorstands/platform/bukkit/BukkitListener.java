package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.platform.EasArmorEntity;
import gg.bundlegroup.easyarmorstands.platform.EasInventoryListener;
import gg.bundlegroup.easyarmorstands.platform.EasListener;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EquipmentAccessor;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class BukkitListener implements Listener {
    private final BukkitPlatform platform;
    private final EasListener listener;
    private final EquipmentAccessor equipmentAccessor;
    private final EasArmorEntity.Slot[] hands = new EasArmorEntity.Slot[]{
            EasArmorEntity.Slot.MAIN_HAND, EasArmorEntity.Slot.OFF_HAND
    };

    public BukkitListener(BukkitPlatform platform, EasListener listener) {
        this.platform = platform;
        this.listener = listener;
        this.equipmentAccessor = platform.equipmentAccessor();
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.LEFT_CLICK_AIR && action != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (listener.onLeftClick(platform.getPlayer(event.getPlayer()), platform.getItem(event.getItem()))) {
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

        Entity entity = event.getEntity();
        if (!(entity instanceof ArmorStand)) {
            return;
        }
        ArmorStand armorStand = (ArmorStand) entity;

        BukkitPlayer bukkitPlayer = platform.getPlayer(player);
        BukkitArmorStand bukkitArmorStand = platform.getArmorStand(armorStand);
        EntityEquipment equipment = player.getEquipment();
        for (EasArmorEntity.Slot hand : hands) {
            ItemStack item = equipmentAccessor.getItem(equipment, hand);
            if (listener.onLeftClickArmorStand(bukkitPlayer, bukkitArmorStand, platform.getItem(item))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (listener.onRightClick(platform.getPlayer(event.getPlayer()), platform.getItem(event.getItem()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClickEntity(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (!(entity instanceof ArmorStand)) {
            return;
        }
        ArmorStand armorStand = (ArmorStand) entity;

        Player player = event.getPlayer();
        BukkitPlayer bukkitPlayer = platform.getPlayer(player);
        BukkitArmorStand bukkitArmorStand = platform.getArmorStand(armorStand);
        EntityEquipment equipment = player.getEquipment();
        for (EasArmorEntity.Slot hand : hands) {
            ItemStack item = equipmentAccessor.getItem(equipment, hand);
            if (listener.onRightClickArmorStand(bukkitPlayer, bukkitArmorStand, platform.getItem(item))) {
                event.setCancelled(true);
            }
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

    private EasInventoryListener getInventoryListener(InventoryEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof BukkitInventoryHolder) {
            return ((BukkitInventoryHolder) holder).getListener();
        } else {
            return null;
        }
    }

    private boolean onInventoryClick(EasInventoryListener inventoryListener, int slot) {
        Bukkit.getScheduler().runTask(platform.plugin(), inventoryListener::update);
        return inventoryListener.onClick(slot);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        EasInventoryListener inventoryListener = getInventoryListener(event);
        if (inventoryListener == null) {
            return;
        }
        InventoryAction action = event.getAction();
        if (action != InventoryAction.PICKUP_ALL && action != InventoryAction.PLACE_ALL && action != InventoryAction.SWAP_WITH_CURSOR) {
            event.setCancelled(true);
            return;
        }
        int slot = event.getRawSlot();
        if (slot != event.getView().convertSlot(slot)) {
            return;
        }
        if (onInventoryClick(inventoryListener, slot)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        EasInventoryListener inventoryListener = getInventoryListener(event);
        if (inventoryListener == null) {
            return;
        }
        if (event.getRawSlots().size() != 1) {
            event.setCancelled(true);
            return;
        }
        int slot = event.getRawSlots().iterator().next();
        if (slot != event.getView().convertSlot(slot)) {
            return;
        }
        if (onInventoryClick(inventoryListener, slot)) {
            event.setCancelled(true);
        }
    }
}
