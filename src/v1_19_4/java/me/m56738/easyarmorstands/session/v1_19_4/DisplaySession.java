package me.m56738.easyarmorstands.session.v1_19_4;

import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.joml.Math;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Matrix4f;
import org.joml.Quaterniond;
import org.joml.Vector3dc;
import org.joml.Vector3f;

public class DisplaySession extends Session {
    private final Display entity;
    private final JOMLMapper mapper;

    public DisplaySession(Player player, Display entity) {
        super(player);
        this.entity = entity;
        try {
            this.mapper = new JOMLMapper();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update() {
        return super.update() && entity.isValid() &&
                getPlayer().getEyeLocation().distanceSquared(entity.getLocation()) < 100 * 100;
    }

    @Override
    public double getScale() {
        Vector3f scale = mapper.getScale(entity.getTransformation());
        return Math.abs(scale.get(scale.maxComponent()));
    }

    public Display getEntity() {
        return entity;
    }

    public Matrix4d getTransformation() {
        return new Matrix4d(mapper.getMatrix(entity.getTransformation()));
    }

    public void setTransformation(Matrix4dc transformation) {
        entity.setTransformation(mapper.getTransformation(new Matrix4f(transformation)));
    }

    public Vector3dc getPosition() {
        return Util.toVector3d(entity.getLocation());
    }

    public Matrix3dc getRotation() {
        return new Matrix3d().rotation(getTransformation().getUnnormalizedRotation(new Quaterniond()));
    }

    public boolean move(Vector3dc position) {
        entity.teleport(new Location(entity.getWorld(), position.x(), position.y(), position.z()));
        return true;
    }
}
