package me.m56738.easyarmorstands.editor.armorstand.layer;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.layer.ResettableLayer;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.editor.input.ReturnInput;
import me.m56738.easyarmorstands.editor.tool.DelegateToolProvider;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.platform.util.Location;
import org.jetbrains.annotations.NotNull;

public class ArmorStandPositionLayer extends ArmorStandLayer implements ResettableLayer {
    private final Session session;

    public ArmorStandPositionLayer(Session session, PropertyContainer properties, OffsetProvider offsetProvider, ArmorStandElement element) {
        super(session, properties, element);
        this.session = session;
        addNode(session.nodeProvider().tools(new DelegateToolProvider(element.getTools(properties),
                new EntityPositionProvider(properties, offsetProvider),
                null)));
    }

    @Override
    public void reset() {
        Property<Location> locationProperty = properties().get(EntityPropertyTypes.LOCATION);
        locationProperty.setValue(locationProperty.getValue()
                .withYaw(0)
                .withPitch(0));
        properties().commit();
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        context.addInput(new ReturnInput(session));
    }
}
