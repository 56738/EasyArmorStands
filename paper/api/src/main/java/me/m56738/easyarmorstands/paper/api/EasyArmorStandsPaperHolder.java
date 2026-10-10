package me.m56738.easyarmorstands.paper.api;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@ApiStatus.Internal
@NullMarked
public final class EasyArmorStandsPaperHolder {
    static @Nullable EasyArmorStandsPaper instance;

    private EasyArmorStandsPaperHolder() {
    }

    public static void initialize(@Nullable EasyArmorStandsPaper instance) {
        EasyArmorStandsPaperHolder.instance = instance;
    }
}
