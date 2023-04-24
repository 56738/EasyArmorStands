package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.capability.item.ItemType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;

import java.util.Arrays;

public class ToggleVisibilitySlot extends ToggleSlot {
    private final ArmorStandMenu menu;

    public ToggleVisibilitySlot(ArmorStandMenu menu) {
        super(
                menu,
                ItemType.INVISIBILITY_POTION,
                Component.text("Toggle visibility", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Changes whether the", NamedTextColor.GRAY),
                        Component.text("armor stand is visible.", NamedTextColor.GRAY),
                        Component.text("Items are always visible.", NamedTextColor.GRAY)
                )
        );
        this.menu = menu;
    }

    @Override
    protected Component getValue() {
        return menu.getEntity().isVisible()
                ? Component.text("visible", NamedTextColor.GREEN)
                : Component.text("invisible", NamedTextColor.RED);
    }

    @Override
    protected void onClick() {
        ArmorStand entity = menu.getEntity();
        entity.setVisible(!entity.isVisible());
    }
}
