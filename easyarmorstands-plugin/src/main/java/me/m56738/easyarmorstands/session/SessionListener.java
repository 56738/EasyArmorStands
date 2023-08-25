package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.history.action.ElementCreateAction;
import me.m56738.easyarmorstands.history.action.ElementDestroyAction;
import me.m56738.easyarmorstands.node.EntitySelectionNode;
import me.m56738.easyarmorstands.permission.Permissions;
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
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
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

    public SessionListener(Plugin plugin, SessionManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    private boolean isHoldingTool(Player player) {
        if (!player.hasPermission(Permissions.EDIT)) {
            return false;
        }
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        EquipmentCapability equipmentCapability = EasyArmorStandsPlugin.getInstance().getCapability(EquipmentCapability.class);
        EntityEquipment equipment = player.getEquipment();
        for (EquipmentSlot hand : equipmentCapability.getHands()) {
            ItemStack item = equipmentCapability.getItem(equipment, hand);
            if (plugin.isTool(item)) {
                return true;
            }
        }
        return false;
    }

    public boolean handleLeftClick(Player player, Entity entity, Block block) {
        SessionImpl session = manager.getSession(player);
        if (session == null) {
            return false;
        }
        session.handleClick(new ClickContextImpl(session.eyeRay(), ClickContext.Type.LEFT_CLICK, entity, block));
        return true;
    }

    public boolean handleLeftClick(Player player) {
        return handleLeftClick(player, null, null);
    }

    public boolean handleLeftClick(Player player, Block block) {
        return handleLeftClick(player, null, block);
    }

    public boolean handleLeftClick(Player player, Entity entity) {
        return handleLeftClick(player, entity, null);
    }

    private boolean handleRightClick(Player player, Entity entity, Block block) {
        SessionImpl session = manager.getSession(player);
        if (session != null) {
            session.handleClick(new ClickContextImpl(session.eyeRay(), ClickContext.Type.RIGHT_CLICK, entity, block));
            return true;
        }
        if (!isHoldingTool(player)) {
            return false;
        }
        session = manager.start(player);
        if (player.isSneaking() && player.hasPermission(Permissions.SPAWN)) {
            Menu menu = EasyArmorStandsPlugin.getInstance().createSpawnMenu(session.player());
            player.openInventory(menu.getInventory());
        }
        return true;
    }

    public boolean handleRightClick(Player player) {
        return handleRightClick(player, null, null);
    }

    public boolean handleRightClick(Player player, Block block) {
        return handleRightClick(player, null, block);
    }

    public boolean handleRightClick(Player player, Entity entity) {
        return handleRightClick(player, entity, null);
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.LEFT_CLICK_AIR && action != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (handleLeftClick(event.getPlayer(), event.getClickedBlock())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeftClick(PlayerAnimationEvent event) {
        if (event.getAnimationType() != PlayerAnimationType.ARM_SWING) {
            return;
        }

        Player player = event.getPlayer();
        if (handleLeftClick(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeftClick(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager();
        if (!(attacker instanceof Player)) {
            return;
        }
        Player player = (Player) attacker;
        Entity entity = event.getEntity();
        if (handleLeftClick(player, entity)) {
            event.setCancelled(true);
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

        if (handleRightClick(event.getPlayer(), event.getClickedBlock())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if (handleRightClick(player, entity)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClickAtEntity(PlayerInteractAtEntityEvent event) {
        onRightClick(event);
    }

    public void onPlaceEntity(Player player, Entity entity) {
        Bukkit.getScheduler().runTask(EasyArmorStandsPlugin.getInstance(), () -> {
            Element element = EasyArmorStandsPlugin.getInstance().entityElementProviderRegistry().getElement(entity);
            if (element != null) {
                EasyArmorStandsPlugin.getInstance().getHistory(player).push(new ElementCreateAction(element));
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

        Element element = EasyArmorStandsPlugin.getInstance().entityElementProviderRegistry().getElement(entity);
        if (element == null) {
            return;
        }

        ElementDestroyAction action = new ElementDestroyAction(element);
        Bukkit.getScheduler().runTask(plugin, () -> {
            if (entity.isDead()) {
                EasyArmorStandsPlugin.getInstance().getHistory(player).push(action);
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
            if (EasyArmorStandsPlugin.getInstance().isTool(item)) {
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
