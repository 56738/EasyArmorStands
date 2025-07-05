package me.m56738.easyarmorstands.group;

import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import org.jetbrains.annotations.NotNull;

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
