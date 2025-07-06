package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.menu.slot.ItemPropertySlot;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemPropertyType extends ConfigurablePropertyType<ItemStack> {
    public ItemPropertyType(@NotNull Key key) {
        super(key, ItemStack.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull ItemStack value) {
        return value.displayName();
    }

    @Override
    public @NotNull ItemStack cloneValue(@NotNull ItemStack value) {
        return value.clone();
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull Element element) {
        return new ItemPropertySlot(element, this);
    }

    @Override
    public boolean canCopy(@NotNull Player player) {
        return player.getGameMode() == GameMode.CREATIVE;
    }
}
