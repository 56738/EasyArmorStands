package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.node.NodeFactory;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NodeSlot implements MenuSlot {
    private final Session session;
    private final NodeFactory nodeFactory;
    private final Consumer<MenuClick> resetAction;
    private final ItemType type;
    private final Component name;

    public NodeSlot(Session session, NodeFactory nodeFactory, Consumer<MenuClick> resetAction, ItemType type, Component name) {
        this.session = session;
        this.nodeFactory = nodeFactory;
        this.resetAction = resetAction;
        this.type = type;
        this.name = name;
    }

    @Override
    public ItemStack getItem() {
        List<Component> description = new ArrayList<>();
        description.add(Component.text("Click to select this", NamedTextColor.GRAY));
        description.add(Component.text("bone in the editor.", NamedTextColor.GRAY));
        if (resetAction != null) {
            description.add(Component.text("Right click to reset.", NamedTextColor.GRAY));
        }
        return Util.createItem(
                type,
                Component.text()
                        .content("Edit ")
                        .append(name)
                        .color(NamedTextColor.BLUE)
                        .build(),
                description
        );
    }

    @Override
    public void onClick(MenuClick click) {
        if (click.isLeftClick()) {
            session.pushNode(nodeFactory.createNode());
            click.close();
        } else if (click.isRightClick()) {
            if (resetAction != null) {
                resetAction.accept(click);
            }
        }
    }
}
