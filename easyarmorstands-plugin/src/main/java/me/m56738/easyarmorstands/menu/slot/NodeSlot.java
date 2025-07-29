package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.common.editor.node.NodeFactory;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.function.Consumer;

public class NodeSlot implements MenuSlot {
    private final Session session;
    private final NodeFactory nodeFactory;
    private final Consumer<MenuClick> resetAction;
    private final SimpleItemTemplate itemTemplate;
    private final TagResolver resolver;

    public NodeSlot(Session session, NodeFactory nodeFactory, Consumer<MenuClick> resetAction, SimpleItemTemplate itemTemplate, TagResolver resolver) {
        this.session = session;
        this.nodeFactory = nodeFactory;
        this.resetAction = resetAction;
        this.itemTemplate = itemTemplate;
        this.resolver = resolver;
    }

    @Override
    public Item getItem(Locale locale) {
        return itemTemplate.render(locale, resolver);
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
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
