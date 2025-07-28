package me.m56738.easyarmorstands.display.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.display.element.DisplayElement;
import me.m56738.easyarmorstands.display.element.DisplayToolProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

public class DisplayShearNode extends DisplayNode implements ResettableNode {
    private final Session session;
    private final Component name;
    private final Property<Quaternionfc> rightRotationProperty;

    public DisplayShearNode(Session session, DisplayElement element) {
        super(session, element);
        this.session = session;
        this.name = DisplayPropertyTypes.RIGHT_ROTATION.getName().color(NamedTextColor.GOLD);
        this.rightRotationProperty = getProperties().get(DisplayPropertyTypes.RIGHT_ROTATION);
        DisplayToolProvider tools = element.getTools(getContext());
        for (Axis axis : Axis.values()) {
            // TODO use ToolMenuManager
            addButton(session.menuEntryProvider()
                    .axisRotate()
                    .setTool(tools.shear(tools.context(), axis))
                    .build());
        }
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
        if (context.type() == ClickContext.Type.LEFT_CLICK) {
            session.popNode();
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        rightRotationProperty.setValue(new Quaternionf());
    }
}
