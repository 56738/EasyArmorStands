package me.m56738.easyarmorstands.menu.inventory;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import org.bukkit.inventory.ItemStack;

public class ElementInventoryBuilder {
    private final Int2ObjectMap<Property<ItemStack>> properties = new Int2ObjectOpenHashMap<>();
    private final PropertyContainer container;

    public ElementInventoryBuilder(PropertyContainer container) {
        this.container = container;
    }

    public void setSlot(int row, int column, Property<ItemStack> property) {
        int index = 9 * row + column;
        properties.put(index, property);
    }

    public ElementInventory build() {
        return new ElementInventory(new Int2ObjectOpenHashMap<>(properties), container);
    }
}
