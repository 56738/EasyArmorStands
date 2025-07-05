package me.m56738.easyarmorstands.editor.armorstand.node;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.editor.util.ToolManager;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.editor.node.AbstractPropertyNode;
import me.m56738.easyarmorstands.editor.node.ToolModeSwitcher;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.element.ArmorStandPartToolProvider;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

public class ArmorStandPartNode extends AbstractPropertyNode implements ResettableNode {
    private final Session session;
    private final ArmorStandPart part;
    private final ToolManager toolManager;
    private final ToolModeSwitcher toolModeSwitcher;

    public ArmorStandPartNode(Session session, PropertyContainer container, ArmorStandPart part, ArmorStandElement element) {
        super(session, container);
        this.session = session;
        this.part = part;
        this.toolManager = new ToolManager(session, this,
                new ArmorStandPartToolProvider(container, part, element, element.getTools(container)));
        this.toolModeSwitcher = new ToolModeSwitcher(this.toolManager);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        context.setActionBar(toolModeSwitcher.getActionBar());
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
        return toolModeSwitcher.onClick(context);
    }

    @Override
    public void reset() {
        properties().get(ArmorStandPropertyTypes.POSE.get(part)).setValue(EulerAngle.ZERO);
        properties().commit();
    }
}
