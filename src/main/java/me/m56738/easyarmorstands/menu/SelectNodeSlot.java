package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.node.Node;
import me.m56738.easyarmorstands.node.NodeFactory;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class SelectNodeSlot implements InventorySlot {
    private final ArmorStandMenu menu;
    private final NodeFactory nodeFactory;
    private final ItemType type;
    private final Component name;

    public SelectNodeSlot(ArmorStandMenu menu, NodeFactory nodeFactory, ItemType type, Component name) {
        this.menu = menu;
        this.nodeFactory = nodeFactory;
        this.type = type;
        this.name = name;
    }

    @Override
    public void initialize(int slot) {
        ItemStack item = Util.createItem(
                type,
                Component.text()
                        .content("Edit ")
                        .append(name)
                        .color(NamedTextColor.BLUE)
                        .build(),
                Arrays.asList(
                        Component.text("Selects this bone", NamedTextColor.GRAY),
                        Component.text("in the editor.", NamedTextColor.GRAY)
                )
        );
        menu.getInventory().setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, ClickType type) {
        if (click) {
            Node node = nodeFactory.createNode();
            if (node != null) {
                menu.getSession().pushNode(node);
                menu.close(menu.getSession().getPlayer());
            }
        }
        return false;
    }
}
