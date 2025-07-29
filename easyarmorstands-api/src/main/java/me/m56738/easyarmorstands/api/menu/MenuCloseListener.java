package me.m56738.easyarmorstands.api.menu;

import me.m56738.easyarmorstands.api.platform.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface MenuCloseListener {
    void onClose(@NotNull Player player, @NotNull Menu menu);
}
