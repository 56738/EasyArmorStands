package me.m56738.easyarmorstands.display.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.display.bone.DisplayBoxBone;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class DisplayBoxNode extends DisplayMenuNode implements ResettableNode {

    private final DisplayBoxBone bone;

    public DisplayBoxNode(Session session, PropertyContainer properties) {
        super(session, Component.text("Bounding box", NamedTextColor.GOLD), properties);
        this.bone = new DisplayBoxBone(properties);
        setShowBoundingBoxIfInactive(true); // bounding box should remain visible while a tool node is active
        addPositionButtons(session, bone, 3);
        addScaleButtons(session, bone, 3);
        addCarryButton(session, bone);
    }

    @Override
    public void reset() {
        bone.resetPosition();
        bone.commit();
    }
}
