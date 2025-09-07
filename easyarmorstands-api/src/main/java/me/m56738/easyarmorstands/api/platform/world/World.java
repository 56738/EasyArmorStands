package me.m56738.easyarmorstands.api.platform.world;

import me.m56738.easyarmorstands.api.platform.PlatformHolder;
import org.joml.RoundingMode;
import org.joml.Vector3dc;
import org.joml.Vector3i;
import org.joml.Vector3ic;

public interface World extends PlatformHolder {
    Block getBlock(Vector3ic position);

    default Block getBlock(Vector3dc position) {
        return getBlock(new Vector3i(position, RoundingMode.TRUNCATE));
    }
}
