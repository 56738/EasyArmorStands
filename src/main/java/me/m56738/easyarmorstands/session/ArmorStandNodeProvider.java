package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.node.ArmorStandNode;
import me.m56738.easyarmorstands.node.ArmorStandNodeFactory;
import me.m56738.easyarmorstands.node.ClickableNode;
import me.m56738.easyarmorstands.node.LazyNode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

public class ArmorStandNodeProvider implements EntityNodeProvider {
    @Override
    public ClickableNode createNode(Session session, Entity entity) {
        if (!(entity instanceof ArmorStand)) {
            return null;
        }
        ArmorStand armorStand = (ArmorStand) entity;
        return new ArmorStandNode(session, new LazyNode(new ArmorStandNodeFactory(session, armorStand)), armorStand);
    }
}
