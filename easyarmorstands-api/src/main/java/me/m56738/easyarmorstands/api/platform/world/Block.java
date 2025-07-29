package me.m56738.easyarmorstands.api.platform.world;

import me.m56738.easyarmorstands.api.platform.entity.display.Brightness;

public interface Block {
    BlockData getBlockData();

    Brightness getBrightness();
}
