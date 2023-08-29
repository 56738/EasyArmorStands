package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.editor.armorstand.button.ArmorStandButton;
import me.m56738.easyarmorstands.editor.armorstand.node.ArmorStandRootNode;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import org.bukkit.entity.ArmorStand;

public class ArmorStandElement extends SimpleEntityElement<ArmorStand> {
    private final ArmorStand entity;

    public ArmorStandElement(ArmorStand entity, SimpleEntityElementType<ArmorStand> type) {
        super(entity, type);
        this.entity = entity;
    }

    @Override
    public Button createButton(Session session) {
        return new ArmorStandButton(session, entity);
    }

    @Override
    public ElementNode createNode(Session session) {
        return new ArmorStandRootNode(session, entity, this);
    }
}
