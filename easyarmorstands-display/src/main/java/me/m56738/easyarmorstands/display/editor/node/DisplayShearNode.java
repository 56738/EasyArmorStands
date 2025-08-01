package me.m56738.easyarmorstands.display.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.display.element.DisplayElement;
import me.m56738.easyarmorstands.display.element.DisplayToolProvider;
import me.m56738.easyarmorstands.lib.joml.Quaternionf;
import me.m56738.easyarmorstands.lib.joml.Quaternionfc;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

public class DisplayShearNode extends DisplayMenuNode implements ResettableNode {
    private final Session session;
    private final Component name;
    private final Property<Quaternionfc> rightRotationProperty;

    public DisplayShearNode(Session session, PropertyContainer properties, DisplayElement<?> element) {
        super(session, properties);
        this.session = session;
        this.name = DisplayPropertyTypes.RIGHT_ROTATION.getName().color(NamedTextColor.GOLD);
        this.rightRotationProperty = properties.get(DisplayPropertyTypes.RIGHT_ROTATION);
        DisplayToolProvider tools = element.getTools(properties);
        for (Axis axis : Axis.values()) {
            // TODO use ToolMenuManager
            addButton(session.menuEntryProvider()
                    .axisRotate()
                    .setTool(tools.shear(ToolContext.of(tools.position(), tools.rotation()), axis))
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
