package me.m56738.easyarmorstands.editor.armorstand.node;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.editor.node.PropertyMenuNode;
import me.m56738.easyarmorstands.editor.node.ToolMenuManager;
import me.m56738.easyarmorstands.editor.node.ToolMenuMode;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.element.ArmorStandPartToolProvider;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

public class ArmorStandPartNode extends PropertyMenuNode implements ResettableNode {
    private final Session session;
    private final ArmorStandPart part;
    private final ToolMenuManager toolManager;

    public ArmorStandPartNode(Session session, PropertyContainer container, ArmorStandPart part, ArmorStandElement element) {
        super(session, container);
        this.session = session;
        this.part = part;
        this.toolManager = new ToolMenuManager(session, this,
                new ArmorStandPartToolProvider(container, part, element.getTools(container)));
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        context.setActionBar(toolManager.getMode().getName());
    }

    @Override
    public boolean onClick(@NotNull ClickContext context) {
        if (super.onClick(context)) {
            return true;
        }
        if (context.type() == ClickContext.Type.LEFT_CLICK) {
            session.popNode();
            return true;
        }
        if (context.type() == ClickContext.Type.RIGHT_CLICK) {
            ToolMenuMode mode = toolManager.getMode();
            ToolMenuMode nextMode = toolManager.getNextMode();
            if (nextMode != mode) {
                toolManager.setMode(nextMode);
                return true;
            }
        }
        return false;
    }

    @Override
    public void reset() {
        properties().get(ArmorStandPropertyTypes.POSE.get(part)).setValue(EulerAngle.ZERO);
        properties().commit();
    }
}
