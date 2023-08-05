package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.menu.EntityItemSlot;
import me.m56738.easyarmorstands.menu.EntityMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class ItemEntityProperty<E extends Entity> implements ButtonEntityProperty<E, ItemStack> {
    @Override
    public @NotNull Component getValueName(ItemStack value) {
        return EasyArmorStands.getInstance().getCapability(ComponentCapability.class).getItemDisplayName(value);
    }

    @Override
    public @NotNull String getValueClipboardContent(ItemStack value) {
        return value.getType().name();
    }

    @Override
    public InventorySlot createSlot(EntityMenu<? extends E> menu) {
        return new EntityItemSlot<>(menu, this);
    }
}
