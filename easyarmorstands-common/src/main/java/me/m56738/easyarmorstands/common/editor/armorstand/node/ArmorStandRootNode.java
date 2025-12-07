package me.m56738.easyarmorstands.common.editor.armorstand.node;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.AbstractElementNode;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.EulerAngles;
import me.m56738.easyarmorstands.common.editor.armorstand.ArmorStandOffsetProvider;
import me.m56738.easyarmorstands.common.editor.armorstand.button.ArmorStandPartButton;
import me.m56738.easyarmorstands.common.editor.armorstand.button.ArmorStandPositionButton;
import me.m56738.easyarmorstands.common.editor.input.OpenElementMenuInput;
import me.m56738.easyarmorstands.common.editor.input.ReturnInput;
import me.m56738.easyarmorstands.common.element.ArmorStandElement;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.permission.Permissions;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;

import java.util.EnumMap;

@NullMarked
public class ArmorStandRootNode extends AbstractElementNode<ArmorStandElement> implements ResettableNode {
    private final ArmorStandPositionButton positionButton;
    private final EnumMap<ArmorStandPart, ArmorStandPartButton> partButtons = new EnumMap<>(ArmorStandPart.class);
    private final Component name;
    private final boolean allowMenu;

    public ArmorStandRootNode(Session session, ArmorStandElement element) {
        super(session, element);
        this.name = Message.component("easyarmorstands.node.select-bone");
        this.allowMenu = session.player().hasPermission(Permissions.OPEN);

        for (ArmorStandPart part : ArmorStandPart.values()) {
            ArmorStandPartButton partButton = new ArmorStandPartButton(session, getProperties(), part, element);
            addButton(partButton);
            partButtons.put(part, partButton);
        }

        positionButton = new ArmorStandPositionButton(
                session,
                ParticleColor.WHITE,
                Message.component("easyarmorstands.node.position"),
                getProperties(),
                new ArmorStandOffsetProvider(element, getProperties()),
                element);
        addButton(positionButton);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        context.setActionBar(name);
        if (allowMenu) {
            context.addInput(new OpenElementMenuInput(getSession(), getElement()));
        }
        context.addInput(new ReturnInput(getSession()));
    }

    public ArmorStandPositionButton getPositionButton() {
        return positionButton;
    }

    public ArmorStandPartButton getPartButton(ArmorStandPart part) {
        return partButtons.get(part);
    }

    @Override
    public void reset() {
        Property<Location> locationProperty = getProperties().get(EntityPropertyTypes.LOCATION);
        locationProperty.setValue(locationProperty.getValue().withRotation(0, 0));
        for (ArmorStandPart part : ArmorStandPart.values()) {
            getProperties().get(ArmorStandPropertyTypes.POSE.get(part)).setValue(EulerAngles.ZERO);
        }
        getContext().commit();
    }
}
