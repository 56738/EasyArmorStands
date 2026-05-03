package me.m56738.easyarmorstands.menu.click;

import org.jetbrains.annotations.NotNull;

public interface MenuClickInterceptor {
    void interceptClick(@NotNull MenuClick click);
}
