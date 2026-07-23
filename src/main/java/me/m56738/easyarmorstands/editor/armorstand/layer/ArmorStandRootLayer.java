package me.m56738.easyarmorstands.editor.armorstand.layer;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.layer.ElementLayer;
import me.m56738.easyarmorstands.api.editor.layer.ResettableLayer;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.input.ReturnInput;
import me.m56738.easyarmorstands.editor.node.ArmorStandNode;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.platform.entity.ArmorStand;
import me.m56738.easyarmorstands.platform.util.Location;
import me.m56738.easyarmorstands.platform.util.Rotations;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class ArmorStandRootLayer extends ArmorStandLayer implements ElementLayer, ResettableLayer {
    private final Session session;
    private final ArmorStand entity;
    private final Component name;

    public ArmorStandRootLayer(EasyArmorStandsCommon eas, Session session, ArmorStand entity, ArmorStandElement element) {
        super(session, session.properties(element), element);
        this.session = session;
        this.entity = entity;
        this.name = Message.component("easyarmorstands.node.select-bone");
        addNode(new ArmorStandNode(eas, session, element));
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        context.setActionBar(name);
        context.addInput(new ReturnInput(session));
    }

    @Override
    public void onInactiveUpdate(@NotNull UpdateContext context) {
        super.onInactiveUpdate(context);
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public void reset() {
        Property<Location> locationProperty = properties().get(EntityPropertyTypes.LOCATION);
        locationProperty.setValue(locationProperty.getValue()
                .withYaw(0)
                .withPitch(0));
        for (ArmorStandPart part : ArmorStandPart.values()) {
            properties().get(ArmorStandPropertyTypes.POSE.get(part)).setValue(Rotations.ZERO);
        }
        properties().commit();
    }
}
