package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.clipboard.Clipboard;
import me.m56738.easyarmorstands.editor.node.NodeFactory;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ArmorStandPartSlot implements MenuSlot {
    private final Session session;
    private final NodeFactory nodeFactory;
    private final PropertyContainer container;
    private final Property<EulerAngle> property;
    private final SimpleItemTemplate itemTemplate;
    private final TagResolver resolver;

    public ArmorStandPartSlot(Session session, NodeFactory nodeFactory, PropertyContainer container, Property<EulerAngle> property, SimpleItemTemplate itemTemplate, TagResolver resolver) {
        this.session = session;
        this.nodeFactory = nodeFactory;
        this.container = container;
        this.property = property;
        this.itemTemplate = itemTemplate;
        this.resolver = resolver;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return itemTemplate.render(locale, resolver);
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        if (click.isShiftClick()) {
            EasyArmorStandsPlugin.getInstance().getClipboard(click.player())
                    .handlePropertyShiftClick(property, click);
        } else if (click.isLeftClick()) {
            session.pushNode(nodeFactory.createNode());
            click.close();
        } else if (click.isRightClick()) {
            property.setValue(EulerAngle.ZERO);
            container.commit();
        }
    }
}
