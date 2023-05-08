package me.m56738.easyarmorstands.session.v1_19_4;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.display.DisplayAddon;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.event.SessionInitializeEvent;
import me.m56738.easyarmorstands.event.SessionSpawnMenuInitializeEvent;
import me.m56738.easyarmorstands.menu.SpawnMenu;
import me.m56738.easyarmorstands.menu.SpawnSlot;
import me.m56738.easyarmorstands.node.v1_19_4.*;
import me.m56738.easyarmorstands.session.EntityButtonPriority;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.*;
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
        register(event.getSession(), Display.class, DisplayRootNode::new, EntityButtonPriority.LOW);
        register(event.getSession(), ItemDisplay.class, ItemDisplayRootNode::new, EntityButtonPriority.NORMAL);
        register(event.getSession(), BlockDisplay.class, BlockDisplayRootNode::new, EntityButtonPriority.NORMAL);
    }

    @EventHandler
    public void onSpawnMenuInitialize(SessionSpawnMenuInitializeEvent event) {
        SpawnMenu menu = event.getMenu();
        Session session = menu.getSession();
        menu.addButton(new SpawnSlot<>(menu,
                new ItemDisplaySpawner(session, addon, ItemDisplayRootNode::new),
                Util.createItem(
                        ItemType.STICK,
                        EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class).getName(EntityType.ITEM_DISPLAY),
                        Collections.emptyList())));
        menu.addButton(new SpawnSlot<>(menu,
                new DisplaySpawner<>(BlockDisplay.class, session, addon, BlockDisplayRootNode::new),
                Util.createItem(
                        ItemType.STONE,
                        EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class).getName(EntityType.BLOCK_DISPLAY),
                        Collections.emptyList())));
        menu.addButton(new SpawnSlot<>(menu,
                new DisplaySpawner<>(TextDisplay.class, session, addon, DisplayRootNode::new),
                Util.createItem(
                        ItemType.NAME_TAG,
                        EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class).getName(EntityType.TEXT_DISPLAY),
                        Collections.emptyList())));
    }

    private <T extends Display> void register(Session session, Class<T> type, DisplayRootNodeFactory<T> factory, EntityButtonPriority priority) {
        session.addProvider(new DisplayButtonProvider<>(type, addon, factory, priority));
    }
}
