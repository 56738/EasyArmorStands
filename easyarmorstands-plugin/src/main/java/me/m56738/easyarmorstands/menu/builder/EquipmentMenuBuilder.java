package me.m56738.easyarmorstands.menu.builder;

import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuButtonCategory;
import me.m56738.easyarmorstands.menu.MenuCreator;
import me.m56738.easyarmorstands.menu.button.DestroyButton;
import me.m56738.easyarmorstands.menu.button.MenuSlotButton;
import me.m56738.easyarmorstands.menu.slot.EquipmentPropertySlot;
import me.m56738.easyarmorstands.menu.slot.MenuButtonSlot;
import org.bukkit.inventory.EquipmentSlot;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

public class EquipmentMenuBuilder extends AbstractMenuBuilder {
    @Override
    protected void build(List<MenuButton> buttons, MenuCreator creator) {
        LinkedList<MenuButton> shortcutQueue = new LinkedList<>();
        LinkedList<MenuButton> defaultQueue = new LinkedList<>();
        EnumMap<EquipmentSlot, MenuButton> equipmentButtons = new EnumMap<>(EquipmentSlot.class);
        MenuButton destroyButton = null;
        for (MenuButton button : buttons) {
            if (button instanceof DestroyButton) {
                destroyButton = button;
            } else if (button.category() == MenuButtonCategory.SHORTCUT) {
                shortcutQueue.add(button);
            } else if (button instanceof MenuSlotButton menuSlotButton
                    && menuSlotButton.getSlot() instanceof EquipmentPropertySlot equipmentPropertySlot) {
                equipmentButtons.put(equipmentPropertySlot.getSlot(), button);
            } else {
                defaultQueue.add(button);
            }
        }


        for (int i = 0; i < 9 * 6; i++) {
            int row = i / 9;
            int column = i % 9;

            MenuButton button = null;
            if (row == 0 && column == 8 && destroyButton != null) {
                button = destroyButton;

            } else if (row == 1 && column == 1) {
                button = equipmentButtons.remove(EquipmentSlot.HEAD);
            } else if (row == 2 && column == 0) {
                button = equipmentButtons.remove(EquipmentSlot.HAND);
            } else if (row == 2 && column == 1) {
                button = equipmentButtons.remove(EquipmentSlot.BODY);
            } else if (row == 2 && column == 2) {
                button = equipmentButtons.remove(EquipmentSlot.OFF_HAND);
            } else if (row == 3 && column == 1) {
                button = equipmentButtons.remove(EquipmentSlot.LEGS);
            } else if (row == 4 && column == 1) {
                button = equipmentButtons.remove(EquipmentSlot.FEET);
            } else if (row < 2 && column < 4) {
                button = shortcutQueue.poll();
            } else if (column >= 4) {
                button = defaultQueue.poll();
            }

            if (button != null) {
                creator.setSlot(i, MenuButtonSlot.toSlot(button));
            }
        }
    }
}
