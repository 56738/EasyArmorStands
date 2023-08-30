package me.m56738.easyarmorstands.capability.particle.v1_8;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.particle.CircleParticle;
import me.m56738.easyarmorstands.capability.particle.DustParticleCapability;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class CircleDustParticle extends DustParticle implements CircleParticle {
    private final Vector3d center = new Vector3d();
    private final Quaterniond rotation = new Quaterniond();
    private Axis axis;
    private double radius;

    protected CircleDustParticle(DustParticleCapability capability) {
        super(capability);
    }

    @Override
    public void update() {
        Axis side = axis == Axis.Y ? Axis.X : Axis.Y;
        Color color = Color.fromRGB(this.color.red(), this.color.green(), this.color.blue());
        double circumference = 2 * Math.PI * radius;
        int count = Math.min((int) Math.round(circumference * capability.getDensity()), 100);
        Vector3d offset = side.getDirection().mul(radius, new Vector3d()).rotate(rotation);
        Vector3dc axisDirection = axis.getDirection().rotate(rotation, new Vector3d());
        double axisX = axisDirection.x();
        double axisY = axisDirection.y();
        double axisZ = axisDirection.z();
        double angle = 2 * Math.PI / count;
        for (int i = 0; i < count; i++) {
            offset.rotateAxis(angle, axisX, axisY, axisZ);
            for (Player player : players) {
                capability.spawnParticle(player,
                        center.x() + offset.x,
                        center.y() + offset.y,
                        center.z() + offset.z,
                        color);
            }
        }
    }

    @Override
    public @NotNull Vector3dc getCenter() {
        return center;
    }

    @Override
    public void setCenter(@NotNull Vector3dc center) {
        this.center.set(center);
    }

    @Override
    public @NotNull Axis getAxis() {
        return axis;
    }

    @Override
    public void setAxis(@NotNull Axis axis) {
        this.axis = axis;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public void setWidth(double width) {
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return rotation;
    }

    @Override
    public void setRotation(@NotNull Quaterniondc rotation) {
        this.rotation.set(rotation);
    }

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public void setRadius(double radius) {
        this.radius = radius;
    }
}
