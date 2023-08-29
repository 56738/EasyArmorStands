package me.m56738.easyarmorstands.display.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class DisplayShearNode extends DisplayMenuNode {
    private final Session session;
    private final Component name;

    public DisplayShearNode(Session session, PropertyContainer container) {
        super(session, container);
        this.session = session;
        this.name = DisplayPropertyTypes.RIGHT_ROTATION.getName().color(NamedTextColor.GOLD);
    }

    @Override
    public void onUpdate(UpdateContext context) {
        super.onUpdate(context);
        session.setActionBar(name);
    }

    @Override
    public boolean onClick(ClickContext context) {
        if (super.onClick(context)) {
            return true;
        }
        if (context.type() == ClickContext.Type.LEFT_CLICK) {
            session.popNode();
            return true;
        }
        return false;
    }
}
