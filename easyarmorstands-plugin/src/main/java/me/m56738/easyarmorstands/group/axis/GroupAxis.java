package me.m56738.easyarmorstands.group.axis;

import me.m56738.easyarmorstands.api.editor.axis.EditorAxis;
import me.m56738.easyarmorstands.api.group.GroupTool;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class GroupAxis<T extends GroupTool> implements EditorAxis {
    private final List<T> tools;

    public GroupAxis(Collection<T> tools) {
        this.tools = new ArrayList<>(tools);
    }

    public @UnmodifiableView List<T> getTools() {
        return Collections.unmodifiableList(tools);
    }

    @Override
    public void revert() {
        for (T tool : tools) {
            tool.revert();
        }
    }

    @Override
    public void commit() {
        for (T tool : tools) {
            tool.commit();
        }
    }

    @Override
    public boolean isValid() {
        for (T tool : tools) {
            if (tool.isValid()) {
                return true;
            }
        }
        return false;
    }
}
