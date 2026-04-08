package me.m56738.easyarmorstands.menu.layout;

import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuButtonCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.jspecify.annotations.Nullable;

@FunctionalInterface
public interface MenuLayoutRule {
    static MenuLayoutRule background(MenuButton button) {
        return new BackgroundRule(button);
    }

    static MenuLayoutRule equipmentSlot(EquipmentSlot slot) {
        return new EquipmentSlotRule(slot);
    }

    static MenuLayoutRule category(MenuButtonCategory category) {
        return b -> b.category().equals(category);
    }

    static MenuLayoutRule matchAll() {
        return _ -> true;
    }

    boolean matches(MenuButton button);

    default @Nullable MenuButton fallback() {
        return null;
    }
}
