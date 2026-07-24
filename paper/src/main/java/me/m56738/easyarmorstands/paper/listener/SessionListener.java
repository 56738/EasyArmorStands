package me.m56738.easyarmorstands.paper.listener;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.layer.ElementSelectionLayer;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementDiscoverySource;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.editor.layer.EntityElementDiscoverySource;
import me.m56738.easyarmorstands.history.action.ElementCreateAction;
import me.m56738.easyarmorstands.history.action.ElementDestroyAction;
import me.m56738.easyarmorstands.platform.paper.block.PaperBlock;
import me.m56738.easyarmorstands.platform.paper.entity.PaperEntity;
import me.m56738.easyarmorstands.platform.paper.entity.PaperPlayer;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemStack;
import me.m56738.easyarmorstands.session.SessionImpl;
import me.m56738.easyarmorstands.session.SessionManagerImpl;
import org.bukkit.block.Block;
import org.bukkit.block.Crafter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.CrafterCraftEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerHideEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerShowEntityEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SessionListener implements Listener {
    private final EasyArmorStandsCommon eas;
    private final SessionManagerImpl manager;
    private final Map<Player, Integer> suppressClick = new HashMap<>();
    private final Map<Player, Integer> suppressArmSwing = new HashMap<>();

    public SessionListener(EasyArmorStandsCommon eas) {
        this.eas = eas;
        this.manager = eas.sessionManager();
    }

    private boolean handleClick(Player player, ClickContext.Type type, @Nullable Entity entity, @Nullable Block block) {
        if (!suppressClick.containsKey(player)) {
            PaperEntity paperEntity = entity != null ? PaperEntity.fromNative(entity) : null;
            PaperBlock paperBlock = block != null ? PaperBlock.fromNative(block) : null;
            return eas.handleClick(PaperPlayer.fromNative(player), type, paperEntity, paperBlock);
        }
        return true;
    }

    public boolean handleLeftClick(Player player, @Nullable Entity entity, @Nullable Block block) {
        return handleClick(player, ClickContext.Type.LEFT_CLICK, entity, block);
    }

    private boolean handleRightClick(Player player, @Nullable Entity entity, @Nullable Block block) {
        return handleClick(player, ClickContext.Type.RIGHT_CLICK, entity, block);
    }

    public boolean handleSwap(Player player) {
        return handleClick(player, ClickContext.Type.SWAP_HANDS, null, null);
    }

    public boolean handleDrop(Player player) {
        return handleClick(player, ClickContext.Type.DROP, null, null);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        PaperPlayer player = PaperPlayer.fromNative(event.getPlayer());
        eas.platform().getScheduler().runTask(() -> manager.updateHeldItem(player));
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent event) {
        if (handleSwap(event.getPlayer())) {
            event.setCancelled(true);
        }
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

        Block block = event.getClickedBlock();
        if (handleLeftClick(event.getPlayer(), null, block)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeftClick(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager();
        if (!(attacker instanceof Player player)) {
            return;
        }
        Entity entity = event.getEntity();
        if (handleLeftClick(player, entity, null)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeftClick(PlayerAnimationEvent event) {
        PlayerAnimationType type = event.getAnimationType();
        if (type != PlayerAnimationType.ARM_SWING) {
            return;
        }

        eas.platform().getScheduler().runTask(() -> {
            if (!suppressArmSwing.containsKey(event.getPlayer())) {
                handleLeftClick(event.getPlayer(), null, null);
            }
        });
    }

    @EventHandler
    public void onHoldItem(PlayerItemHeldEvent event) {
        PaperPlayer player = PaperPlayer.fromNative(event.getPlayer());
        eas.platform().getScheduler().runTask(() -> manager.updateHeldItem(player));
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        PaperPlayer paperPlayer = PaperPlayer.fromNative(player);
        eas.platform().getScheduler().runTask(() -> manager.updateHeldItem(paperPlayer));
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }
        PaperPlayer paperPlayer = PaperPlayer.fromNative(player);
        eas.platform().getScheduler().runTask(() -> manager.updateHeldItem(paperPlayer));
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        if (handleRightClick(event.getPlayer(), null, block)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if (handleRightClick(player, entity, null)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClickAtEntity(PlayerInteractAtEntityEvent event) {
        onRightClick(event);
    }

    @EventHandler
    public void onEntityPlace(EntityPlaceEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }
        eas.platform().getScheduler().runTask(() -> {
            EasPlayer context = new EasPlayer(eas, PaperPlayer.fromNative(player));
            Element element = eas.getElement(PaperEntity.fromNative(event.getEntity()));
            if (element != null) {
                context.history().push(new ElementCreateAction(eas, element));
                context.clipboard().handleAutoApply(element);
            }
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDestroyEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        if (!(damager instanceof Player player)) {
            return;
        }
        Entity entity = event.getEntity();

        Element element = eas.getElement(PaperEntity.fromNative(entity));
        if (element == null) {
            return;
        }

        ElementDestroyAction action = new ElementDestroyAction(eas, element);
        eas.platform().getScheduler().runTask(() -> {
            if (entity.isDead()) {
                eas.getHistory(PaperPlayer.fromNative(player)).push(action);
            }
        });
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        suppressClick.put(player, 5);
        suppressArmSwing.put(event.getPlayer(), 5);
        if (handleDrop(player)) {
            event.setCancelled(true);
        } else {
            PaperPlayer paperPlayer = PaperPlayer.fromNative(player);
            eas.platform().getScheduler().runTask(() -> manager.updateHeldItem(paperPlayer));
        }
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        if (eas.sessionToolProvider().isTool(PaperItemStack.fromNative(event.getFuel()))) {
            event.setBurnTime(0);
        }
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        for (ItemStack item : event.getInventory().getMatrix()) {
            if (eas.sessionToolProvider().isTool(PaperItemStack.fromNative(item))) {
                event.getInventory().setResult(null);
                break;
            }
        }
    }

    @EventHandler
    public void onCrafterCraft(CrafterCraftEvent event) {
        if (event.getBlock().getState(false) instanceof Crafter crafter) {
            for (ItemStack item : crafter.getInventory()) {
                if (eas.sessionToolProvider().isTool(PaperItemStack.fromNative(item))) {
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        manager.stopSession(PaperPlayer.fromNative(player));
        suppressClick.remove(player);
        suppressArmSwing.remove(player);
    }

    public void update() {
        expireEntries(suppressClick);
        expireEntries(suppressArmSwing);

        for (SessionImpl session : new ArrayList<>(manager.getAllSessions())) {
            manager.updateHeldItem(session);
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

    private void onVisibilityChanged(Player player, Entity entity) {
        eas.platform().getScheduler().runTask(() -> updateEntityVisibility(player, entity));
    }

    @EventHandler
    public void onShow(PlayerShowEntityEvent event) {
        onVisibilityChanged(event.getPlayer(), event.getEntity());
    }

    @EventHandler
    public void onHide(PlayerHideEntityEvent event) {
        onVisibilityChanged(event.getPlayer(), event.getEntity());
    }

    private void updateEntityVisibility(Player player, Entity entity) {
        SessionImpl session = manager.getSession(PaperPlayer.fromNative(player));
        if (session == null) {
            return;
        }
        ElementSelectionLayer layer = session.findLayer(ElementSelectionLayer.class);
        if (layer == null) {
            return;
        }
        for (ElementDiscoverySource source : layer.getSources()) {
            if (source instanceof EntityElementDiscoverySource entitySource) {
                layer.refreshEntry(entitySource.getEntry(PaperEntity.fromNative(entity)));
            }
        }
    }
}
