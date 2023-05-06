package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.node.EntityNode;
import me.m56738.easyarmorstands.node.MenuNode;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;

public class DisplayRootNode extends MenuNode implements EntityNode {
    private final Display entity;

    public DisplayRootNode(Session session, Component name, Display entity) {
        super(session, name);
        this.entity = entity;
    }

    @Override
    public Display getEntity() {
        return entity;
    }

    @Override
    public boolean isValid() {
        return super.isValid() && entity.isValid();
    }
}
