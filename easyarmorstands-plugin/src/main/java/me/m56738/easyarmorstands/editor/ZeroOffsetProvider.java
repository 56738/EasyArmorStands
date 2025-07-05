package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import me.m56738.easyarmorstands.util.Util;

class ZeroOffsetProvider implements OffsetProvider {
    static final ZeroOffsetProvider INSTANCE = new ZeroOffsetProvider();

    private ZeroOffsetProvider() {
    }

    @Override
    public Vector3dc getOffset() {
        return Util.ZERO;
    }
}
