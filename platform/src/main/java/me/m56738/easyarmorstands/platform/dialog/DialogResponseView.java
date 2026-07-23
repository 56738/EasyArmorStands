package me.m56738.easyarmorstands.platform.dialog;

import org.jspecify.annotations.Nullable;

public interface DialogResponseView {
    @Nullable Boolean getBoolean(String key);

    @Nullable String getText(String key);
}
