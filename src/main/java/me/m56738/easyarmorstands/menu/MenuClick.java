package me.m56738.easyarmorstands.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public interface MenuClick {
    int slot();

    Player player();

    void cancel();

    void close();

    void updateItem();

    void queueTask(Runnable task);

    void queueTask(Consumer<ItemStack> task);

    boolean isLeftClick();

    class FakeLeftClick implements MenuClick {
        private final int slot;
        private final Player player;

        public FakeLeftClick(int slot, Player player) {
            this.slot = slot;
            this.player = player;
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
    }
}
