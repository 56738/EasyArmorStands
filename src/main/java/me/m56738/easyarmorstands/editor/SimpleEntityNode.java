package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.bone.EntityLocationBone;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.node.EditableObjectNode;
import me.m56738.easyarmorstands.node.EntityMenuNode;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.Entity;

public class SimpleEntityNode extends EntityMenuNode implements EditableObjectNode {
    private final EditableObject editableObject;

    public SimpleEntityNode(Session session, EditableObject editableObject, Entity entity) {
        super(session, EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class).getName(entity.getType()), entity);
        this.editableObject = editableObject;

        EntityLocationBone bone = new EntityLocationBone(session.properties(editableObject));
        setRoot(true);
        addPositionButtons(session, bone, 3);
        addCarryButtonWithYaw(session, bone);
    }

    @Override
    public EditableObject getEditableObject() {
        return editableObject;
    }
}
