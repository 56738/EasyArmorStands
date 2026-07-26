package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.menu.click.MenuClick;
import me.m56738.easyarmorstands.menu.click.MenuClickInterceptor;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.event.callback.MenuClickCallback;
import me.m56738.easyarmorstands.platform.event.callback.MenuDragCallback;
import me.m56738.easyarmorstands.platform.inventory.Inventory;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.Nullable;

public class MenuListener implements MenuClickCallback, MenuDragCallback {
    private final EasyArmorStandsCommon eas;

    public MenuListener(EasyArmorStandsCommon eas) {
        this.eas = eas;
    }

    @Override
    public boolean onClick(Player player, Inventory inventory, int slot, ClickOptions options) {
        if (!(inventory.getHolder() instanceof Menu menu)) {
            return false;
        }

        if (slot >= menu.getSize()) {
            // Not the upper inventory
            return false;
        }

        menu.onClick(new SingleClick(eas, player, menu, slot, options));
        return true;
    }

    @Override
    public boolean onDrag(Player player, Inventory inventory) {
        // disallow dragging
        return inventory.getHolder() instanceof Menu;
    }

    private static abstract class Click implements MenuClick {
        private final Menu menu;
        private final @Nullable MenuSlot slot;
        private final int index;
        private final EasPlayer player;

        private Click(EasyArmorStandsCommon eas, Player player, Menu menu, int index) {
            this.menu = menu;
            this.slot = menu.getSlot(index);
            this.index = index;
            this.player = new EasPlayer(eas, player);
        }

        @Override
        public Menu menu() {
            return menu;
        }

        @Override
        public @Nullable MenuSlot slot() {
            return slot;
        }

        @Override
        public int index() {
            return index;
        }

        @Override
        public Player player() {
            return player.get();
        }

        @Override
        public ItemStack cursor() {
            return player.get().getItemOnCursor();
        }

        @Override
        public @Nullable Session session() {
            return player.session();
        }

        @Override
        public void close() {
            queueTask(() -> menu.close(player.get()));
        }

        @Override
        public void updateItem() {
            queueTask(() -> menu.updateItem(index));
        }

        @Override
        public void updateItem(MenuSlot slot) {
            queueTask(() -> menu.updateItem(slot));
        }

        @Override
        public void queueTask(Runnable task) {
            menu.queueTask(task);
        }

        @Override
        public void interceptNextClick(MenuClickInterceptor interceptor) {
            menu.interceptNextClick(interceptor);
        }

        @Override
        public Audience audience() {
            return player;
        }
    }

    private static class SingleClick extends Click {
        private final ClickOptions options;

        private SingleClick(EasyArmorStandsCommon eas, Player player, Menu menu, int index, ClickOptions options) {
            super(eas, player, menu, index);
            this.options = options;
        }

        @Override
        public boolean isLeftClick() {
            return options.left();
        }

        @Override
        public boolean isRightClick() {
            return options.right();
        }

        @Override
        public boolean isShiftClick() {
            return options.shift();
        }

        @Override
        public void updateItem() {
            queueTask(() -> menu().updateItem(index()));
        }
    }
}
