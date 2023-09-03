package me.m56738.easyarmorstands.capability.particle.v1_8;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.capability.particle.DustParticleCapability;
import org.bukkit.Color;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class LineDustParticle extends DustParticle implements LineParticle {
    private final Vector3d center = new Vector3d();
    private final Quaterniond rotation = new Quaterniond();
    private Axis axis = Axis.Z;
    private double length;
    private double offset;

    protected LineDustParticle(DustParticleCapability capability) {
        super(capability);
    }

    @Override
    public void update() {
        Color color = Color.fromRGB(this.color.red(), this.color.green(), this.color.blue());
        Vector3d direction = axis.getDirection().rotate(rotation, new Vector3d());
        Vector3d start = center.fma(offset - length / 2, direction, new Vector3d());
        Vector3d end = center.fma(offset + length / 2, direction, new Vector3d());
        DustParticle.showLine(players, capability,
                start.x(),
                start.y(),
                start.z(),
                end.x() - start.x(),
                end.y() - start.y(),
                end.z() - start.z(),
                color,
                true,
                DustParticle.getCount(capability, length));
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
    public double getLength() {
        return length;
    }

    @Override
    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public double getOffset() {
        return offset;
    }

    @Override
    public void setOffset(double offset) {
        this.offset = offset;
    }
}
