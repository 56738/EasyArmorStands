package me.m56738.easyarmorstands.core.inventory;

import me.m56738.easyarmorstands.core.platform.EasArmorStand;
import me.m56738.easyarmorstands.core.platform.EasMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Arrays;

public class ToggleSizeSlot extends ToggleSlot {
    private final SessionMenu menu;

    public ToggleSizeSlot(SessionMenu menu) {
        super(
                menu,
                EasMaterial.BONE_MEAL,
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
        EasArmorStand entity = menu.getSession().getEntity();
        entity.setSmall(!entity.isSmall());
    }
}
