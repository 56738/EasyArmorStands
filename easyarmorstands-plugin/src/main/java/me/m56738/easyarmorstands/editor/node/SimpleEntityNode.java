package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.editor.node.MenuNode;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.editor.axis.LocationCarryAxis;
import me.m56738.easyarmorstands.editor.axis.LocationMoveAxis;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SimpleEntityNode extends MenuNode implements ElementNode {
    private final Session session;
    private final Element element;
    private final Component name;

    public SimpleEntityNode(Session session, Element element) {
        super(session);
        this.session = session;
        this.element = element;
        this.name = Message.component("easyarmorstands.node.select-axis");

        PropertyContainer properties = session.properties(element);
        for (Axis axis : Axis.values()) {
            addButton(session.menuEntryProvider()
                    .move()
                    .setAxis(new LocationMoveAxis(properties, axis, OffsetProvider.zero()))
                    .build());
        }

        addButton(session.menuEntryProvider()
                .carry()
                .setAxis(new LocationCarryAxis(properties, OffsetProvider.zero()))
                .setPriority(1)
                .build());
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        context.setActionBar(name);
    }

    @Override
    public boolean onClick(@NotNull ClickContext context) {
        if (super.onClick(context)) {
            return true;
        }
        if (element instanceof MenuElement) {
            Player player = session.player();
            if (context.type() == ClickContext.Type.LEFT_CLICK && player.hasPermission(Permissions.OPEN)) {
                ((MenuElement) element).openMenu(player);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isValid() {
        return element.isValid();
    }

    @Override
    public Element getElement() {
        return element;
    }
}
