package gg.bundlegroup.easyarmorstands.common.command;

import gg.bundlegroup.easyarmorstands.common.util.Util;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public enum AlignAxis {
    X(true, false, false),
    Y(false, true, false),
    Z(false, false, true),
    XZ(true, false, true),
    ALL(true, true, true);

    private final boolean x;
    private final boolean y;
    private final boolean z;

    AlignAxis(boolean x, boolean y, boolean z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean x() {
        return x;
    }

    public boolean y() {
        return y;
    }

    public boolean z() {
        return z;
    }

    public Vector3d snap(Vector3dc value, double increment, Vector3dc offset, Vector3d dest) {
        if (x) {
            dest.x = Util.snap(value.x() - offset.x(), increment) + offset.x();
        } else {
            dest.x = value.x();
        }
        if (y) {
            dest.y = Util.snap(value.y() - offset.y(), increment) + offset.y();
        } else {
            dest.y = value.y();
        }
        if (z) {
            dest.z = Util.snap(value.z() - offset.z(), increment) + offset.z();
        } else {
            dest.z = value.z();
        }
        return dest;
    }
}
