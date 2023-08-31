package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.group.GroupMember;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An element which can be edited as part of a group.
 */
public interface GroupEditableElement extends EditableElement {
    @Contract(pure = true)
    @Nullable GroupMember createGroupMember(@NotNull Session session);
}
