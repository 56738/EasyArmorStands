package me.m56738.easyarmorstands.session.v1_19_4;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.display.DisplayAddon;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.event.SessionInitializeEvent;
import me.m56738.easyarmorstands.event.SessionSpawnMenuBuildEvent;
import me.m56738.easyarmorstands.menu.builder.MenuBuilder;
import me.m56738.easyarmorstands.menu.slot.SpawnSlot;
import me.m56738.easyarmorstands.node.v1_19_4.DisplayButtonProvider;
import me.m56738.easyarmorstands.node.v1_19_4.DisplayRootNode;
import me.m56738.easyarmorstands.node.v1_19_4.DisplayRootNodeFactory;
import me.m56738.easyarmorstands.node.v1_19_4.ItemDisplayRootNode;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTeleportEvent;


public class DisplaySessionListener implements Listener {
    private final DisplayAddon addon;

    public DisplaySessionListener(DisplayAddon addon) {
        this.addon = addon;
    }

    @EventHandler
    public void onInitialize(SessionInitializeEvent event) {
        register(event.getSession(), ItemDisplay.class, ItemDisplayRootNode::new);
        register(event.getSession(), BlockDisplay.class, DisplayRootNode::new);
        register(event.getSession(), TextDisplay.class, DisplayRootNode::new);
    }

    @EventHandler
    public void onSpawnMenuBuild(SessionSpawnMenuBuildEvent event) {
        MenuBuilder builder = event.getBuilder();
        Session session = event.getSession();
        Player player = event.getPlayer();
        EntityTypeCapability entityTypeCapability = EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class);
        if (player.hasPermission("easyarmorstands.spawn.itemdisplay")) {
            builder.addButton(new SpawnSlot(session,
                    new DisplaySpawner<>(ItemDisplay.class, EntityType.ITEM_DISPLAY, addon.getMapper()),
                    Util.createItem(
                            ItemType.STICK,
                            entityTypeCapability.getName(EntityType.ITEM_DISPLAY))));
        }
        if (player.hasPermission("easyarmorstands.spawn.blockdisplay")) {
            builder.addButton(new SpawnSlot(session,
                    new DisplaySpawner<>(BlockDisplay.class, EntityType.BLOCK_DISPLAY, addon.getMapper()),
                    Util.createItem(
                            ItemType.STONE,
                            entityTypeCapability.getName(EntityType.BLOCK_DISPLAY))));
        }
        if (player.hasPermission("easyarmorstands.spawn.textdisplay")) {
            builder.addButton(new SpawnSlot(session,
                    new TextDisplaySpawner(addon.getMapper()),
                    Util.createItem(
                            ItemType.NAME_TAG,
                            entityTypeCapability.getName(EntityType.TEXT_DISPLAY))));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSpawn(EntitySpawnEvent event) {
        if (event.getEntity().hasMetadata("easyarmorstands_force")) {
            event.setCancelled(false);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTeleport(EntityTeleportEvent event) {
        if (event.getEntity().hasMetadata("easyarmorstands_force")) {
            event.setCancelled(false);
        }
    }

    private <T extends Display> void register(Session session, Class<T> type, DisplayRootNodeFactory<T> factory) {
        session.addProvider(new DisplayButtonProvider<>(type, addon, factory));
    }
}
