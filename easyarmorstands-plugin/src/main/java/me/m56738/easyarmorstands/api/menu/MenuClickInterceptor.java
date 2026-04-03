package me.m56738.easyarmorstands.api.menu;

import org.jetbrains.annotations.NotNull;

public interface MenuClickInterceptor {
    void interceptClick(@NotNull MenuClick click);
}
