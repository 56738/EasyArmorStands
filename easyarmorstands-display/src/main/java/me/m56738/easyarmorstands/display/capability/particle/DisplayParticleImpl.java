package me.m56738.easyarmorstands.display.capability.particle;

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
        entity.setInterpolationDuration(1);
    }

    @Override
    protected void update(T entity) {
        super.update(entity);
        entity.setInterpolationDelay(0);
    }
}
