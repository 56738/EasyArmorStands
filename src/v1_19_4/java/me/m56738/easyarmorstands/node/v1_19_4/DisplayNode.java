package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.node.ButtonNode;
import me.m56738.easyarmorstands.node.EntityNode;
import me.m56738.easyarmorstands.node.Node;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.joml.Vector3dc;

public class DisplayNode extends ButtonNode implements EntityNode {
    private final Display entity;

    public DisplayNode(Session session, Node node, Display entity) {
        super(session, node);
        this.entity = entity;
    }

    @Override
    protected Vector3dc getPosition() {
        return Util.toVector3d(entity.getLocation());
    }

    @Override
    public Component getName() {
        return Component.text(entity.getUniqueId().toString());
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
