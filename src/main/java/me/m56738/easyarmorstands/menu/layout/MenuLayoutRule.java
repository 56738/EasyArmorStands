package me.m56738.easyarmorstands.menu.layout;

import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuButtonCategory;
import me.m56738.easyarmorstands.menu.button.MenuButtonFactory;
import me.m56738.easyarmorstands.platform.inventory.EquipmentSlot;
import org.jspecify.annotations.Nullable;

@FunctionalInterface
public interface MenuLayoutRule {
    static MenuLayoutRule background(MenuButtonFactory button) {
        return new BackgroundRule(button);
    }

    static MenuLayoutRule equipmentSlot(EquipmentSlot slot) {
        return new EquipmentSlotRule(slot);
    }

    static MenuLayoutRule category(MenuButtonCategory category) {
        return b -> b.category().equals(category);
    }

    static MenuLayoutRule matchAll() {
        return b -> true;
    }

    boolean matches(MenuButton button);

    default @Nullable MenuButtonFactory fallback() {
        return null;
    }
}
