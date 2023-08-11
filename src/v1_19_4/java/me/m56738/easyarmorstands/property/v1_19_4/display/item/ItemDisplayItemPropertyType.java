package me.m56738.easyarmorstands.property.v1_19_4.display.item;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.EntityPropertyAction;
import me.m56738.easyarmorstands.property.EntityPropertyType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.SimpleEntityProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemDisplayItemPropertyType implements EntityPropertyType<ItemStack> {
    public static final ItemDisplayItemPropertyType INSTANCE = new ItemDisplayItemPropertyType();

    private final ComponentCapability componentCapability;

    private ItemDisplayItemPropertyType() {
        componentCapability = EasyArmorStands.getInstance().getCapability(ComponentCapability.class);
    }

    @Override
    public @Nullable Property<ItemStack> bind(Entity entity) {
        if (entity instanceof ItemDisplay) {
            return new Bound((ItemDisplay) entity);
        }
        return null;
    }

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
        return componentCapability.getItemDisplayName(value);
    }

    private class Bound extends SimpleEntityProperty<ItemStack> {
        private final ItemDisplay entity;

        private Bound(ItemDisplay entity) {
            super(ItemDisplayItemPropertyType.this, entity);
            this.entity = entity;
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
    }
}
