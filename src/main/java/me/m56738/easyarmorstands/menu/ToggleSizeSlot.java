package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.capability.item.ItemType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;

import java.util.Arrays;

public class ToggleSizeSlot extends ToggleSlot {
    private final SessionMenu menu;

    public ToggleSizeSlot(SessionMenu menu) {
        super(
                menu,
                ItemType.BONE_MEAL,
                Component.text("Toggle size", NamedTextColor.BLUE), Arrays.asList(
                        Component.text("Changes the size of", NamedTextColor.GRAY),
                        Component.text("the armor stand.", NamedTextColor.GRAY)
                )
        );
        this.menu = menu;
    }

    @Override
    protected Component getValue() {
        return menu.getSession().getEntity().isSmall()
                ? Component.text("small", NamedTextColor.RED)
                : Component.text("large", NamedTextColor.GREEN);
    }

    @Override
    protected void onClick() {
        ArmorStand entity = menu.getSession().getEntity();
        entity.setSmall(!entity.isSmall());
    }
}
