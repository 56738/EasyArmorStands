package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.ArmorStand;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandNode extends ButtonNode {
    private final ArmorStand entity;

    public ArmorStandNode(Session session, Node node, ArmorStand entity) {
        super(session, node);
        this.entity = entity;
    }

    @Override
    protected Vector3dc getPosition() {
        Vector3d position = Util.toVector3d(entity.getLocation());
        if (!entity.isMarker()) {
            double offset = 1.25;
            if (entity.isSmall()) {
                offset /= 2;
            }
            position.y += offset;
        }
        return position;
    }

    @Override
    public Component getName() {
        return Component.text(entity.getUniqueId().toString());
    }
}
