package me.m56738.easyarmorstands.common.editor.armorstand.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.AbstractElementNode;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.editor.util.ToolManager;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.common.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.common.editor.OffsetProvider;
import me.m56738.easyarmorstands.common.editor.input.ReturnInput;
import me.m56738.easyarmorstands.common.editor.node.ToolModeSwitcher;
import me.m56738.easyarmorstands.common.editor.tool.DelegateToolProvider;
import me.m56738.easyarmorstands.common.element.ArmorStandElement;
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
        ToolProvider tools = element.getTools(getContext());
        this.toolManager = new ToolManager(session, this, new DelegateToolProvider(
                tools, tools.context().withPosition(new EntityPositionProvider(properties, offsetProvider))));
        this.toolModeSwitcher = new ToolModeSwitcher(this.toolManager);
    }

    @Override
    public void reset() {
        Property<Location> locationProperty = getProperties().get(EntityPropertyTypes.LOCATION);
        locationProperty.setValue(locationProperty.getValue().withRotation(0, 0));
        getContext().commit();
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        context.setActionBar(toolModeSwitcher.getActionBar());
        toolModeSwitcher.onUpdate(context);
        super.onUpdate(context);
        context.addInput(new ReturnInput(session));
    }
}
