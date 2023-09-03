package me.m56738.easyarmorstands.group.property;

import me.m56738.easyarmorstands.api.property.PendingChange;

import java.util.List;

public class GroupPendingChange implements PendingChange {
    private final List<PendingChange> changes;

    public GroupPendingChange(List<PendingChange> changes) {
        this.changes = changes;
    }

    @Override
    public boolean execute() {
        boolean changed = false;
        for (PendingChange change : changes) {
            if (change.execute()) {
                changed = true;
            }
        }
        return changed;
    }
}
