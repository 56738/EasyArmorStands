package me.m56738.easyarmorstands.menu.inventory;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.TooltipDisplay;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.Nullable;

public class ElementInventory implements InventoryHolder {
    private final Inventory inventory;
    private final Int2ObjectMap<@Nullable Property<ItemStack>> properties;
    private final PropertyContainer container;

    public ElementInventory(Int2ObjectMap<Property<ItemStack>> properties, PropertyContainer container) {
        this.inventory = Bukkit.createInventory(this, getHeight(properties) * 9, Component.text("Items"));
        this.properties = properties;
        this.container = container;
        populateInventory();
    }

    private static int getHeight(Int2ObjectMap<Property<ItemStack>> properties) {
        int maxIndex = properties.keySet().intStream().max().orElse(-1);
        return (maxIndex + 8) / 9;
    }

    private void populateInventory() {
        int size = inventory.getSize();
        ItemStack background = ItemStack.of(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        background.setData(DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplay.tooltipDisplay()
                .hideTooltip(true)
                .build());
        for (int i = 0; i < size; i++) {
            Property<ItemStack> property = properties.get(i);
            if (property != null) {
                inventory.setItem(i, property.getValue());
            } else {
                inventory.setItem(i, background);
            }
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public Int2ObjectMap<@Nullable Property<ItemStack>> getProperties() {
        return Int2ObjectMaps.unmodifiable(properties);
    }

    public PropertyContainer getContainer() {
        return container;
    }
}
