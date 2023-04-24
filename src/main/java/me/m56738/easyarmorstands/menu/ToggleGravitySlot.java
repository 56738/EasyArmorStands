package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.capability.tick.TickCapability;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;

import java.util.Arrays;
import java.util.List;

public class ToggleGravitySlot extends ToggleSlot {
    private final ArmorStandMenu menu;
    private final TickCapability tickCapability;

    public ToggleGravitySlot(ArmorStandMenu menu, TickCapability tickCapability) {
        super(
                menu,
                ItemType.FEATHER,
                Component.text("Toggle gravity", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Changes whether the", NamedTextColor.GRAY),
                        Component.text("armor stand will fall", NamedTextColor.GRAY),
                        Component.text("due to gravity.", NamedTextColor.GRAY)
                )
        );
        this.menu = menu;
        this.tickCapability = tickCapability;
    }

    @Override
    protected List<Component> getLore() {
        List<Component> lore = super.getLore();
        ArmorStand entity = menu.getEntity();
        if (tickCapability != null && entity.hasGravity() && !tickCapability.canTick(entity)) {
            lore.add(Component.text("Gravity is enabled but", NamedTextColor.GOLD));
            lore.add(Component.text("armor stand ticking is", NamedTextColor.GOLD));
            lore.add(Component.text("disabled, so gravity", NamedTextColor.GOLD));
            lore.add(Component.text("has no effect.", NamedTextColor.GOLD));
        }
        return lore;
    }

    @Override
    protected Component getValue() {
        ArmorStand entity = menu.getEntity();
        if (tickCapability != null && !tickCapability.canTick(entity)) {
            return Component.text("frozen", NamedTextColor.GOLD);
        }
        return entity.hasGravity()
                ? Component.text("has gravity", NamedTextColor.GREEN)
                : Component.text("static", NamedTextColor.RED);
    }

    @Override
    protected void onClick() {
        ArmorStand entity = menu.getEntity();
        boolean gravity = !entity.hasGravity();
        entity.setGravity(gravity);
        if (gravity && tickCapability != null && !tickCapability.canTick(entity) &&
                menu.getSession().getPlayer().hasPermission("easyarmorstands.property.cantick")) {
            tickCapability.setCanTick(entity, true);
        }
    }
}
