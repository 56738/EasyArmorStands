package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.editor.EditableObject;
import me.m56738.easyarmorstands.node.NodeButton;

@Deprecated
@FunctionalInterface
public interface EntityButtonProvider {
    NodeButton createButton(Session session, EditableObject editableObject);

    default EntityButtonPriority getPriority() {
        return EntityButtonPriority.NORMAL;
    }
}
