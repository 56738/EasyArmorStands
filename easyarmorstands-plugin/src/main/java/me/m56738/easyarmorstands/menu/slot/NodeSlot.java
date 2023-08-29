package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.item.ItemTemplate;
import me.m56738.easyarmorstands.editor.node.NodeFactory;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.function.Consumer;

public class NodeSlot implements MenuSlot {
    private final Session session;
    private final NodeFactory nodeFactory;
    private final Consumer<MenuClick> resetAction;
    private final ItemTemplate itemTemplate;
    private final TagResolver resolver;

    public NodeSlot(Session session, NodeFactory nodeFactory, Consumer<MenuClick> resetAction, ItemTemplate itemTemplate, TagResolver resolver) {
        this.session = session;
        this.nodeFactory = nodeFactory;
        this.resetAction = resetAction;
        this.itemTemplate = itemTemplate;
        this.resolver = resolver;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return itemTemplate.render(locale, resolver);
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
