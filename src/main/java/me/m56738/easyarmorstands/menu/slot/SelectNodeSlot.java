package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.node.NodeFactory;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class SelectNodeSlot implements MenuSlot {
    private final Session session;
    private final NodeFactory nodeFactory;
    private final ItemType type;
    private final Component name;

    public SelectNodeSlot(Session session, NodeFactory nodeFactory, ItemType type, Component name) {
        this.session = session;
        this.nodeFactory = nodeFactory;
        this.type = type;
        this.name = name;
    }

    @Override
    public ItemStack getItem() {
        return Util.createItem(
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
    }

    @Override
    public void onClick(MenuClick click) {
        if (click.isLeftClick()) {
            session.pushNode(nodeFactory.createNode());
            click.close();
        }
    }
}
