package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.capability.armswing.ArmSwingEvent;
import me.m56738.easyarmorstands.capability.entityplace.EntityPlaceEvent;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.history.action.ElementCreateAction;
import me.m56738.easyarmorstands.history.action.ElementDestroyAction;
import me.m56738.easyarmorstands.node.EntitySelectionNode;
import me.m56738.easyarmorstands.session.context.ClickContextImpl;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
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
        return EasyArmorStands.getInstance().isTool(item) && player.hasPermission("easyarmorstands.edit");
    }

    private boolean isHoldingTool(Player player) {
        if (!player.hasPermission("easyarmorstands.edit")) {
            return false;
        }
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        EquipmentCapability equipmentCapability = EasyArmorStands.getInstance().getCapability(EquipmentCapability.class);
        EntityEquipment equipment = player.getEquipment();
        for (EquipmentSlot hand : equipmentCapability.getHands()) {
            ItemStack item = equipmentCapability.getItem(equipment, hand);
            if (plugin.isTool(item)) {
                return true;
            }
        }
        return false;
    }

    public boolean onLeftClick(Player player, ItemStack item, Block block) {
        SessionImpl session = manager.getSession(player);
        if (session != null) {
            session.handleClick(new ClickContextImpl(session.eyeRay(), ClickContext.Type.LEFT_CLICK, null, block));
            return true;
        }
        return isTool(player, item);
    }

    public boolean onLeftClickEntity(Player player, Entity entity, ItemStack item) {
        SessionImpl session = manager.getSession(player);
        if (session != null) {
            session.handleClick(new ClickContextImpl(session.eyeRay(), ClickContext.Type.LEFT_CLICK, entity, null));
            return true;
        }
        return onLeftClick(player, item, null);
    }

    public boolean onRightClick(Player player, ItemStack item, Block block) {
        SessionImpl session = manager.getSession(player);
        if (session != null) {
            session.handleClick(new ClickContextImpl(session.eyeRay(), ClickContext.Type.RIGHT_CLICK, null, block));
            return true;
        }
        if (!isTool(player, item)) {
            return false;
        }
        session = manager.start(player);
        if (player.isSneaking() && player.hasPermission("easyarmorstands.spawn")) {
            SessionImpl.openSpawnMenu(session.player());
        }
        return true;
    }

    public boolean onRightClickEntity(Player player, Entity entity, ItemStack item) {
        SessionImpl session = manager.getSession(player);
        if (session != null) {
            session.handleClick(new ClickContextImpl(session.eyeRay(), ClickContext.Type.RIGHT_CLICK, entity, null));
            return true;
        }
        return onRightClick(player, item, null);
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.LEFT_CLICK_AIR && action != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (onLeftClick(event.getPlayer(), event.getItem(), event.getClickedBlock())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeftClick(ArmSwingEvent event) {
        Player player = event.getPlayer();
        EntityEquipment equipment = player.getEquipment();
        ItemStack item = equipmentCapability.getItem(equipment, event.getHand());
        if (onLeftClick(player, item, null)) {
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
        if (isHoldingTool(player)) {
            if (manager.getSession(player) == null) {
                manager.start(player);
            }
        } else {
            manager.stop(player);
        }
    }

    @EventHandler
    public void onHoldItem(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTask(plugin, () -> updateHeldItem(player));
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTask(plugin, () -> updateHeldItem(player));
    }

    @EventHandler
    public void onClick(InventoryCloseEvent event) {
        HumanEntity player = event.getPlayer();
        if (!(player instanceof Player)) {
            return;
        }
        Bukkit.getScheduler().runTask(plugin, () -> updateHeldItem((Player) player));
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (onRightClick(event.getPlayer(), event.getItem(), event.getClickedBlock())) {
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
        Bukkit.getScheduler().runTask(EasyArmorStands.getInstance(), () -> {
            Element element = EasyArmorStands.getInstance().getEntityElementProviderRegistry().getElement(entity);
            if (element != null) {
                EasyArmorStands.getInstance().getHistory(player).push(new ElementCreateAction(element));
            }
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDestroyEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        if (!(damager instanceof Player)) {
            return;
        }
        Player player = (Player) damager;
        Entity entity = event.getEntity();

        Element element = EasyArmorStands.getInstance().getEntityElementProviderRegistry().getElement(entity);
        if (element == null) {
            return;
        }

        ElementDestroyAction action = new ElementDestroyAction(element);
        Bukkit.getScheduler().runTask(plugin, () -> {
            if (entity.isDead()) {
                EasyArmorStands.getInstance().getHistory(player).push(action);
            }
        });
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        SessionImpl session = manager.getSession(player);
        if (session != null) {
            EntitySelectionNode node = session.findNode(EntitySelectionNode.class);
            if (node != null) {
                session.clearNode();
                session.pushNode(node);
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        for (ItemStack item : event.getInventory().getMatrix()) {
            if (EasyArmorStands.getInstance().isTool(item)) {
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
}