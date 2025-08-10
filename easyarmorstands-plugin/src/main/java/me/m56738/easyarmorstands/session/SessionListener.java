package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.node.ElementSelectionNode;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.capability.handswap.SwapHandItemsListener;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.history.action.ElementCreateAction;
import me.m56738.easyarmorstands.history.action.ElementDestroyAction;
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
import org.bukkit.event.inventory.FurnaceBurnEvent;
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
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SessionListener implements Listener, SwapHandItemsListener {
    private final EasyArmorStandsPlugin plugin;
    private final SessionManagerImpl manager;
    private final Map<Player, Integer> suppressClick = new HashMap<>();
    private final Map<Player, Integer> suppressArmSwing = new HashMap<>();

    public SessionListener(EasyArmorStandsPlugin plugin, SessionManagerImpl manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    private boolean isHoldingTool(Player player) {
        if (!player.hasPermission(Permissions.EDIT)) {
            return false;
        }
        EquipmentCapability equipmentCapability = plugin.getCapability(EquipmentCapability.class);
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
        if (!suppressClick.containsKey(player)) {
            session.handleClick(new ClickContextImpl(session.eyeRay(), ClickContext.Type.LEFT_CLICK, entity, block));
        }
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
            if (!suppressClick.containsKey(player)) {
                session.handleClick(new ClickContextImpl(session.eyeRay(), ClickContext.Type.RIGHT_CLICK, entity, block));
            }
            return true;
        }
        if (!isHoldingTool(player)) {
            return false;
        }
        session = manager.startSession(player);
        session.setToolRequired(true);
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

    @Override
    public boolean handleSwap(Player player) {
        SessionImpl session = manager.getSession(player);
        if (session == null) {
            return false;
        }
        session.handleClick(new ClickContextImpl(session.eyeRay(), ClickContext.Type.SWAP_HANDS, null, null));
        return true;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        suppressArmSwing.put(event.getPlayer(), 5);
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

    @EventHandler
    public void onLeftClick(PlayerAnimationEvent event) {
        PlayerAnimationType type = event.getAnimationType();
        if (type != PlayerAnimationType.ARM_SWING) {
            return;
        }

        Bukkit.getScheduler().runTask(plugin, () -> {
            if (!suppressArmSwing.containsKey(event.getPlayer())) {
                handleLeftClick(event.getPlayer());
            }
        });
    }

    public void updateHeldItem(Player player) {
        SessionImpl session = manager.getSession(player);
        if (session != null) {
            updateHeldItem(session);
        } else if (isHoldingTool(player)) {
            session = manager.startSession(player);
            session.setToolRequired(true);
        }
    }

    public void updateHeldItem(SessionImpl session) {
        Player player = session.player();
        if (session.isToolRequired() && !isHoldingTool(player)) {
            manager.stopSession(player);
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
    public void onClose(InventoryCloseEvent event) {
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
        Bukkit.getScheduler().runTask(plugin, () -> {
            EasPlayer context = new EasPlayer(player);
            Element element = plugin.entityElementProviderRegistry().getElement(entity);
            if (element != null) {
                context.history().push(new ElementCreateAction(element));
                context.clipboard().handleAutoApply(element, context);
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

        Element element = plugin.entityElementProviderRegistry().getElement(entity);
        if (element == null) {
            return;
        }

        ElementDestroyAction action = new ElementDestroyAction(element);
        Bukkit.getScheduler().runTask(plugin, () -> {
            if (entity.isDead()) {
                plugin.getHistory(player).push(action);
            }
        });
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        suppressClick.put(player, 5);
        suppressArmSwing.put(event.getPlayer(), 5);
        SessionImpl session = manager.getSession(player);
        if (session != null) {
            ElementSelectionNode node = session.findNode(ElementSelectionNode.class);
            if (node != null && node != session.getNode()) {
                session.returnToNode(node);
                event.setCancelled(true);
            }
        }
        Bukkit.getScheduler().runTask(plugin, () -> updateHeldItem(player));
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        if (plugin.isTool(event.getFuel())) {
            event.setBurnTime(0);
        }
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        for (ItemStack item : event.getInventory().getMatrix()) {
            if (plugin.isTool(item)) {
                event.getInventory().setResult(null);
                break;
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        manager.hideSkeletons(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        manager.stopSession(event.getPlayer());
        suppressClick.remove(event.getPlayer());
        suppressArmSwing.remove(event.getPlayer());
    }

    public void update() {
        expireEntries(suppressClick);
        expireEntries(suppressArmSwing);

        for (SessionImpl session : new ArrayList<>(manager.getAllSessions())) {
            updateHeldItem(session);
        }
    }

    private void expireEntries(Map<Player, Integer> map) {
        for (Iterator<Map.Entry<Player, Integer>> iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<Player, Integer> entry = iterator.next();
            int value = entry.getValue();
            if (value > 0) {
                entry.setValue(value - 1);
            } else {
                iterator.remove();
            }
        }
    }
}
