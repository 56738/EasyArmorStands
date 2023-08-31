package me.m56738.easyarmorstands.group;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.group.GroupMember;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Group {
    private final Session session;
    private final Set<GroupMember> members = new LinkedHashSet<>();

    public Group(Session session) {
        this.session = session;
    }

    public void addMember(GroupMember member) {
        members.add(member);
    }

    public void update() {
        members.removeIf(m -> !m.isValid());
    }

    public @UnmodifiableView @NotNull Set<GroupMember> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    public @NotNull Session getSession() {
        return session;
    }

    public Vector3dc getAveragePosition() {
        Vector3d position = new Vector3d();
        for (GroupMember member : members) {
            position.add(member.getPosition());
        }
        position.div(members.size());
        return position;
    }

    public void commit() {
        for (GroupMember member : members) {
            member.commit();
        }
    }

    public boolean isValid() {
        return !members.isEmpty();
    }
}
