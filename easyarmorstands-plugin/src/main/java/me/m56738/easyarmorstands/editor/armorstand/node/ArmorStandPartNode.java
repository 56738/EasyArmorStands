package me.m56738.easyarmorstands.editor.armorstand.node;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.editor.util.ToolMenuManager;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.editor.input.ReturnInput;
import me.m56738.easyarmorstands.editor.node.PropertyMenuNode;
import me.m56738.easyarmorstands.editor.node.ToolMenuModeSwitcher;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.element.ArmorStandPartToolProvider;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

public class ArmorStandPartNode extends PropertyMenuNode implements ResettableNode {
    private final Session session;
    private final ArmorStandPart part;
    private final ToolMenuManager toolManager;
    private final ToolMenuModeSwitcher toolSwitcher;

    public ArmorStandPartNode(Session session, PropertyContainer container, ArmorStandPart part, ArmorStandElement element) {
        super(session, container);
        this.session = session;
        this.part = part;
        this.toolManager = new ToolMenuManager(session, this,
                new ArmorStandPartToolProvider(container, part, element, element.getTools(container)));
        this.toolSwitcher = new ToolMenuModeSwitcher(this.toolManager);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        context.setActionBar(toolSwitcher.getActionBar());
        toolSwitcher.onUpdate(context);
        super.onUpdate(context);
        context.addInput(new ReturnInput(session));
    }

    @Override
    public void reset() {
        properties().get(ArmorStandPropertyTypes.POSE.get(part)).setValue(EulerAngle.ZERO);
        properties().commit();
    }
}
