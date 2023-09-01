package me.m56738.easyarmorstands.display.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.display.DisplayBox;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.display.editor.DisplayBoxPositionProvider;
import me.m56738.easyarmorstands.display.editor.DisplaySidePositionProvider;
import me.m56738.easyarmorstands.display.editor.button.DisplayBoxResizeButton;
import me.m56738.easyarmorstands.display.editor.tool.DisplayBoxResizeTool;
import me.m56738.easyarmorstands.display.editor.tool.DisplayBoxToolProvider;
import me.m56738.easyarmorstands.display.element.DisplayElement;
import me.m56738.easyarmorstands.editor.node.ToolMenuManager;
import me.m56738.easyarmorstands.editor.node.ToolMenuMode;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3fc;

public class DisplayBoxNode extends DisplayMenuNode implements ResettableNode {
    private final Session session;
    private final PropertyContainer properties;
    private final Component name;
    private final Property<Location> locationProperty;
    private final Property<Vector3fc> translationProperty;

    public DisplayBoxNode(Session session, PropertyContainer properties) {
        super(session, properties);
        this.session = session;
        this.properties = properties;
        this.name = Message.component("easyarmorstands.node.display.box").color(NamedTextColor.GOLD);
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.translationProperty = properties.get(DisplayPropertyTypes.TRANSLATION);
        setShowBoundingBoxIfInactive(true); // bounding box should remain visible while a tool node is active
        new ToolMenuManager(session, this, new DisplayBoxToolProvider(properties, new DisplayBoxPositionProvider(properties)))
                .setMode(ToolMenuMode.GLOBAL);
        for (Axis axis : Axis.values()) {
            addAxisEnd(axis, false);
            addAxisEnd(axis, true);
        }
    }

    private void addAxisEnd(Axis axis, boolean end) {
        Component name;
        if (axis == Axis.Y) {
            name = Message.component("easyarmorstands.node.display.box.height");
        } else {
            name = Message.component("easyarmorstands.node.display.box.width");
        }
        addButton(new DisplayBoxResizeButton(
                session,
                name.color(NamedTextColor.AQUA),
                ParticleColor.AQUA,
                new DisplayBoxResizeTool(
                        properties,
                        new DisplaySidePositionProvider(properties, axis, end),
                        RotationProvider.identity(),
                        axis,
                        end)));
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
        DisplayBox box = new DisplayBox(properties);
        box.setPosition(Util.toVector3d(locationProperty.getValue()).add(translationProperty.getValue()));
    }
}
