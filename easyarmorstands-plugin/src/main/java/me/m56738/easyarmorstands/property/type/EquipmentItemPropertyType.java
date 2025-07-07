package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.layout.EquipmentMenuLayout;
import me.m56738.easyarmorstands.api.menu.layout.MenuLayout;
import net.kyori.adventure.key.Key;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class EquipmentItemPropertyType extends ItemPropertyType {
    private final EquipmentSlot equipmentSlot;

    public EquipmentItemPropertyType(Key key, EquipmentSlot equipmentSlot) {
        super(key);
        this.equipmentSlot = equipmentSlot;
    }

    @Override
    public void addToMenu(@NotNull MenuLayout layout, @NotNull Element element) {
        MenuSlot slot = createSlot(element);
        if (slot != null) {
            if (layout instanceof EquipmentMenuLayout equipmentMenuLayout && equipmentMenuLayout.isEquipmentSlotSupported(equipmentSlot)) {
                equipmentMenuLayout.setEquipmentSlot(equipmentSlot, slot);
            } else {
                layout.addSlot(slot);
            }
        }
    }
}
