package me.m56738.easyarmorstands.api.menu;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.lib.joml.Matrix4dc;
import me.m56738.easyarmorstands.lib.kyori.adventure.audience.ForwardingAudience;
import me.m56738.easyarmorstands.lib.kyori.adventure.identity.Identity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

@ApiStatus.NonExtendable
public interface MenuClick extends ForwardingAudience.Single {
    @NotNull Menu menu();

    @Nullable MenuSlot slot();

    int index();

    @NotNull Player player();

    @Nullable Session session();

    @NotNull Matrix4dc eyeMatrix();

    default @NotNull Locale locale() {
        return getOrDefault(Identity.LOCALE, Locale.US);
    }

    @NotNull ItemStack cursor();

    void allow();

    void open(@NotNull Inventory inventory);

    void close();

    void updateItem();

    void updateItem(@NotNull MenuSlot slot);

    void queueTask(@NotNull Runnable task);

    void interceptNextClick(@NotNull MenuClickInterceptor interceptor);

    boolean isLeftClick();

    boolean isRightClick();

    boolean isShiftClick();
}
