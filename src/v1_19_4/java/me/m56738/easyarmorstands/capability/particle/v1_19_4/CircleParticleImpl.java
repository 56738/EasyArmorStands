package me.m56738.easyarmorstands.capability.particle.v1_19_4;

import me.m56738.easyarmorstands.particle.CircleParticle;
import me.m56738.easyarmorstands.particle.LineParticle;
import me.m56738.easyarmorstands.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Axis;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class CircleParticleImpl implements CircleParticle {
    private final int count = 20;
    private final Vector3d center = new Vector3d();
    private final Quaterniond rotation = new Quaterniond();
    private final LineParticle[] lines = new LineParticle[count];
    private Axis axis = Axis.Y;
    private double radius;
    private boolean dirty;

    public CircleParticleImpl(World world, JOMLMapper mapper) {
        for (int i = 0; i < count; i++) {
            lines[i] = new LineParticleImpl(world, mapper);
        }
    }

    @Override
    public ParticleColor getColor() {
        return lines[0].getColor();
    }

    @Override
    public void setColor(ParticleColor color) {
        for (LineParticle line : lines) {
            line.setColor(color);
        }
    }

    @Override
    public Vector3dc getCenter() {
        return center;
    }

    @Override
    public void setCenter(Vector3dc center) {
        if (!this.center.equals(center, 1e-6)) {
            this.center.set(center);
            dirty = true;
        }
    }

    @Override
    public Axis getAxis() {
        return axis;
    }

    @Override
    public void setAxis(Axis axis) {
        if (this.axis != axis) {
            this.axis = axis;
            dirty = true;
        }
    }

    @Override
    public double getWidth() {
        return lines[0].getWidth();
    }

    @Override
    public void setWidth(double width) {
        if (lines[0].getWidth() != width) {
            for (LineParticle line : lines) {
                line.setWidth(width);
            }
            dirty = true;
        }
    }

    @Override
    public Quaterniondc getRotation() {
        return rotation;
    }

    @Override
    public void setRotation(Quaterniondc rotation) {
        if (!this.rotation.equals(rotation, 1e-6)) {
            this.rotation.set(rotation);
            dirty = true;
        }
    }

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public void setRadius(double radius) {
        if (this.radius != radius) {
            this.radius = radius;
            dirty = true;
        }
    }

    private void updateCircle() {
        Axis forward = axis == Axis.Z ? Axis.X : Axis.Z;
        Axis side = axis == Axis.Y ? Axis.X : Axis.Y;

        Quaterniond currentRotation = new Quaterniond(rotation);
        Vector3d offset = side.getDirection().mul(radius, new Vector3d());
        double angle = 2 * Math.PI / count;
        Vector3dc axisDirection = axis.getDirection();
        double axisX = axisDirection.x();
        double axisY = axisDirection.y();
        double axisZ = axisDirection.z();
        double length = 2 * Math.tan(angle / 2) * (radius + getWidth() / 2);
        for (int i = 0; i < count; i++) {
            lines[i].setCenter(offset.rotate(currentRotation, new Vector3d()).add(center));
            lines[i].setRotation(currentRotation);
            lines[i].setLength(length);
            lines[i].setAxis(forward);
            currentRotation.rotateAxis(angle, axisX, axisY, axisZ);
        }
        dirty = false;
    }

    @Override
    public void show(Player player) {
        updateCircle();
        for (LineParticle line : lines) {
            line.show(player);
        }
    }

    @Override
    public void update() {
        if (dirty) {
            updateCircle();
        }
        for (LineParticle line : lines) {
            line.update();
        }
    }

    @Override
    public void hide(Player player) {
        for (LineParticle line : lines) {
            line.hide(player);
        }
    }
}
