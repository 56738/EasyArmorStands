package me.m56738.easyarmorstands.display.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.display.editor.axis.DisplayShearRotateAxis;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

public class DisplayShearNode extends DisplayMenuNode implements ResettableNode {
    private final Session session;
    private final Component name;
    private final Property<Quaternionfc> rightRotationProperty;

    public DisplayShearNode(Session session, PropertyContainer properties) {
        super(session, properties);
        this.session = session;
        this.name = DisplayPropertyTypes.RIGHT_ROTATION.getName().color(NamedTextColor.GOLD);
        this.rightRotationProperty = properties.get(DisplayPropertyTypes.RIGHT_ROTATION);
        for (Axis axis : Axis.values()) {
            addButton(session.menuEntryProvider()
                    .rotate()
                    .setAxis(new DisplayShearRotateAxis(properties, axis))
                    .build());
        }
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

    @Override
    public void reset() {
        rightRotationProperty.setValue(new Quaternionf());
    }
}
