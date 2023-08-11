package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import me.m56738.easyarmorstands.particle.LineParticle;
import me.m56738.easyarmorstands.particle.ParticleColor;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandPoseProperty;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandSizeProperty;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.ArmorStandSize;
import me.m56738.easyarmorstands.util.Axis;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.*;

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
    private Vector3dc lookTarget;

    public ArmorStandPartButton(Session session, PropertyContainer container, ArmorStandPart part, Node node) {
        this.session = session;
        this.part = part;
        this.node = node;
        this.particle = EasyArmorStands.getInstance().getCapability(ParticleCapability.class).createLine(session.getWorld());
        this.particle.setAxis(Axis.Y);
        this.locationProperty = container.get(EntityLocationProperty.TYPE);
        this.poseProperty = container.get(ArmorStandPoseProperty.type(part));
        this.sizeProperty = container.get(ArmorStandSizeProperty.TYPE);
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
    public void updateLookTarget(Vector3dc eyes, Vector3dc target) {
        Vector3d closestOnLookRay = new Vector3d();
        Vector3d closestOnBone = new Vector3d();
        double distanceSquared = Intersectiond.findClosestPointsLineSegments(
                eyes.x(), eyes.y(), eyes.z(),
                target.x(), target.y(), target.z(),
                start.x(), start.y(), start.z(),
                end.x(), end.y(), end.z(),
                closestOnLookRay,
                closestOnBone
        );

        double threshold = session.getLookThreshold();
        if (distanceSquared < threshold * threshold) {
            lookTarget = closestOnLookRay;
        } else {
            lookTarget = null;
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
    public @Nullable Vector3dc getLookTarget() {
        return lookTarget;
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
