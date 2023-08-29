package me.m56738.easyarmorstands.editor;

import org.joml.Vector3dc;

public interface OffsetProvider {
    static OffsetProvider zero() {
        return ZeroOffsetProvider.INSTANCE;
    }

    Vector3dc getOffset();
}
