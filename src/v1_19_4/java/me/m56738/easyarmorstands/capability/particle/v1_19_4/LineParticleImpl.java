package me.m56738.easyarmorstands.capability.particle.v1_19_4;

import me.m56738.easyarmorstands.particle.LineParticle;
import me.m56738.easyarmorstands.util.Axis;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.BlockDisplay;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class LineParticleImpl extends BlockDisplayParticleImpl implements LineParticle {
    private final World world;
    private final JOMLMapper mapper;
    private final Vector3d center = new Vector3d();
    private final Quaternionf rotation = new Quaternionf();
    private Axis axis = Axis.Z;
    private double width = 0.0625;
    private double length;

    public LineParticleImpl(World world, JOMLMapper mapper) {
        super(world);
        this.world = world;
        this.mapper = mapper;
    }

    @Override
    public Vector3dc getCenter() {
        return center;
    }

    @Override
    public void setCenter(Vector3dc center) {
        if (!this.center.equals(center, 1e-6)) {
            this.center.set(center);
            markDirty();
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
            markDirty();
        }
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public void setWidth(double width) {
        if (this.width != width) {
            this.width = width;
            markDirty();
        }
    }

    @Override
    public Quaterniondc getRotation() {
        return new Quaterniond(rotation);
    }

    @Override
    public void setRotation(Quaterniondc rotation) {
        Quaternionf rot = new Quaternionf(rotation);
        if (!this.rotation.equals(rot, 1e-6f)) {
            this.rotation.set(rot);
            markDirty();
        }
    }

    @Override
    public double getLength() {
        return length;
    }

    @Override
    public void setLength(double length) {
        if (this.length != length) {
            this.length = length;
            markDirty();
        }
    }

    @Override
    protected void update(BlockDisplay entity) {
        super.update(entity);
        float mainScale = (float) length;
        float offScale = (float) width;
        Vector3fc scale;
        switch (axis) {
            case X:
                scale = new Vector3f(mainScale, offScale, offScale);
                break;
            case Y:
                scale = new Vector3f(offScale, mainScale, offScale);
                break;
            case Z:
                scale = new Vector3f(offScale, offScale, mainScale);
                break;
            default:
                throw new IllegalArgumentException();
        }
        entity.setTransformation(mapper.getTransformation(
                scale.mul(-0.5f, new Vector3f()).rotate(rotation),
                rotation,
                scale,
                new Quaternionf()));
    }

    @Override
    protected Location getLocation() {
        return new Location(world, center.x(), center.y(), center.z());
    }
}
