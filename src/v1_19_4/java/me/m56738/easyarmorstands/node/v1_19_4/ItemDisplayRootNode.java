package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.menu.builder.SplitMenuBuilder;
import me.m56738.easyarmorstands.menu.slot.ItemPropertySlot;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.v1_19_4.display.item.ItemDisplayItemProperty;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;

public class ItemDisplayRootNode extends DisplayRootNode {
    private final Session session;
    private final PropertyContainer container;

    public ItemDisplayRootNode(Session session, Component name, DisplayObject<ItemDisplay> editableObject) {
        super(session, name, editableObject);
        this.session = session;
        this.container = session.properties(editableObject);
    }

    @Override
    protected void populate(SplitMenuBuilder builder) {
        Property<ItemStack> property = container.get(ItemDisplayItemProperty.TYPE);
        builder.setSlot(Menu.index(3, 1), new ItemPropertySlot(property, session));
        builder.ensureRow(4);
        super.populate(builder);
    }
}
