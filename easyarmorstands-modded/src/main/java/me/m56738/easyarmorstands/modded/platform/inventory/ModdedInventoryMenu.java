package me.m56738.easyarmorstands.modded.platform.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.NonInteractiveResultSlot;
import net.minecraft.world.inventory.Slot;

public class ModdedInventoryMenu extends ChestMenu {
    public ModdedInventoryMenu(MenuType<?> type, int containerId, Inventory playerInventory, Container container, int rows) {
        super(type, containerId, playerInventory, container, rows);
    }

    @Override
    protected Slot addSlot(Slot slot) {
        if (slot.container == getContainer()) {
            return super.addSlot(new NonInteractiveResultSlot(slot.container, slot.getContainerSlot(), slot.x, slot.y));
        } else {
            return super.addSlot(slot);
        }
    }
}
