package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.context.ManagedChangeContext;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.editor.node.NodeFactory;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ArmorStandPartSlot implements MenuSlot {
    private final Element element;
    private final PropertyType<EulerAngle> type;
    private final Session session;
    private final NodeFactory nodeFactory;
    private final SimpleItemTemplate itemTemplate;
    private final TagResolver resolver;

    public ArmorStandPartSlot(Element element, PropertyType<EulerAngle> type, Session session, NodeFactory nodeFactory, SimpleItemTemplate itemTemplate, TagResolver resolver) {
        this.element = element;
        this.type = type;
        this.session = session;
        this.nodeFactory = nodeFactory;
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
                    .handlePropertyShiftClick(element.getProperties().get(type), click);
        } else if (click.isLeftClick()) {
            session.pushNode(nodeFactory.createNode());
            click.close();
        } else if (click.isRightClick()) {
            try (ManagedChangeContext context = EasyArmorStands.get().changeContext().create(click.player())) {
                Property<EulerAngle> property = context.getProperties(element).get(type);
                property.setValue(EulerAngle.ZERO);
            }
        }
    }
}
