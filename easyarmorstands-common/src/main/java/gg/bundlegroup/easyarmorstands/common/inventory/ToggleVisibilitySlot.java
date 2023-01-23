package gg.bundlegroup.easyarmorstands.common.inventory;

import gg.bundlegroup.easyarmorstands.common.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.common.platform.EasMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Arrays;

public class ToggleVisibilitySlot extends ToggleSlot {
    private final SessionMenu menu;

    public ToggleVisibilitySlot(SessionMenu menu) {
        super(
                menu,
                EasMaterial.INVISIBILITY_POTION,
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
        return menu.getSession().getEntity().isVisible()
                ? Component.text("visible", NamedTextColor.GREEN)
                : Component.text("invisible", NamedTextColor.RED);
    }

    @Override
    protected void onClick() {
        EasArmorStand entity = menu.getSession().getEntity();
        entity.setVisible(!entity.isVisible());
    }
}
