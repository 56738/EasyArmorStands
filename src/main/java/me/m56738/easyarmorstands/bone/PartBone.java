package me.m56738.easyarmorstands.bone;

import me.m56738.easyarmorstands.session.ArmorStandSession;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class PartBone extends AbstractBone {
    private static final double NORMAL_SCALE = 1.0 / 16;
    private static final double SMALL_SCALE = NORMAL_SCALE / 2;
    private static final Matrix3d poseMatrix = new Matrix3d();
    private final ArmorStandSession session;
    private final ArmorStandPart part;
    private final Component component;
    private final Vector3d normalOffset;
    private final Vector3d normalLength;
    private final Vector3d smallOffset;
    private final Vector3d smallLength;

    private final Vector3d start = new Vector3d();
    private final Vector3d end = new Vector3d();
    private final Matrix3d rotation = new Matrix3d();

    public PartBone(ArmorStandSession session, ArmorStandPart part, Component component, Vector3dc offset, Vector3dc length) {
        super(session);
        this.session = session;
        this.part = part;
        this.component = component;
        this.normalOffset = offset.mul(NORMAL_SCALE, new Vector3d());
        this.normalLength = length.mul(NORMAL_SCALE, new Vector3d());
        this.smallOffset = offset.mul(SMALL_SCALE, new Vector3d());
        this.smallLength = length.mul(SMALL_SCALE, new Vector3d());
    }

    private Vector3dc getOffset(ArmorStand entity) {
        if (!entity.isSmall()) {
            return normalOffset;
        } else {
            return smallOffset;
        }
    }

    private Vector3dc getLength(ArmorStand entity) {
        if (!entity.isSmall()) {
            return normalLength;
        } else {
            return smallLength;
        }
    }

    @Override
    public void refresh() {
        ArmorStand entity = session.getEntity();
        Matrix3dc yaw = session.getArmorStandYaw();
        yaw.transform(getOffset(entity), start).add(Util.toVector3d(entity.getLocation()));
        yaw.mul(Util.fromEuler(part.getPose(entity), poseMatrix), rotation);
        rotation.transform(getLength(entity), end).add(start);
    }

    @Override
    public Vector3dc getPosition() {
        return end;
    }

    @Override
    public @NotNull ArmorStandSession getSession() {
        return session;
    }

    @Override
    public Component getName() {
        return component;
    }

    public ArmorStandPart getPart() {
        return part;
    }

    public Vector3dc getAnchor() {
        return start;
    }

    public Matrix3dc getRotation() {
        return rotation;
    }

    public void setRotation(Matrix3dc rotation) {
        this.rotation.set(rotation);
        part.setPose(session.getEntity(), Util.toEuler(poseMatrix.setTransposed(session.getArmorStandYaw()).mul(rotation)));
    }
}
