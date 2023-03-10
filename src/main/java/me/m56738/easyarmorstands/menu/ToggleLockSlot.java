package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.capability.lock.LockCapability;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;

import java.util.Arrays;

public class ToggleLockSlot extends ToggleSlot {
    private final SessionMenu menu;
    private final LockCapability lockCapability;

    public ToggleLockSlot(SessionMenu menu, LockCapability lockCapability) {
        super(
                menu,
                ItemType.IRON_BARS,
                Component.text("Toggle lock", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Changes whether the", NamedTextColor.GRAY),
                        Component.text("equipment can be changed", NamedTextColor.GRAY),
                        Component.text("by right clicking.", NamedTextColor.GRAY)
                )
        );
        this.menu = menu;
        this.lockCapability = lockCapability;
    }

    @Override
    protected Component getValue() {
        return lockCapability.isLocked(menu.getSession().getEntity())
                ? Component.text("locked", NamedTextColor.GREEN)
                : Component.text("unlocked", NamedTextColor.RED);
    }

    @Override
    protected void onClick() {
        ArmorStand entity = menu.getSession().getEntity();
        lockCapability.setLocked(entity, !lockCapability.isLocked(entity));
    }
}
