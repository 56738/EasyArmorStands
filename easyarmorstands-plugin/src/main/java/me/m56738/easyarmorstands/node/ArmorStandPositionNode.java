package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;

public class ArmorStandPositionNode extends PropertyMenuNode implements ResettableNode {
    public ArmorStandPositionNode(Session session, Component name, PropertyContainer container) {
        super(session, name, container);
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
}
