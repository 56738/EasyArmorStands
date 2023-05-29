package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.armswing.ArmSwingEvent;
import me.m56738.easyarmorstands.capability.entityplace.EntityPlaceEvent;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.history.action.EntityDestroyAction;
import me.m56738.easyarmorstands.history.action.EntitySpawnAction;
import me.m56738.easyarmorstands.inventory.InventoryListener;
import me.m56738.easyarmorstands.node.ClickContext;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class SessionListener implements Listener {
    private final Plugin plugin;
    private final SessionManager manager;
    private final EquipmentCapability equipmentCapability;

    public SessionListener(Plugin plugin, SessionManager manager) {
        this.plugin = plugin;
        this.manager = manager;
        this.equipmentCapability = EasyArmorStands.getInstance().getCapability(EquipmentCapability.class);
    }

    private boolean isTool(Player player, ItemStack item) {
        return Util.isTool(item) && player.hasPermission("easyarmorstands.edit");
    }

    public boolean onLeftClick(Player player, ItemStack item) {
        Session session = manager.getSession(player);
        if (session != null) {
            session.handleClick(new ClickContext(me.m56738.easyarmorstands.node.ClickType.LEFT_CLICK, null));
            return true;
        }
        return isTool(player, item);
    }

    public boolean onLeftClickEntity(Player player, Entity entity, ItemStack item) {
        Session session = manager.getSession(player);
        if (session != null) {
            session.handleClick(new ClickContext(me.m56738.easyarmorstands.node.ClickType.LEFT_CLICK, entity));
            return true;
        }
        return onLeftClick(player, item);
    }

    public boolean onRightClick(Player player, ItemStack item) {
        Session session = manager.getSession(player);
        if (session != null) {
            session.handleClick(new ClickContext(me.m56738.easyarmorstands.node.ClickType.RIGHT_CLICK, null));
            return true;
        }
        if (!isTool(player, item)) {
            return false;
        }
        session = manager.start(player);
        if (player.isSneaking() && player.hasPermission("easyarmorstands.spawn")) {
            session.openSpawnMenu();
        }
        return true;
    }

    public boolean onRightClickEntity(Player player, Entity entity, ItemStack item) {
        Session session = manager.getSession(player);
        if (session != null) {
            session.handleClick(new ClickContext(me.m56738.easyarmorstands.node.ClickType.RIGHT_CLICK, entity));
            return true;
        }
        return onRightClick(player, item);
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
    public void onLeftClick(ArmSwingEvent event) {
        Player player = event.getPlayer();
        EntityEquipment equipment = player.getEquipment();
        ItemStack item = equipmentCapability.getItem(equipment, event.getHand());
        if (onLeftClick(player, item)) {
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
        EntityEquipment equipment = player.getEquipment();
        Entity entity = event.getEntity();
        for (EquipmentSlot hand : equipmentCapability.getHands()) {
            ItemStack item = equipmentCapability.getItem(equipment, hand);
            if (onLeftClickEntity(player, entity, item)) {
                event.setCancelled(true);
            }
        }
    }

    public void updateHeldItem(Player player) {
        if (manager.getSession(player) != null) {
            return;
        }
        EntityEquipment equipment = player.getEquipment();
        for (EquipmentSlot hand : equipmentCapability.getHands()) {
            ItemStack item = equipmentCapability.getItem(equipment, hand);
            if (isTool(player, item)) {
                manager.start(player);
                return;
            }
        }
    }

    @EventHandler
    public void onHoldItem(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItem(event.getNewSlot());
        if (manager.getSession(player) != null) {
            return;
        }
        if (isTool(player, item)) {
            manager.start(player);
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (onRightClick(event.getPlayer(), event.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClickEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        EntityEquipment equipment = player.getEquipment();
        Entity entity = event.getRightClicked();
        for (EquipmentSlot hand : equipmentCapability.getHands()) {
            ItemStack item = equipmentCapability.getItem(equipment, hand);
            if (onRightClickEntity(player, entity, item)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onRightClickAtEntity(PlayerInteractAtEntityEvent event) {
        onRightClickEntity(event);
    }

    @EventHandler
    public void onPlaceEntity(EntityPlaceEvent event) {
        Entity entity = event.getEntity();
        Player player = event.getPlayer();
        EasyArmorStands.getInstance().getHistory(player).push(new EntitySpawnAction<>(entity));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDestroyEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        if (!(damager instanceof Player)) {
            return;
        }
        Player player = (Player) damager;
        Entity entity = event.getEntity();
        Bukkit.getScheduler().runTask(plugin, () -> {
            if (entity.isDead()) {
                EasyArmorStands.getInstance().getHistory(player).push(new EntityDestroyAction<>(entity));
            }
        });
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Session session = manager.getSession(event.getPlayer());
        if (session != null) {
            session.clearNode();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        for (ItemStack item : event.getInventory().getMatrix()) {
            if (Util.isTool(item)) {
                event.getInventory().setResult(null);
                break;
            }
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        manager.hideSkeletons(event.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        manager.hideSkeletons(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        manager.stop(event.getPlayer());
    }

    private InventoryListener getInventoryListener(InventoryEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof InventoryListener) {
            return (InventoryListener) holder;
        } else {
            return null;
        }
    }

    private boolean onInventoryClick(InventoryListener inventoryListener,
                                     int slot, boolean click, boolean put, boolean take, ClickType type) {
        Bukkit.getScheduler().runTask(plugin, inventoryListener::update);
        return !inventoryListener.onClick(slot, click, put, take, type);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryListener inventoryListener = getInventoryListener(event);
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
        if (onInventoryClick(inventoryListener, slot, click, put, take, event.getClick())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        InventoryListener inventoryListener = getInventoryListener(event);
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
        ClickType type = event.getType() == DragType.EVEN ? ClickType.LEFT : ClickType.RIGHT;
        if (onInventoryClick(inventoryListener, slot, true, true, false, type)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        InventoryListener inventoryListener = getInventoryListener(event);
        if (inventoryListener == null) {
            return;
        }
        inventoryListener.onClose();
    }
}
