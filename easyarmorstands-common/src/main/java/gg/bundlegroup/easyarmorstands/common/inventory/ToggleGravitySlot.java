package gg.bundlegroup.easyarmorstands.common.inventory;

import gg.bundlegroup.easyarmorstands.common.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.common.platform.EasMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Arrays;
import java.util.List;

public class ToggleGravitySlot extends ToggleSlot {
    private final SessionMenu menu;

    public ToggleGravitySlot(SessionMenu menu) {
        super(
                menu,
                EasMaterial.FEATHER,
                Component.text("Toggle gravity", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Changes whether the", NamedTextColor.GRAY),
                        Component.text("armor stand will fall", NamedTextColor.GRAY),
                        Component.text("due to gravity.", NamedTextColor.GRAY)
                )
        );
        this.menu = menu;
    }

    @Override
    protected List<Component> getLore() {
        List<Component> lore = super.getLore();
        EasArmorStand entity = menu.getSession().getEntity();
        if (entity.hasGravity() && !entity.canTick()) {
            lore.add(Component.text("Gravity is enabled but", NamedTextColor.GOLD));
            lore.add(Component.text("armor stand ticking is", NamedTextColor.GOLD));
            lore.add(Component.text("disabled, so gravity", NamedTextColor.GOLD));
            lore.add(Component.text("has no effect.", NamedTextColor.GOLD));
        }
        return lore;
    }

    @Override
    protected Component getValue() {
        EasArmorStand entity = menu.getSession().getEntity();
        if (!entity.canTick()) {
            return Component.text("frozen", NamedTextColor.GOLD);
        }
        return entity.hasGravity()
                ? Component.text("has gravity", NamedTextColor.GREEN)
                : Component.text("static", NamedTextColor.RED);
    }

    @Override
    protected void onClick() {
        EasArmorStand entity = menu.getSession().getEntity();
        boolean gravity = !entity.hasGravity();
        entity.setGravity(gravity);
        if (gravity) {
            entity.setCanTick(true);
        }
    }
}
