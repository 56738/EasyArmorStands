package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.capability.item.ItemType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;

import java.util.Arrays;

public class ToggleMarkerSlot extends ToggleSlot {
    private final ArmorStandMenu menu;

    public ToggleMarkerSlot(ArmorStandMenu menu) {
        super(
                menu,
                ItemType.SUNFLOWER,
                Component.text("Toggle marker", NamedTextColor.BLUE), Arrays.asList(
                        Component.text("Changes whether the", NamedTextColor.GRAY),
                        Component.text("armor stand is a marker", NamedTextColor.GRAY),
                        Component.text("with zero size and without", NamedTextColor.GRAY),
                        Component.text("collision or interaction.", NamedTextColor.GRAY)
                )
        );
        this.menu = menu;
    }

    @Override
    protected Component getValue() {
        return menu.getEntity().isMarker()
                ? Component.text("a marker", NamedTextColor.GREEN)
                : Component.text("not a marker", NamedTextColor.RED);
    }

    @Override
    protected void onClick() {
        ArmorStand entity = menu.getEntity();
        entity.setMarker(!entity.isMarker());
    }
}
