package me.m56738.easyarmorstands.editor.armorstand.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.EntityYawRotationProvider;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.editor.node.PropertyMenuNode;
import me.m56738.easyarmorstands.editor.node.ToolMenuManager;
import me.m56738.easyarmorstands.editor.node.ToolMenuMode;
import me.m56738.easyarmorstands.editor.tool.DelegateToolProvider;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class ArmorStandPositionNode extends PropertyMenuNode implements ResettableNode {
    private final Session session;
    private final Component name;
    private final ToolMenuManager toolManager;

    public ArmorStandPositionNode(Session session, Component name, PropertyContainer properties, OffsetProvider offsetProvider, ArmorStandElement element) {
        super(session, properties);
        this.session = session;
        this.name = name;
        this.toolManager = new ToolMenuManager(session, this,
                new DelegateToolProvider(element.getTools(properties),
                        new EntityPositionProvider(properties, offsetProvider),
                        new EntityYawRotationProvider(properties)));
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
}
