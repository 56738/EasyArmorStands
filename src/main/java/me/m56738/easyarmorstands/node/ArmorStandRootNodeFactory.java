package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.ArmorStand;

import java.util.function.Supplier;

public class ArmorStandRootNodeFactory implements Supplier<ArmorStandRootNode> {
    private final Session session;
    private final ArmorStand entity;

    public ArmorStandRootNodeFactory(Session session, ArmorStand entity) {
        this.session = session;
        this.entity = entity;
    }

    @Override
    public ArmorStandRootNode get() {
        return new ArmorStandRootNode(session, entity);
    }
}
