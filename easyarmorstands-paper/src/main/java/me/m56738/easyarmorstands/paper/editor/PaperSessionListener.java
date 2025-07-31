package me.m56738.easyarmorstands.paper.editor;

import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.clipboard.Clipboard;
import me.m56738.easyarmorstands.common.editor.SessionListener;
import me.m56738.easyarmorstands.common.history.History;
import me.m56738.easyarmorstands.common.history.action.ElementCreateAction;
import me.m56738.easyarmorstands.common.history.action.ElementDestroyAction;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperEntity;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import me.m56738.easyarmorstands.paper.api.platform.inventory.PaperItem;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperBlock;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class PaperSessionListener implements Listener {
    private final Plugin plugin;
    private final EasyArmorStandsCommon eas;
    private final SessionListener sessionListener;

    public PaperSessionListener(Plugin plugin, EasyArmorStandsCommon eas, SessionListener sessionListener) {
        this.plugin = plugin;
        this.eas = eas;
        this.sessionListener = sessionListener;
    }

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event) {
        if (sessionListener.handleClick(PaperPlayer.fromNative(event.getPlayer()), ClickContext.Type.SWAP_HANDS)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.LEFT_CLICK_AIR && action != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        Player player = PaperPlayer.fromNative(event.getPlayer());
        PaperBlock block = Optional.ofNullable(event.getClickedBlock()).map(PaperBlock::fromNative).orElse(null);
        if (sessionListener.handleClick(player, ClickContext.Type.LEFT_CLICK, block)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeftClick(EntityDamageByEntityEvent event) {
        Entity attacker = PaperEntity.fromNative(event.getDamager());
        if (!(attacker instanceof Player player)) {
            return;
        }
        Entity entity = PaperEntity.fromNative(event.getEntity());
        if (sessionListener.handleClick(player, ClickContext.Type.LEFT_CLICK, entity)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = PaperPlayer.fromNative(event.getPlayer());
        PaperBlock block = Optional.ofNullable(event.getClickedBlock()).map(PaperBlock::fromNative).orElse(null);
        if (sessionListener.handleClick(player, ClickContext.Type.RIGHT_CLICK, block)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent event) {
        Player player = PaperPlayer.fromNative(event.getPlayer());
        Entity entity = PaperEntity.fromNative(event.getRightClicked());
        if (sessionListener.handleClick(player, ClickContext.Type.RIGHT_CLICK, entity)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClickAtEntity(PlayerInteractAtEntityEvent event) {
        onRightClick(event);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = PaperPlayer.fromNative(event.getPlayer());
        Bukkit.getScheduler().runTask(plugin, () -> sessionListener.updateHeldItem(player));
    }

    @EventHandler
    public void onHoldItem(PlayerItemHeldEvent event) {
        Player player = PaperPlayer.fromNative(event.getPlayer());
        Bukkit.getScheduler().runTask(plugin, () -> sessionListener.updateHeldItem(player));
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        Entity entity = PaperEntity.fromNative(event.getEntity());
        if (entity instanceof Player player) {
            Bukkit.getScheduler().runTask(plugin, () -> sessionListener.updateHeldItem(player));
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        HumanEntity entity = event.getPlayer();
        if (entity instanceof Player player) {
            Bukkit.getScheduler().runTask(plugin, () -> sessionListener.updateHeldItem(player));
        }
    }

    @EventHandler
    public void onPlaceEntity(EntityPlaceEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        Player player = PaperPlayer.fromNative(event.getPlayer());
        Entity entity = PaperEntity.fromNative(event.getEntity());
        Bukkit.getScheduler().runTask(plugin, () -> {
            History history = eas.historyManager().getHistory(player);
            Clipboard clipboard = eas.clipboardManager().getClipboard(player);
            Element element = eas.entityElementProviderRegistry().getElement(entity);
            if (element != null) {
                history.push(new ElementCreateAction(eas, element));
                clipboard.handleAutoApply(element, player);
            }
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDestroyEntity(EntityDamageByEntityEvent event) {
        Entity damager = PaperEntity.fromNative(event.getDamager());
        if (!(damager instanceof Player player)) {
            return;
        }
        Entity entity = PaperEntity.fromNative(event.getEntity());

        Element element = eas.entityElementProviderRegistry().getElement(entity);
        if (element == null) {
            return;
        }

        ElementDestroyAction action = new ElementDestroyAction(eas, element);
        Bukkit.getScheduler().runTask(plugin, () -> {
            if (entity.isDead()) {
                eas.historyManager().getHistory(player).push(action);
            }
        });
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = PaperPlayer.fromNative(event.getPlayer());
        sessionListener.suppressClick(player);
        if (sessionListener.handleDrop(player)) {
            event.setCancelled(true);
        }
        Bukkit.getScheduler().runTask(plugin, () -> sessionListener.updateHeldItem(player));
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        if (eas.platform().isTool(PaperItem.fromNative(event.getFuel()))) {
            event.setBurnTime(0);
        }
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        for (ItemStack item : event.getInventory().getMatrix()) {
            if (eas.platform().isTool(PaperItem.fromNative(item))) {
                event.getInventory().setResult(null);
                break;
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        sessionListener.handleQuit(PaperPlayer.fromNative(event.getPlayer()));
    }
}
