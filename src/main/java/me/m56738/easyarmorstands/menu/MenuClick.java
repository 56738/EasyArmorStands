package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public interface MenuClick extends ForwardingAudience.Single {
    Menu menu();

    MenuSlot slot();

    int index();

    EasPlayer player();

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
