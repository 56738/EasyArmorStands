package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.node.ArmorStandButton;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

public class ArmorStandButtonProvider implements EntityButtonProvider {
    @Override
    public ArmorStandButton createButton(Session session, Entity entity) {
        if (!(entity instanceof ArmorStand)) {
            return null;
        }
        if (!session.getPlayer().hasPermission("easyarmorstands.edit.armorstand")) {
            return null;
        }
        ArmorStand armorStand = (ArmorStand) entity;
        return new ArmorStandButton(session, armorStand);
    }
}
