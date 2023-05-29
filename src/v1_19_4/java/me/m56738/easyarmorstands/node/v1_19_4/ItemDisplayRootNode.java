package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.menu.EntityMenu;
import me.m56738.easyarmorstands.menu.v1_19_4.ItemDisplayMenu;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;

public class ItemDisplayRootNode extends DisplayRootNode {
    private final Session session;
    private final ItemDisplay entity;

    public ItemDisplayRootNode(Session session, Component name, ItemDisplay entity) {
        super(session, name, entity);
        this.session = session;
        this.entity = entity;
    }

    @Override
    protected EntityMenu<? extends Display> createMenu() {
        return new ItemDisplayMenu(session, entity);
    }
}
