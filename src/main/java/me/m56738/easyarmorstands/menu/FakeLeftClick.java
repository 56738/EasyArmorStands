package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuClickInterceptor;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4dc;

import java.util.function.Consumer;

public class FakeLeftClick implements MenuClick {
    private final Menu menu;
    private final MenuSlot slot;
    private final int index;
    private final EasPlayer player;

    public FakeLeftClick(Menu menu, int index, Player player) {
        this.menu = menu;
        this.slot = menu.getSlot(index);
        this.index = index;
        this.player = new EasPlayer(player);
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
        return player.get();
    }

    @Override
    public @Nullable Session session() {
        return player.session();
    }

    @Override
    public Matrix4dc eyeMatrix() {
        return Util.toMatrix4d(player.get().getEyeLocation());
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
        return player;
    }
}
