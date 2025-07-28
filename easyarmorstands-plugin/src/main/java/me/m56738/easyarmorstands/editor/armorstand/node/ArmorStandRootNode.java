package me.m56738.easyarmorstands.editor.armorstand.node;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.AbstractElementNode;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.EulerAngles;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandOffsetProvider;
import me.m56738.easyarmorstands.editor.armorstand.button.ArmorStandPartButton;
import me.m56738.easyarmorstands.editor.armorstand.button.ArmorStandPositionButton;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;

import java.util.EnumMap;

@NullMarked
public class ArmorStandRootNode extends AbstractElementNode<ArmorStandElement> implements ResettableNode {
    private final ArmorStandPositionButton positionButton;
    private final EnumMap<ArmorStandPart, ArmorStandPartButton> partButtons = new EnumMap<>(ArmorStandPart.class);
    private final Component name;

    public ArmorStandRootNode(Session session, ArmorStandElement element) {
        super(session, element);
        this.name = Message.component("easyarmorstands.node.select-bone");

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
    }

    public ArmorStandPositionButton getPositionButton() {
        return positionButton;
    }

    public ArmorStandPartButton getPartButton(ArmorStandPart part) {
        return partButtons.get(part);
    }

    @Override
    public boolean onClick(@NotNull ClickContext context) {
        Player player = getSession().player();
        if (context.type() == ClickContext.Type.LEFT_CLICK && player.hasPermission(Permissions.OPEN)) {
            getElement().openMenu(player);
            return true;
        }
        return super.onClick(context);
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
