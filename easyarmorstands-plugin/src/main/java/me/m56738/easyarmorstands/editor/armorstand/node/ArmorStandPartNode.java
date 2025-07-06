package me.m56738.easyarmorstands.editor.armorstand.node;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.AbstractElementNode;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.editor.util.ToolManager;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.editor.node.ToolModeSwitcher;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.element.ArmorStandPartToolProvider;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ArmorStandPartNode extends AbstractElementNode<ArmorStandElement> implements ResettableNode {
    private final ArmorStandPart part;
    private final ToolManager toolManager;
    private final ToolModeSwitcher toolModeSwitcher;

    public ArmorStandPartNode(Session session, ArmorStandElement element, ArmorStandPart part) {
        super(session, element);
        this.part = part;
        this.toolManager = new ToolManager(session, this,
                new ArmorStandPartToolProvider(element, part, getContext(), element.getTools(getContext())));
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
            getSession().popNode();
            return true;
        }
        return toolModeSwitcher.onClick(context);
    }

    @Override
    public void reset() {
        getProperties().get(ArmorStandPropertyTypes.POSE.get(part)).setValue(EulerAngle.ZERO);
        getContext().commit();
    }
}
