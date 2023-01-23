package gg.bundlegroup.easyarmorstands.common.inventory;

import gg.bundlegroup.easyarmorstands.common.handle.Handle;
import gg.bundlegroup.easyarmorstands.common.platform.EasItem;
import gg.bundlegroup.easyarmorstands.common.platform.EasMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Arrays;

public class SelectHandleSlot implements InventorySlot {
    private final SessionMenu menu;
    private final Handle handle;
    private final EasMaterial type;
    private final Component name;

    public SelectHandleSlot(SessionMenu menu, Handle handle, EasMaterial type, Component name) {
        this.menu = menu;
        this.handle = handle;
        this.type = type;
        this.name = name;
    }

    @Override
    public void initialize(int slot) {
        EasItem item = menu.getInventory().platform().createItem(
                type,
                Component.text()
                        .content("Edit ")
                        .append(name)
                        .color(NamedTextColor.BLUE)
                        .build(),
                Arrays.asList(
                        Component.text("Selects this handle", NamedTextColor.GRAY),
                        Component.text("in the editor.", NamedTextColor.GRAY)
                )
        );
        menu.getInventory().setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, EasItem cursor) {
        if (click) {
            menu.getSession().setHandle(handle);
            menu.queueTask(() -> {
                menu.getSession().getPlayer().closeInventory(menu.getInventory());
            });
        }
        return false;
    }
}
