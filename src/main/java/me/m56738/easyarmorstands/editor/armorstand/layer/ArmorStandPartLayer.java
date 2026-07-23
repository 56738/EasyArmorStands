package me.m56738.easyarmorstands.editor.armorstand.layer;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.layer.ResettableLayer;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.editor.input.ReturnInput;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.element.ArmorStandPartToolProvider;
import me.m56738.easyarmorstands.platform.util.Rotations;
import org.jetbrains.annotations.NotNull;

public class ArmorStandPartLayer extends ArmorStandLayer implements ResettableLayer {
    private final Session session;
    private final ArmorStandPart part;

    public ArmorStandPartLayer(Session session, PropertyContainer container, ArmorStandPart part, ArmorStandElement element) {
        super(session, container, element);
        this.session = session;
        this.part = part;
        addNode(session.nodeProvider().tools(new ArmorStandPartToolProvider(container, part, element, element.getTools(container))));
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        context.addInput(new ReturnInput(session));
    }

    @Override
    public void reset() {
        properties().get(ArmorStandPropertyTypes.POSE.get(part)).setValue(Rotations.ZERO);
        properties().commit();
    }
}
