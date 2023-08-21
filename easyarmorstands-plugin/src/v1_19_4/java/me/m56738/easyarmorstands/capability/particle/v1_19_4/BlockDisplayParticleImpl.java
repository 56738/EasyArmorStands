package me.m56738.easyarmorstands.capability.particle.v1_19_4;

import me.m56738.easyarmorstands.api.particle.ColoredParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.BlockDisplay;

public abstract class BlockDisplayParticleImpl extends DisplayParticleImpl<BlockDisplay> implements ColoredParticle {
    private ParticleColor color = ParticleColor.WHITE;
    private Material material = Material.WHITE_CONCRETE;

    public BlockDisplayParticleImpl(World world) {
        super(BlockDisplay.class, world);
    }

    @Override
    public ParticleColor getColor() {
        return color;
    }

    @Override
    public void setColor(ParticleColor color) {
        if (this.color == color) {
            return;
        }
        this.color = color;
        switch (color) {
            case WHITE:
                this.material = Material.WHITE_CONCRETE;
                break;
            case RED:
                this.material = Material.RED_CONCRETE;
                break;
            case GREEN:
                this.material = Material.LIME_CONCRETE;
                break;
            case BLUE:
                this.material = Material.BLUE_CONCRETE;
                break;
            case YELLOW:
                this.material = Material.YELLOW_CONCRETE;
                break;
            case GRAY:
                this.material = Material.LIGHT_GRAY_CONCRETE;
                break;
            case AQUA:
                this.material = Material.LIGHT_BLUE_CONCRETE;
                break;
        }
        markDirty();
    }

    @Override
    protected void update(BlockDisplay entity) {
        super.update(entity);
        entity.setBlock(material.createBlockData());
        entity.setGlowColorOverride(Util.toColor(color));
    }
}
