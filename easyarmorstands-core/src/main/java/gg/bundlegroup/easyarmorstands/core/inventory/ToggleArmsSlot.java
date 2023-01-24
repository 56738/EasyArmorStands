package gg.bundlegroup.easyarmorstands.core.inventory;

import gg.bundlegroup.easyarmorstands.core.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.core.platform.EasMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Arrays;

public class ToggleArmsSlot extends ToggleSlot {
    private final SessionMenu menu;

    public ToggleArmsSlot(SessionMenu menu) {
        super(
                menu,
                EasMaterial.STICK,
                Component.text("Toggle arms", NamedTextColor.BLUE), Arrays.asList(
                        Component.text("Changes whether the arms", NamedTextColor.GRAY),
                        Component.text("of the armor stand are", NamedTextColor.GRAY),
                        Component.text("visible.", NamedTextColor.GRAY)
                )
        );
        this.menu = menu;
    }

    @Override
    protected Component getValue() {
        return menu.getSession().getEntity().hasArms()
                ? Component.text("has arms", NamedTextColor.GREEN)
                : Component.text("has no arms", NamedTextColor.RED);
    }

    @Override
    protected void onClick() {
        EasArmorStand entity = menu.getSession().getEntity();
        entity.setArms(!entity.hasArms());
    }
}
