package me.m56738.easyarmorstands.api.menu;

import me.m56738.easyarmorstands.api.editor.Session;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.identity.Identity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4dc;

import java.util.Locale;
import java.util.function.Consumer;

@ApiStatus.NonExtendable
public interface MenuClick extends ForwardingAudience.Single {
    Menu menu();

    MenuSlot slot();

    int index();

    Player player();

    @Nullable Session session();

    Matrix4dc eyeMatrix();

    default Locale locale() {
        return getOrDefault(Identity.LOCALE, Locale.US);
    }

    ItemStack cursor();

    void allow();

    void open(Inventory inventory);

    void close();

    void updateItem();

    void updateItem(MenuSlot slot);

    void queueTask(Runnable task);

    void queueTask(Consumer<ItemStack> task);

    void interceptNextClick(MenuClickInterceptor interceptor);

    boolean isLeftClick();

    boolean isRightClick();

    boolean isShiftClick();
}
