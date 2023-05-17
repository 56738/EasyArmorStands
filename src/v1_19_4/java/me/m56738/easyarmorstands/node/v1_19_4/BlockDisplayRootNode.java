package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.BlockDisplay;

public class BlockDisplayRootNode extends DisplayRootNode {
    private final BlockDisplay entity;

    public BlockDisplayRootNode(Session session, Component name, BlockDisplay entity) {
        super(session, name, entity);
        this.entity = entity;
    }

    @Override
    public BlockDisplay getEntity() {
        return entity;
    }
}
