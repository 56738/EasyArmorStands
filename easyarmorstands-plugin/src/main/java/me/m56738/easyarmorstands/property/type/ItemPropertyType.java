package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.menu.button.MenuSlotButton;
import me.m56738.easyarmorstands.menu.slot.ItemPropertySlot;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class ItemPropertyType extends ConfigurablePropertyType<ItemStack> {
    public ItemPropertyType(@NotNull Key key) {
        super(key);
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
    public boolean canCopy(@NotNull Player player) {
        return player.getGameMode() == GameMode.CREATIVE;
    }

    @Override
    public void addToMenu(@NonNull MenuBuilder builder, @NonNull Property<ItemStack> property) {
        builder.addButton(MenuSlotButton.toButton(new ItemPropertySlot(property)));
    }
}
