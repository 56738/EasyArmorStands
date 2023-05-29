package me.m56738.easyarmorstands.property;

import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.bukkit.parsers.ItemStackArgument;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.menu.EntityItemSlot;
import me.m56738.easyarmorstands.menu.EntityMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class ItemEntityProperty<E extends Entity> implements ButtonEntityProperty<E, ItemStack> {

    @Override
    public ArgumentParser<EasCommandSender, ItemStack> getArgumentParser() {
        return new ItemStackArgument.Parser<EasCommandSender>().map((ctx, value) ->
                ArgumentParseResult.success(value.createItemStack(1, true)));
    }

    @Override
    public @NotNull Component getValueName(ItemStack value) {
        return EasyArmorStands.getInstance().getCapability(ComponentCapability.class).getItemDisplayName(value);
    }

    @Override
    public InventorySlot createSlot(EntityMenu<? extends E> menu) {
        return new EntityItemSlot<>(menu, this);
    }

    @Override
    public boolean isCreativeModeRequired() {
        return true;
    }
}
