package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface MenuClick extends ForwardingAudience.Single {
    Menu menu();

    MenuSlot slot();

    int index();

    Player player();

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

    @Override
    default @NotNull Audience audience() {
        return EasyArmorStands.getInstance().getAdventure().player(player());
    }

    class FakeLeftClick implements MenuClick {
        private final Menu menu;
        private final MenuSlot slot;
        private final int index;
        private final Player player;
        private final Audience audience;

        public FakeLeftClick(Menu menu, int index, Player player) {
            this.menu = menu;
            this.slot = menu.getSlot(index);
            this.index = index;
            this.player = player;
            this.audience = EasyArmorStands.getInstance().getAdventure().player(player);
        }

        @Override
        public Menu menu() {
            return menu;
        }

        @Override
        public MenuSlot slot() {
            return slot;
        }

        @Override
        public int index() {
            return index;
        }

        @Override
        public Player player() {
            return player;
        }

        @Override
        public ItemStack cursor() {
            return new ItemStack(Material.AIR);
        }

        @Override
        public void allow() {
        }

        @Override
        public void open(Inventory inventory) {
        }

        @Override
        public void close() {
        }

        @Override
        public void updateItem() {
        }

        @Override
        public void updateItem(MenuSlot slot) {
        }

        @Override
        public void queueTask(Runnable task) {
            task.run();
        }

        @Override
        public void queueTask(Consumer<ItemStack> task) {
            task.accept(null);
        }

        @Override
        public void interceptNextClick(MenuClickInterceptor interceptor) {
        }

        @Override
        public boolean isLeftClick() {
            return true;
        }

        @Override
        public boolean isRightClick() {
            return false;
        }

        @Override
        public boolean isShiftClick() {
            return false;
        }

        @Override
        public @NotNull Audience audience() {
            return audience;
        }
    }
}
