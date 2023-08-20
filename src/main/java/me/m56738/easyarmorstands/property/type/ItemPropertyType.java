package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

public class ItemPropertyType extends ConfigurablePropertyType<ItemStack> {
    public ItemPropertyType(String key) {
        super(key);
    }

    @Override
    public Component getValueComponent(ItemStack value) {
        return EasyArmorStands.getInstance().getCapability(ComponentCapability.class).getItemDisplayName(value);
    }

    @Override
    public ItemStack cloneValue(ItemStack value) {
        return value.clone();
    }
}
