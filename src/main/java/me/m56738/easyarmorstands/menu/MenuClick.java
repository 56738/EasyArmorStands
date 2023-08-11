package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface MenuClick extends ForwardingAudience.Single {
    int slot();

    Player player();

    void cancel();

    void open(Inventory inventory);

    default void open(InventoryHolder holder) {
        open(holder.getInventory());
    }

    void close();

    void updateItem();

    void queueTask(Runnable task);

    void queueTask(Consumer<ItemStack> task);

    boolean isLeftClick();

    boolean isRightClick();

    @Override
    default @NotNull Audience audience() {
        return EasyArmorStands.getInstance().getAdventure().player(player());
    }

    class FakeLeftClick implements MenuClick {
        private final int slot;
        private final Player player;
        private final Audience audience;

        public FakeLeftClick(int slot, Player player) {
            this.slot = slot;
            this.player = player;
            this.audience = EasyArmorStands.getInstance().getAdventure().player(player);
        }

        @Override
        public int slot() {
            return slot;
        }

        @Override
        public Player player() {
            return player;
        }

        @Override
        public void cancel() {
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
        public void queueTask(Runnable task) {
            task.run();
        }

        @Override
        public void queueTask(Consumer<ItemStack> task) {
            task.accept(null);
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
        public @NotNull Audience audience() {
            return audience;
        }
    }
}
