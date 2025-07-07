package me.m56738.easyarmorstands.menu.layout;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.layout.EquipmentMenuLayout;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.EquipmentSlot;
import org.jspecify.annotations.Nullable;

import java.util.Locale;

public class EquipmentMenuBuilder extends MenuBuilder implements EquipmentMenuLayout {
    private int nextRow;
    private int nextColumn = 4;

    public EquipmentMenuBuilder(Component title, Locale locale, MenuSlot background) {
        super(title, locale, background);
    }

    @Override
    public boolean isEquipmentSlotSupported(EquipmentSlot equipmentSlot) {
        return getSlot(equipmentSlot) != null;
    }

    @Override
    public void setEquipmentSlot(EquipmentSlot equipmentSlot, MenuSlot slot) {
        Slot targetSlot = getSlot(equipmentSlot);
        if (targetSlot == null) {
            throw new IllegalArgumentException("Unexpected equipment slot: " + equipmentSlot);
        }
        setSlot(targetSlot.row(), targetSlot.column(), slot);
    }

    @Override
    public void addSlot(MenuSlot slot) {
        setSlot(nextRow, nextColumn, slot);
        nextColumn++;
        if (nextColumn > 8) {
            nextColumn = 4;
            nextRow++;
        }
    }

    @SuppressWarnings("UnnecessaryDefault")
    private @Nullable Slot getSlot(EquipmentSlot equipmentSlot) {
        return switch (equipmentSlot) {
            case HEAD -> new Slot(2, 1);
            case OFF_HAND -> new Slot(3, 0);
            case CHEST -> new Slot(3, 1);
            case HAND -> new Slot(3, 2);
            case LEGS -> new Slot(4, 1);
            case FEET -> new Slot(5, 1);
            case SADDLE -> new Slot(4, 0);
            case BODY -> new Slot(5, 0);
            default -> null;
        };
    }

    private record Slot(int row, int column) {
    }
}
