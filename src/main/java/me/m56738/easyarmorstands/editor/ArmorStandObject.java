package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.node.ArmorStandButton;
import me.m56738.easyarmorstands.node.ArmorStandRootNode;
import me.m56738.easyarmorstands.node.Button;
import me.m56738.easyarmorstands.node.EditableObjectNode;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.ArmorStand;

public class ArmorStandObject extends SimpleEntityObject {
    private final Session session;
    private final ArmorStand entity;

    public ArmorStandObject(Session session, ArmorStand entity) {
        super(session, entity);
        this.session = session;
        this.entity = entity;
    }

    @Override
    public ArmorStand getEntity() {
        return entity;
    }

    @Override
    public Button createButton() {
        return new ArmorStandButton(session, entity);
    }

    @Override
    public EditableObjectNode createNode() {
        return new ArmorStandRootNode(session, entity, this);
    }
}
