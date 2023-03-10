package me.m56738.easyarmorstands.inventory;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class ToggleSlot implements InventorySlot {
    private final SessionMenu menu;
    private final ItemType type;
    private final Component title;
    private final List<Component> description;

    public ToggleSlot(SessionMenu menu, ItemType type, Component title, List<Component> description) {
        this.menu = menu;
        this.type = type;
        this.title = title;
        this.description = description;
    }

    protected abstract Component getValue();

    protected List<Component> getLore() {
        List<Component> lore = new ArrayList<>(description.size() + 1);
        lore.add(Component.text()
                .content("Currently ")
                .append(getValue())
                .append(Component.text("."))
                .color(NamedTextColor.GRAY)
                .build());
        lore.addAll(description);
        return lore;
    }

    protected abstract void onClick();

    @Override
    public void initialize(int slot) {
        ItemStack item = Util.createItem(type, title, getLore());
        menu.getInventory().setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, ItemStack cursor) {
        if (click) {
            onClick();
            initialize(slot);
        }
        return false;
    }
}
