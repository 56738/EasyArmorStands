package me.m56738.easyarmorstands.api.menu;

import org.jspecify.annotations.Nullable;

public interface MenuAction {
    @Nullable
    Runnable getAction();

    void setAction(@Nullable Runnable action);

    boolean isSubmit();

    void setSubmit(boolean submit);
}
