package me.m56738.easyarmorstands.property.v1_19_4.display;

import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.property.ItemEntityProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemDisplayItemProperty extends ItemEntityProperty<ItemDisplay> {
    @Override
    public ItemStack getValue(ItemDisplay entity) {
        ItemStack item = entity.getItemStack();
        if (item == null) {
            item = new ItemStack(Material.AIR);
        }
        return item;
    }

    @Override
    public TypeToken<ItemStack> getValueType() {
        return TypeToken.get(ItemStack.class);
    }

    @Override
    public void setValue(ItemDisplay entity, ItemStack value) {
        entity.setItemStack(value);
    }

    @Override
    public @NotNull String getName() {
        return "item";
    }

    @Override
    public @NotNull Class<ItemDisplay> getEntityType() {
        return ItemDisplay.class;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("item");
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.display.item";
    }

    @Override
    public int getSlotIndex() {
        return 31;
    }
}
