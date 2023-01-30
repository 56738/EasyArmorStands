package me.m56738.easyarmorstands.bukkit.platform;

import me.m56738.easyarmorstands.bukkit.feature.EquipmentAccessor;
import me.m56738.easyarmorstands.core.platform.EasArmorEntity;
import me.m56738.easyarmorstands.core.platform.EasInventoryListener;
import me.m56738.easyarmorstands.core.platform.EasItem;
import me.m56738.easyarmorstands.core.platform.EasListener;
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
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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

    public boolean onLeftClick(Player player, ItemStack item) {
        return listener.onLeftClick(platform.getPlayer(player), platform.getItem(item));
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.LEFT_CLICK_AIR && action != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (onLeftClick(event.getPlayer(), event.getItem())) {
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
        BukkitPlayer bukkitPlayer = platform.getPlayer(player);

        Entity entity = event.getEntity();
        if (!(entity instanceof ArmorStand)) {
            EntityEquipment equipment = player.getEquipment();
            for (EasArmorEntity.Slot hand : hands) {
                ItemStack item = equipmentAccessor.getItem(equipment, hand);
                BukkitItem bukkitItem = platform.getItem(item);
                if (listener.onLeftClick(bukkitPlayer, bukkitItem)) {
                    event.setCancelled(true);
                }
            }
            return;
        }
        ArmorStand armorStand = (ArmorStand) entity;

        BukkitArmorStand bukkitArmorStand = platform.getArmorStand(armorStand);
        EntityEquipment equipment = player.getEquipment();
        for (EasArmorEntity.Slot hand : hands) {
            ItemStack item = equipmentAccessor.getItem(equipment, hand);
            BukkitItem bukkitItem = platform.getItem(item);
            if (listener.onLeftClickArmorStand(bukkitPlayer, bukkitArmorStand, bukkitItem, event.isCancelled())) {
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
        Player player = event.getPlayer();
        BukkitPlayer bukkitPlayer = platform.getPlayer(player);
        Entity entity = event.getRightClicked();
        if (!(entity instanceof ArmorStand)) {
            EntityEquipment equipment = player.getEquipment();
            for (EasArmorEntity.Slot hand : hands) {
                ItemStack item = equipmentAccessor.getItem(equipment, hand);
                BukkitItem bukkitItem = platform.getItem(item);
                if (listener.onRightClick(bukkitPlayer, bukkitItem)) {
                    event.setCancelled(true);
                }
            }
            return;
        }
        ArmorStand armorStand = (ArmorStand) entity;

        BukkitArmorStand bukkitArmorStand = platform.getArmorStand(armorStand);
        EntityEquipment equipment = player.getEquipment();
        for (EasArmorEntity.Slot hand : hands) {
            ItemStack item = equipmentAccessor.getItem(equipment, hand);
            BukkitItem bukkitItem = platform.getItem(item);
            if (listener.onRightClickArmorStand(bukkitPlayer, bukkitArmorStand, bukkitItem, event.isCancelled())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onRightClickAtEntity(PlayerInteractAtEntityEvent event) {
        onRightClickEntity(event);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        BukkitPlayer player = platform.getPlayer(event.getPlayer());
        BukkitItem item = platform.getItem(event.getItemDrop().getItemStack());
        if (listener.onDrop(player, item)) {
            event.setCancelled(true);
        }
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

    private boolean onInventoryClick(EasInventoryListener inventoryListener,
                                     int slot, boolean click, boolean put, boolean take, EasItem cursor) {
        Bukkit.getScheduler().runTask(platform.plugin(), inventoryListener::update);
        return !inventoryListener.onClick(slot, click, put, take, cursor);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        EasInventoryListener inventoryListener = getInventoryListener(event);
        if (inventoryListener == null) {
            return;
        }
        InventoryAction action = event.getAction();
        if (action == InventoryAction.COLLECT_TO_CURSOR) {
            event.setCancelled(true);
            return;
        }
        int slot = event.getSlot();
        if (slot != event.getRawSlot()) {
            // Not the upper inventory
            if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                event.setCancelled(true);
            }
            return;
        }
        boolean click = false;
        boolean put = false;
        boolean take = false;
        switch (action) {
            case NOTHING:
            case CLONE_STACK:
                click = true;
                break;
            case PICKUP_ALL:
            case PICKUP_SOME:
            case PICKUP_HALF:
            case PICKUP_ONE:
            case MOVE_TO_OTHER_INVENTORY:
                click = true;
                take = true;
                break;
            case PLACE_ALL:
            case PLACE_SOME:
            case PLACE_ONE:
                click = true;
                put = true;
                break;
            case SWAP_WITH_CURSOR:
                click = true;
                put = true;
                take = true;
                break;
            case DROP_ALL_SLOT:
            case DROP_ONE_SLOT:
            case HOTBAR_MOVE_AND_READD:
            case HOTBAR_SWAP:
                take = true;
                break;
            case DROP_ALL_CURSOR:
            case DROP_ONE_CURSOR:
                return;
            default:
                event.setCancelled(true);
                return;
        }
        EasItem cursor = platform.getItem(event.getCursor());
        if (onInventoryClick(inventoryListener, slot, click, put, take, cursor)) {
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
        EasItem cursor = platform.getItem(event.getOldCursor());
        if (onInventoryClick(inventoryListener, slot, true, true, false, cursor)) {
            event.setCancelled(true);
        }
    }
}
