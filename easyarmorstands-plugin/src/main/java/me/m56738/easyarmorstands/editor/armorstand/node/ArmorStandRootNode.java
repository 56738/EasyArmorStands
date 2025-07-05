package me.m56738.easyarmorstands.editor.armorstand.node;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.editor.node.AbstractNode;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandOffsetProvider;
import me.m56738.easyarmorstands.editor.armorstand.button.ArmorStandPartButton;
import me.m56738.easyarmorstands.editor.armorstand.button.ArmorStandPositionButton;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

public class ArmorStandRootNode extends AbstractNode implements ElementNode, ResettableNode {
    private final Session session;
    private final ArmorStand entity;
    private final ArmorStandElement element;
    private final ArmorStandPositionButton positionButton;
    private final EnumMap<ArmorStandPart, ArmorStandPartButton> partButtons = new EnumMap<>(ArmorStandPart.class);
    private final PropertyContainer container;
    private final Component name;

    public ArmorStandRootNode(Session session, ArmorStand entity, ArmorStandElement element) {
        super(session);
        this.session = session;
        this.entity = entity;
        this.element = element;
        this.container = session.properties(element);
        this.name = Message.component("easyarmorstands.node.select-bone");

        for (ArmorStandPart part : ArmorStandPart.values()) {
            ArmorStandPartButton partButton = new ArmorStandPartButton(session, container, part, element);
            addButton(partButton);
            partButtons.put(part, partButton);
        }

        positionButton = new ArmorStandPositionButton(
                session,
                ParticleColor.WHITE,
                Message.component("easyarmorstands.node.position"),
                container,
                new ArmorStandOffsetProvider(element, container),
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
        Player player = session.player();
        if (context.type() == ClickContext.Type.LEFT_CLICK && player.hasPermission(Permissions.OPEN)) {
            element.openMenu(player);
            return true;
        }
        return super.onClick(context);
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public @NotNull Element getElement() {
        return element;
    }

    @Override
    public void reset() {
        Property<Location> locationProperty = container.get(EntityPropertyTypes.LOCATION);
        Location location = locationProperty.getValue();
        location.setYaw(0);
        location.setPitch(0);
        locationProperty.setValue(location);
        for (ArmorStandPart part : ArmorStandPart.values()) {
            container.get(ArmorStandPropertyTypes.POSE.get(part)).setValue(EulerAngle.ZERO);
        }
        container.commit();
    }
}
