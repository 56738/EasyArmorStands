package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.bone.EntityLocationBone;
import net.kyori.adventure.text.Component;

public class SimpleEntityNode extends MenuNode implements ElementNode {
    private final Element element;

    public SimpleEntityNode(Session session, Component name, Element element) {
        super(session, name);
        this.element = element;

        EntityLocationBone bone = new EntityLocationBone(session.properties(element));
        setRoot(true);
        addPositionButtons(session, bone, 3);
        addCarryButtonWithYaw(session, bone);
    }

    @Override
    public boolean isValid() {
        return element.isValid();
    }

    @Override
    public Element getElement() {
        return element;
    }
}
