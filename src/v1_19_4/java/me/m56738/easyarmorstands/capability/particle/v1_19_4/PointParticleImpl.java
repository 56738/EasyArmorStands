package me.m56738.easyarmorstands.capability.particle.v1_19_4;

import me.m56738.easyarmorstands.particle.PointParticle;
import me.m56738.easyarmorstands.util.Util;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;

public class PointParticleImpl extends BlockDisplayParticleImpl implements PointParticle {
    private final World world;
    private final JOMLMapper mapper;
    private final Vector3d position = new Vector3d();
    private final Quaternionf rotation = new Quaternionf();
    private double size = Util.PIXEL;
    private boolean billboard = true;

    public PointParticleImpl(World world, JOMLMapper mapper) {
        super(world);
        this.world = world;
        this.mapper = mapper;
    }

    @Override
    public double getSize() {
        return size;
    }

    @Override
    public void setSize(double size) {
        if (this.size != size) {
            this.size = size;
            markDirty();
        }
    }

    @Override
    public Vector3dc getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector3dc position) {
        if (!this.position.equals(position, 1e-6)) {
            this.position.set(position);
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
    public boolean isBillboard() {
        return billboard;
    }

    @Override
    public void setBillboard(boolean billboard) {
        if (this.billboard != billboard) {
            this.billboard = billboard;
            markDirty();
        }
    }

    @Override
    protected void update(BlockDisplay entity) {
        super.update(entity);
        Vector3f scale = new Vector3f((float) size);
        entity.setTransformation(mapper.getTransformation(
                scale.mul(-0.5f, new Vector3f()).rotate(rotation),
                rotation,
                scale,
                new Quaternionf()));
        entity.setBillboard(billboard ? Display.Billboard.CENTER : Display.Billboard.FIXED);
    }

    @Override
    protected Location getLocation() {
        return new Location(world, position.x(), position.y(), position.z());
    }
}