package me.m56738.easyarmorstands.session.v1_19_4;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.display.DisplayAddon;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.event.SessionInitializeEvent;
import me.m56738.easyarmorstands.event.SessionSpawnMenuInitializeEvent;
import me.m56738.easyarmorstands.menu.SpawnMenu;
import me.m56738.easyarmorstands.menu.SpawnSlot;
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
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Collections;


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
    public void onSpawnMenuInitialize(SessionSpawnMenuInitializeEvent event) {
        SpawnMenu menu = event.getMenu();
        Session session = menu.getSession();
        if (session.getPlayer().hasPermission("easyarmorstands.spawn.itemdisplay")) {
            menu.addButton(new SpawnSlot<>(menu,
                    new ItemDisplaySpawner(),
                    Util.createItem(
                            ItemType.STICK,
                            EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class).getName(EntityType.ITEM_DISPLAY),
                            Collections.emptyList())));
        }
        if (session.getPlayer().hasPermission("easyarmorstands.spawn.blockdisplay")) {
            menu.addButton(new SpawnSlot<>(menu,
                    new DisplaySpawner<>(BlockDisplay.class, EntityType.BLOCK_DISPLAY),
                    Util.createItem(
                            ItemType.STONE,
                            EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class).getName(EntityType.BLOCK_DISPLAY),
                            Collections.emptyList())));
        }
        if (session.getPlayer().hasPermission("easyarmorstands.spawn.textdisplay")) {
            menu.addButton(new SpawnSlot<>(menu,
                    new DisplaySpawner<>(TextDisplay.class, EntityType.TEXT_DISPLAY),
                    Util.createItem(
                            ItemType.NAME_TAG,
                            EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class).getName(EntityType.TEXT_DISPLAY),
                            Collections.emptyList())));
        }
    }

    private <T extends Display> void register(Session session, Class<T> type, DisplayRootNodeFactory<T> factory) {
        session.addProvider(new DisplayButtonProvider<>(type, addon, factory));
    }
}
