package me.m56738.easyarmorstands.common.group;

import me.m56738.easyarmorstands.api.util.PositionProvider;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

public class GroupPositionProvider implements PositionProvider {
    private final Group group;

    public GroupPositionProvider(Group group) {
        this.group = group;
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return group.getAveragePosition();
    }
}
