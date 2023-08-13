package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.bone.v1_19_4.DisplayBoxBone;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class DisplayBoxNode extends DisplayMenuNode {
    public DisplayBoxNode(Session session, PropertyContainer properties) {
        super(session, Component.text("Bounding box", NamedTextColor.GOLD), properties);
        DisplayBoxBone bone = new DisplayBoxBone(properties);
        setShowBoundingBoxIfInactive(true); // bounding box should remain visible while a tool node is active
        addPositionButtons(session, bone, 3);
        addScaleButtons(session, bone, 3);
        addCarryButton(session, bone);
    }
}
