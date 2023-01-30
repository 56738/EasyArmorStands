package me.m56738.easyarmorstands.core.inventory;

import me.m56738.easyarmorstands.core.platform.EasArmorStand;
import me.m56738.easyarmorstands.core.platform.EasMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Arrays;

public class ToggleLockSlot extends ToggleSlot {
    private final SessionMenu menu;

    public ToggleLockSlot(SessionMenu menu) {
        super(
                menu,
                EasMaterial.IRON_BARS,
                Component.text("Toggle lock", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Changes whether the", NamedTextColor.GRAY),
                        Component.text("equipment can be changed", NamedTextColor.GRAY),
                        Component.text("by right clicking.", NamedTextColor.GRAY)
                )
        );
        this.menu = menu;
    }

    @Override
    protected Component getValue() {
        return menu.getSession().getEntity().isLocked()
                ? Component.text("locked", NamedTextColor.GREEN)
                : Component.text("unlocked", NamedTextColor.RED);
    }

    @Override
    protected void onClick() {
        EasArmorStand entity = menu.getSession().getEntity();
        entity.setLocked(!entity.isLocked());
    }
}
