package me.m56738.easyarmorstands.editor.armorstand.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.editor.axis.LocationCarryAxis;
import me.m56738.easyarmorstands.editor.axis.LocationMoveAxis;
import me.m56738.easyarmorstands.editor.axis.LocationYawRotateAxis;
import me.m56738.easyarmorstands.editor.node.PropertyMenuNode;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;

public class ArmorStandPositionNode extends PropertyMenuNode implements ResettableNode {
    private final Session session;
    private final Component name;

    public ArmorStandPositionNode(Session session, Component name, PropertyContainer properties, OffsetProvider offsetProvider) {
        super(session, properties);
        this.session = session;
        this.name = name;
        addButton(session.menuEntryProvider()
                .carry()
                .setAxis(new LocationCarryAxis(properties, offsetProvider))
                .setPriority(1)
                .build());
        addButton(session.menuEntryProvider()
                .rotate()
                .setAxis(new LocationYawRotateAxis(properties, offsetProvider))
                .setName(Message.component("easyarmorstands.node.yaw"))
                .build());
        for (Axis axis : Axis.values()) {
            addButton(session.menuEntryProvider()
                    .move()
                    .setAxis(new LocationMoveAxis(properties, axis, offsetProvider))
                    .build());
        }
    }

    @Override
    public void reset() {
        Property<Location> locationProperty = properties().get(EntityPropertyTypes.LOCATION);
        Location location = locationProperty.getValue();
        location.setYaw(0);
        location.setPitch(0);
        locationProperty.setValue(location);
        properties().commit();
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
