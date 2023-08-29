package me.m56738.easyarmorstands.display.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.display.editor.axis.DisplayBoxMoveAxis;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class DisplayBoxNode extends DisplayMenuNode implements ResettableNode {
    private final Session session;
    private final Component name;

    public DisplayBoxNode(Session session, PropertyContainer properties) {
        super(session, properties);
        this.session = session;
        this.name = Message.component("easyarmorstands.node.display.box").color(NamedTextColor.GOLD);
        setShowBoundingBoxIfInactive(true); // bounding box should remain visible while a tool node is active
        for (Axis axis : Axis.values()) {
            addButton(session.menuEntryProvider()
                    .move()
                    .setAxis(new DisplayBoxMoveAxis(properties, axis))
                    .build());
        }
        // TODO
//        addScaleButtons(session, bone, 3);
//        addButton(session.menuEntryProvider()
//                .carry()
//                .setBone(bone)
//                .setPriority(1)
//                .build());
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
        // TODO
    }
}
