package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.node.ElementSelectionNode;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.common.clipboard.Clipboard;
import me.m56738.easyarmorstands.common.editor.SessionImpl;
import me.m56738.easyarmorstands.common.editor.context.ClickContextImpl;
import me.m56738.easyarmorstands.common.history.History;
import me.m56738.easyarmorstands.common.history.action.ElementCreateAction;
import me.m56738.easyarmorstands.common.history.action.ElementDestroyAction;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperEntity;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperBlock;
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
import org.bukkit.event.entity.EntityPlaceEvent;
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
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SessionListener implements Listener {
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
        PlayerInventory inventory = player.getInventory();
        return plugin.isTool(inventory.getItemInMainHand()) || plugin.isTool(inventory.getItemInOffHand());
    }

    public boolean handleLeftClick(Player player, Entity entity, Block block) {
        SessionImpl session = manager.getSession(PaperPlayer.fromNative(player));
        if (session == null) {
            return false;
        }
        if (!suppressClick.containsKey(player)) {
            session.handleClick(new ClickContextImpl(session.eyeRay(), ClickContext.Type.LEFT_CLICK, PaperEntity.fromNative(entity), PaperBlock.fromNative(block)));
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
        SessionImpl session = manager.getSession(PaperPlayer.fromNative(player));
        if (session != null) {
            if (!suppressClick.containsKey(player)) {
                session.handleClick(new ClickContextImpl(session.eyeRay(), ClickContext.Type.RIGHT_CLICK, PaperEntity.fromNative(entity), PaperBlock.fromNative(block)));
            }
            return true;
        }
        if (!isHoldingTool(player)) {
            return false;
        }
        session = manager.startSession(PaperPlayer.fromNative(player));
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

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event) {
        SessionImpl session = manager.getSession(PaperPlayer.fromNative(event.getPlayer()));
        if (session == null) {
            return;
        }
        session.handleClick(new ClickContextImpl(session.eyeRay(), ClickContext.Type.SWAP_HANDS, null, null));
        event.setCancelled(true);
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
        SessionImpl session = manager.getSession(PaperPlayer.fromNative(player));
        if (session != null) {
            if (session.isToolRequired() && !isHoldingTool(player)) {
                manager.stopSession(PaperPlayer.fromNative(player));
            }
        } else if (isHoldingTool(player)) {
            session = manager.startSession(PaperPlayer.fromNative(player));
            session.setToolRequired(true);
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

    @EventHandler
    public void onPlaceEntity(EntityPlaceEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        me.m56738.easyarmorstands.api.platform.entity.Player player = PaperPlayer.fromNative(event.getPlayer());
        Entity entity = event.getEntity();
        Bukkit.getScheduler().runTask(plugin, () -> {
            History history = EasyArmorStandsPlugin.getInstance().getHistory(player);
            Clipboard clipboard = EasyArmorStandsPlugin.getInstance().getClipboard(player);
            Element element = plugin.entityElementProviderRegistry().getElement(PaperEntity.fromNative(entity));
            if (element != null) {
                history.push(new ElementCreateAction(plugin.platform(), plugin.getHistoryManager(), element));
                clipboard.handleAutoApply(element, player);
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

        Element element = plugin.entityElementProviderRegistry().getElement(PaperEntity.fromNative(entity));
        if (element == null) {
            return;
        }

        ElementDestroyAction action = new ElementDestroyAction(plugin.platform(), plugin.getHistoryManager(), element);
        Bukkit.getScheduler().runTask(plugin, () -> {
            if (entity.isDead()) {
                plugin.getHistory(PaperPlayer.fromNative(player)).push(action);
            }
        });
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        suppressClick.put(player, 5);
        suppressArmSwing.put(event.getPlayer(), 5);
        SessionImpl session = manager.getSession(PaperPlayer.fromNative(player));
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
    public void onQuit(PlayerQuitEvent event) {
        manager.stopSession(PaperPlayer.fromNative(event.getPlayer()));
        suppressClick.remove(event.getPlayer());
        suppressArmSwing.remove(event.getPlayer());
    }

    public void update() {
        expireEntries(suppressClick);
        expireEntries(suppressArmSwing);
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
