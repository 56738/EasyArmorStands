package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.node.DefaultEntityNode;
import me.m56738.easyarmorstands.node.SimpleButton;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Entity;
import org.joml.Vector3dc;

public class DefaultEntityButton extends SimpleButton {
    private final Session session;
    private final Entity entity;

    public DefaultEntityButton(Session session, Entity entity) {
        super(session, NamedTextColor.WHITE);
        this.session = session;
        this.entity = entity;
    }

    @Override
    protected Vector3dc getPosition() {
        return Util.toVector3d(entity.getLocation());
    }

    @Override
    public Component getName() {
        return Component.text(Util.getId(entity.getUniqueId()));
    }

    @Override
    public DefaultEntityNode createNode() {
        if (!session.canSelectEntity(entity)) {
            return null;
        }
        return new DefaultEntityNode(session, entity);
    }
}
