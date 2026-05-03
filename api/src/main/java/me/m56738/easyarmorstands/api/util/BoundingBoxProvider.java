package me.m56738.easyarmorstands.api.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface BoundingBoxProvider {
    @Contract(pure = true)
    @NotNull BoundingBox getBoundingBox();
}
