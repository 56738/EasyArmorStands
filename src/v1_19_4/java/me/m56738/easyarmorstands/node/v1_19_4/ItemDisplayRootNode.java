package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.ItemDisplay;

public class ItemDisplayRootNode extends DisplayRootNode {
    private final ItemDisplay entity;

    public ItemDisplayRootNode(Session session, Component name, ItemDisplay entity) {
        super(session, name, entity);
        this.entity = entity;
    }

    @Override
    public ItemDisplay getEntity() {
        return entity;
    }
}
