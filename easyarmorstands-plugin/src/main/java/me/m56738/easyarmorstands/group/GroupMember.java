package me.m56738.easyarmorstands.group;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.util.BoundingBox;

import java.util.Objects;

public class GroupMember {
    private final Session session;
    private final EditableElement element;

    public GroupMember(Session session, EditableElement element) {
        this.session = session;
        this.element = element;
    }

    public EditableElement getElement() {
        return element;
    }

    public BoundingBox getBoundingBox() {
        return element.getBoundingBox();
    }

    public boolean isValid() {
        return element.isValid();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMember that = (GroupMember) o;
        return Objects.equals(session, that.session) && Objects.equals(element, that.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, element);
    }
}
