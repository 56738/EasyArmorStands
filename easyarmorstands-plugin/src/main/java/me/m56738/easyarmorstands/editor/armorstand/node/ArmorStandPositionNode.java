package me.m56738.easyarmorstands.editor.armorstand.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.AbstractElementNode;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.editor.util.ToolManager;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.editor.node.ToolModeSwitcher;
import me.m56738.easyarmorstands.editor.tool.DelegateToolProvider;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ArmorStandPositionNode extends AbstractElementNode<ArmorStandElement> implements ResettableNode {
    private final Session session;
    private final ToolManager toolManager;
    private final ToolModeSwitcher toolModeSwitcher;

    public ArmorStandPositionNode(Session session, PropertyContainer properties, OffsetProvider offsetProvider, ArmorStandElement element) {
        super(session, element);
        this.session = session;
        this.toolManager = new ToolManager(session, this,
                new DelegateToolProvider(element.getTools(getContext()),
                        new EntityPositionProvider(properties, offsetProvider),
                        null));
        this.toolModeSwitcher = new ToolModeSwitcher(this.toolManager);
    }

    @Override
    public void reset() {
        Property<Location> locationProperty = getProperties().get(EntityPropertyTypes.LOCATION);
        Location location = locationProperty.getValue();
        location.setYaw(0);
        location.setPitch(0);
        locationProperty.setValue(location);
        getContext().commit();
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
            session.popNode();
            return true;
        }
        return toolModeSwitcher.onClick(context);
    }
}
