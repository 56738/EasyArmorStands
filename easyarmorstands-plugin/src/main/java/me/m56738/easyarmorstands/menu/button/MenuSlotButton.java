package me.m56738.easyarmorstands.menu.button;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuClickInterceptor;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.menu.slot.MenuButtonSlot;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4dc;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Locale;

@NullMarked
public class MenuSlotButton implements MenuButton {
    private final MenuSlot slot;

    private MenuSlotButton(MenuSlot slot) {
        this.slot = slot;
    }

    public static MenuButton toButton(MenuSlot slot) {
        if (slot instanceof MenuButtonSlot menuButtonSlot) {
            return menuButtonSlot.getButton();
        } else {
            return new MenuSlotButton(slot);
        }
    }

    public MenuSlot getSlot() {
        return slot;
    }

    @Override
    public Material icon() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Component name() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Component> description() {
        throw new UnsupportedOperationException();
    }

    public ItemStack getItem(Locale locale) {
        return Util.wrapItem(slot.getItem(locale));
    }

    @Override
    public void onClick(MenuClickContext context) {
        slot.onClick(new MenuClick() {
            @Override
            public Menu menu() {
                throw new UnsupportedOperationException();
            }

            @Override
            public @Nullable MenuSlot slot() {
                return null;
            }

            @Override
            public int index() {
                return 0;
            }

            @Override
            public Player player() {
                return context.player();
            }

            @Override
            public @Nullable Session session() {
                return EasyArmorStandsPlugin.getInstance().sessionManager().getSession(player());
            }

            @Override
            public Matrix4dc eyeMatrix() {
                throw new UnsupportedOperationException();
            }

            @Override
            public ItemStack cursor() {
                return ItemStack.empty();
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
            }

            @Override
            public void interceptNextClick(MenuClickInterceptor interceptor) {
            }

            @Override
            public boolean isLeftClick() {
                return context.isLeftClick();
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
            public Audience audience() {
                return player();
            }
        });
    }
}
