package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface ToolContext {
    static ToolContext of(PositionProvider positionProvider, RotationProvider rotationProvider) {
        return new ToolContextImpl(positionProvider, rotationProvider);
    }

    PositionProvider position();

    RotationProvider rotation();

    default ToolContext withPosition(PositionProvider position) {
        return of(position, rotation());
    }

    default ToolContext withRotation(RotationProvider rotation) {
        return of(position(), rotation);
    }
}
