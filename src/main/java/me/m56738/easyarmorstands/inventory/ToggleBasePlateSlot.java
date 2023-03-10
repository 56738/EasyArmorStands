package me.m56738.easyarmorstands.inventory;

import me.m56738.easyarmorstands.capability.item.ItemType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;

import java.util.Arrays;

public class ToggleBasePlateSlot extends ToggleSlot {
    private final SessionMenu menu;

    public ToggleBasePlateSlot(SessionMenu menu) {
        super(
                menu,
                ItemType.STONE_SLAB,
                Component.text("Toggle base plate", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Changes whether the base", NamedTextColor.GRAY),
                        Component.text("plate of the armor stand", NamedTextColor.GRAY),
                        Component.text("is visible.", NamedTextColor.GRAY)
                )
        );
        this.menu = menu;
    }

    @Override
    protected Component getValue() {
        return menu.getSession().getEntity().hasBasePlate()
                ? Component.text("has a base plate", NamedTextColor.GREEN)
                : Component.text("has no base plate", NamedTextColor.RED);
    }

    @Override
    protected void onClick() {
        ArmorStand entity = menu.getSession().getEntity();
        entity.setBasePlate(!entity.hasBasePlate());
    }
}
