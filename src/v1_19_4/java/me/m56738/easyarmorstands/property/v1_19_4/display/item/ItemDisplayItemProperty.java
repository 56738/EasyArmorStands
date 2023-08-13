package me.m56738.easyarmorstands.property.v1_19_4.display.item;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemDisplayItemProperty implements Property<ItemStack> {
    public static final PropertyType<ItemStack> TYPE = new Type();
    private final ItemDisplay entity;

    public ItemDisplayItemProperty(ItemDisplay entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<ItemStack> getType() {
        return TYPE;
    }

    @Override
    public ItemStack getValue() {
        return entity.getItemStack();
    }

    @Override
    public boolean setValue(ItemStack value) {
        entity.setItemStack(value);
        return true;
    }

    private static class Type implements PropertyType<ItemStack> {

        @Override
        public @Nullable String getPermission() {
            return "easyarmorstands.property.display.item";
        }

        @Override
        public Component getDisplayName() {
            return Component.text("item");
        }

        @Override
        public Component getValueComponent(ItemStack value) {
            ComponentCapability componentCapability = EasyArmorStands.getInstance().getCapability(ComponentCapability.class);
            return componentCapability.getItemDisplayName(value);
        }

        @Override
        public ItemStack cloneValue(ItemStack value) {
            return value.clone();
        }
    }
}
