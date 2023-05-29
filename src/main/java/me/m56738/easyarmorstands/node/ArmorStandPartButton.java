package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.Nullable;
import org.joml.Intersectiond;
import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandPartButton implements Button {
    private final Session session;
    private final ArmorStand entity;
    private final ArmorStandPart part;
    private final Node node;
    private final Vector3d start = new Vector3d();
    private final Vector3d end = new Vector3d();
    private Vector3dc lookTarget;

    public ArmorStandPartButton(Session session, ArmorStand entity, ArmorStandPart part, Node node) {
        this.session = session;
        this.entity = entity;
        this.part = part;
        this.node = node;
    }

    @Override
    public void update(Vector3dc eyes, Vector3dc target) {
        Location location = entity.getLocation();
        start.set(part.getOffset(entity))
                .rotateY(-Math.toRadians(location.getYaw()))
                .add(location.getX(), location.getY(), location.getZ());
        end.set(part.getLength(entity))
                .rotate(Util.fromEuler(part.getPose(entity), new Quaterniond()))
                .rotateY(-Math.toRadians(location.getYaw()))
                .add(start);
        start.lerp(end, 0.5);

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
    public void showPreview(boolean focused) {
        session.showLine(start, end, focused ? NamedTextColor.YELLOW : NamedTextColor.WHITE, true);
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
