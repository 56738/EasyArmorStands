package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.ButtonResult;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.property.type.PropertyTypes;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.ArmorStandSize;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.function.Consumer;

public class ArmorStandPartButton implements NodeButton {
    private final Session session;
    private final ArmorStandPart part;
    private final Node node;
    private final Vector3d start = new Vector3d();
    private final Vector3d end = new Vector3d();
    private final Vector3d center = new Vector3d();
    private final Quaterniond rotation = new Quaterniond();
    private final LineParticle particle;
    private final Property<Location> locationProperty;
    private final Property<Quaterniondc> poseProperty;
    private final Property<ArmorStandSize> sizeProperty;

    public ArmorStandPartButton(Session session, PropertyContainer container, ArmorStandPart part, Node node) {
        this.session = session;
        this.part = part;
        this.node = node;
        this.particle = session.particleFactory().createLine();
        this.particle.setAxis(Axis.Y);
        this.locationProperty = container.get(PropertyTypes.ENTITY_LOCATION);
        this.poseProperty = container.get(PropertyTypes.ARMOR_STAND_POSE.get(part));
        this.sizeProperty = container.get(PropertyTypes.ARMOR_STAND_SIZE);
    }

    @Override
    public void update() {
        Location location = locationProperty.getValue();
        ArmorStandSize size = sizeProperty.getValue();
        // rotation = combination of yaw and pose
        poseProperty.getValue().rotateLocalY(-Math.toRadians(location.getYaw()), rotation);
        // start = where the bone is attached to the armor stand, depends on yaw
        part.getOffset(size)
                .rotateY(-Math.toRadians(location.getYaw()), start)
                .add(location.getX(), location.getY(), location.getZ());
        // end = where the bone ends, depends on yaw and pose
        part.getLength(size)
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
        return part.getDisplayName();
    }

    @Override
    public Node createNode() {
        return node;
    }
}
