package me.m56738.easyarmorstands.menu;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface MenuCloseListener {
    void onClose(@NotNull Player player, @NotNull Menu menu);
}
