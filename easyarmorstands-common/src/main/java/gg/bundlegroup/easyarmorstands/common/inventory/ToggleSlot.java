package gg.bundlegroup.easyarmorstands.common.inventory;

import gg.bundlegroup.easyarmorstands.common.platform.EasItem;
import gg.bundlegroup.easyarmorstands.common.platform.EasMaterial;
import gg.bundlegroup.easyarmorstands.common.platform.EasPlatform;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public abstract class ToggleSlot implements InventorySlot {
    private final SessionMenu menu;
    private final EasPlatform platform;
    private final EasMaterial type;
    private final Component title;
    private final List<Component> description;

    public ToggleSlot(SessionMenu menu, EasMaterial type, Component title, List<Component> description) {
        this.menu = menu;
        this.platform = menu.getInventory().platform();
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
        EasItem item = platform.createItem(type, title, getLore());
        menu.getInventory().setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, EasItem cursor) {
        if (click) {
            onClick();
            initialize(slot);
        }
        return false;
    }
}
