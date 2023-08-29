package me.m56738.easyarmorstands.editor.armorstand.button;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.ButtonResult;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.armorstand.node.ArmorStandPartNode;
import me.m56738.easyarmorstands.editor.button.NodeFactoryButton;
import me.m56738.easyarmorstands.util.ArmorStandPartInfo;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.util.EulerAngle;
import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.function.Consumer;

public class ArmorStandPartButton implements NodeFactoryButton {
    private final Session session;
    private final PropertyContainer container;
    private final ArmorStandPart part;
    private final ArmorStandPartInfo partInfo;
    private final Vector3d start = new Vector3d();
    private final Vector3d end = new Vector3d();
    private final Vector3d center = new Vector3d();
    private final Quaterniond rotation = new Quaterniond();
    private final LineParticle particle;
    private final Property<Location> locationProperty;
    private final Property<EulerAngle> poseProperty;
    private final Property<ArmorStandSize> sizeProperty;

    public ArmorStandPartButton(Session session, PropertyContainer container, ArmorStandPart part) {
        this.session = session;
        this.container = container;
        this.part = part;
        this.partInfo = ArmorStandPartInfo.of(part);
        this.particle = session.particleProvider().createLine();
        this.particle.setAxis(Axis.Y);
        this.locationProperty = container.get(EntityPropertyTypes.LOCATION);
        this.poseProperty = container.get(ArmorStandPropertyTypes.POSE.get(part));
        this.sizeProperty = container.get(ArmorStandPropertyTypes.SIZE);
    }

    @Override
    public void update() {
        Location location = locationProperty.getValue();
        ArmorStandSize size = sizeProperty.getValue();
        // rotation = combination of yaw and pose
        Util.fromEuler(poseProperty.getValue(), rotation).rotateLocalY(-Math.toRadians(location.getYaw()));
        // start = where the bone is attached to the armor stand, depends on yaw
        partInfo.getOffset(size)
                .rotateY(-Math.toRadians(location.getYaw()), start)
                .add(location.getX(), location.getY(), location.getZ());
        // end = where the bone ends, depends on yaw and pose
        partInfo.getLength(size)
                .rotate(rotation, end)
                .add(start);
        // move start down, start-end will be the lower 2/3 of the bone
        start.lerp(end, 1.0 / 3);
        start.lerp(end, 0.5, center);
    }

    @Override
    public void intersect(EyeRay ray, Consumer<ButtonResult> results) {
        Vector3dc intersection = ray.intersectLine(start, end);
        if (intersection != null) {
            results.accept(ButtonResult.of(intersection));
        }
    }

    @Override
    public void updatePreview(boolean focused) {
        particle.setRotation(rotation);
        particle.setCenter(center);
        particle.setLength(center.distance(end) * 2);
        particle.setColor(focused ? ParticleColor.YELLOW : ParticleColor.WHITE);
    }

    @Override
    public void showPreview() {
        session.addParticle(particle);
    }

    @Override
    public void hidePreview() {
        session.removeParticle(particle);
    }

    @Override
    public Component getName() {
        return partInfo.getDisplayName();
    }

    @Override
    public Node createNode() {
        return new ArmorStandPartNode(session, container, part);
    }
}
