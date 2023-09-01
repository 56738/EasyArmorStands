package me.m56738.easyarmorstands.capability.particle.v1_19_4;

import org.bukkit.World;
import org.bukkit.entity.Display;

public abstract class DisplayParticleImpl<T extends Display> extends ParticleImpl<T> {
    public DisplayParticleImpl(Class<T> type, World world) {
        super(type, world);
    }

    @Override
    protected void configure(T entity) {
        super.configure(entity);
        entity.setBrightness(new Display.Brightness(15, 0));
    }
}
