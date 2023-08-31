package me.m56738.easyarmorstands.api.group;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public interface GroupMember {
    @Contract(pure = true)
    @NotNull Element getElement();

    @Contract(pure = true)
    @NotNull Vector3dc getPosition();

    default @Nullable GroupMoveTool move() {
        return null;
    }

    default @Nullable GroupMoveAxisTool move(@NotNull Axis axis) {
        GroupMoveTool moveTool = move();
        if (moveTool != null) {
            return new SimpleMoveAxisTool(moveTool, axis);
        }
        return null;
    }

    default @Nullable GroupRotateTool rotate(@NotNull Vector3dc anchor, @NotNull Axis axis) {
        return null;
    }

    @Contract(pure = true)
    default @NotNull BoundingBox getBoundingBox() {
        return BoundingBox.of(getPosition());
    }

    void commit();

    @Contract(pure = true)
    boolean isValid();
}
