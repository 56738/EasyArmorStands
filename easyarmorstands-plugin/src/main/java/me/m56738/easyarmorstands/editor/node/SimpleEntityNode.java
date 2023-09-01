package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.editor.node.MenuNode;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SimpleEntityNode extends MenuNode implements ElementNode {
    private final Session session;
    private final Element element;
    private final Component name;

    public SimpleEntityNode(Session session, EditableElement element) {
        super(session);
        this.session = session;
        this.element = element;
        this.name = Message.component("easyarmorstands.node.select-axis");
        new ToolMenuManager(session, this, element.getTools(session.properties(element)))
                .setMode(ToolMenuMode.GLOBAL);
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
