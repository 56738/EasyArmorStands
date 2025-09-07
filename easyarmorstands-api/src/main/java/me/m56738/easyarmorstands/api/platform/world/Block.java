package me.m56738.easyarmorstands.api.platform.world;

import me.m56738.easyarmorstands.api.platform.PlatformHolder;
import me.m56738.easyarmorstands.api.platform.entity.display.Brightness;

public interface Block extends PlatformHolder {
    BlockData getBlockData();

    Brightness getBrightness();
}
