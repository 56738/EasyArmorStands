package me.m56738.easyarmorstands.common.editor;

import me.m56738.easyarmorstands.common.util.Util;
import org.joml.Vector3dc;

class ZeroOffsetProvider implements OffsetProvider {
    static final ZeroOffsetProvider INSTANCE = new ZeroOffsetProvider();

    private ZeroOffsetProvider() {
    }

    @Override
    public Vector3dc getOffset() {
        return Util.ZERO;
    }
}
