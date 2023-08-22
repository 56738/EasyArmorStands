package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import net.kyori.adventure.text.Component;
import org.bukkit.util.EulerAngle;

public class ArmorStandPartNode extends PropertyMenuNode implements ResettableNode {
    private final ArmorStandPart part;

    public ArmorStandPartNode(Session session, Component name, PropertyContainer container, ArmorStandPart part) {
        super(session, name, container);
        this.part = part;
    }

    @Override
    public void reset() {
        properties().get(ArmorStandPropertyTypes.POSE.get(part)).setValue(EulerAngle.ZERO);
        properties().commit();
    }
}
