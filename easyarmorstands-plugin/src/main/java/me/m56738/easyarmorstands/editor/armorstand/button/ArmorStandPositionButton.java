package me.m56738.easyarmorstands.editor.armorstand.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.editor.armorstand.node.ArmorStandPositionNode;
import me.m56738.easyarmorstands.editor.button.NodeFactoryButton;
import me.m56738.easyarmorstands.editor.button.SimpleButton;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.EasMath;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class ArmorStandPositionButton extends SimpleButton implements NodeFactoryButton {
    private final Session session;
    private final Component name;
    private final PropertyContainer container;
    private final Property<Location> locationProperty;
    private final OffsetProvider offsetProvider;
    private final ArmorStandElement element;

    public ArmorStandPositionButton(Session session, ParticleColor color, Component name, PropertyContainer container, OffsetProvider offsetProvider, ArmorStandElement element) {
        super(session, color);
        this.session = session;
        this.name = name;
        this.container = container;
        this.locationProperty = container.get(EntityPropertyTypes.LOCATION);
        this.offsetProvider = offsetProvider;
        this.element = element;
        setPriority(1);
    }

    @Override
    protected Vector3dc getPosition() {
        return Util.toVector3d(locationProperty.getValue()).add(offsetProvider.getOffset());
    }

    @Override
    protected Quaterniondc getRotation() {
        return EasMath.getEntityYawRotation(locationProperty.getValue().getYaw(), new Quaterniond());
    }

    @Override
    protected boolean isBillboard() {
        return false;
    }

    @Override
    public @NotNull Component getName() {
        return name;
    }

    @Override
    public Node createNode() {
        return new ArmorStandPositionNode(session, Message.component("easyarmorstands.node.select-axis"), container, offsetProvider, element);
    }
}
