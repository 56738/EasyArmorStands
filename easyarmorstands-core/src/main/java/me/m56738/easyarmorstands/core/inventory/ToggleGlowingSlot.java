package me.m56738.easyarmorstands.core.inventory;

import me.m56738.easyarmorstands.core.platform.EasArmorStand;
import me.m56738.easyarmorstands.core.platform.EasMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Arrays;

public class ToggleGlowingSlot extends ToggleSlot {
    private final SessionMenu menu;

    public ToggleGlowingSlot(SessionMenu menu) {
        super(
                menu,
                EasMaterial.GLOWSTONE_DUST,
                Component.text("Toggle glowing", NamedTextColor.BLUE), Arrays.asList(
                        Component.text("Changes whether the", NamedTextColor.GRAY),
                        Component.text("armor stand has an", NamedTextColor.GRAY),
                        Component.text("outline.", NamedTextColor.GRAY)
                )
        );
        this.menu = menu;
    }

    @Override
    protected Component getValue() {
        return menu.getSession().getEntity().isGlowing()
                ? Component.text("glowing", NamedTextColor.GREEN)
                : Component.text("not glowing", NamedTextColor.RED);
    }

    @Override
    protected void onClick() {
        EasArmorStand entity = menu.getSession().getEntity();
        entity.setGlowing(!entity.isGlowing());
    }
}
