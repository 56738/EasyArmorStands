package me.m56738.easyarmorstands.capability.particle.v1_19_4;

import org.bukkit.World;
import org.bukkit.entity.Display;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public abstract class DisplayParticleImpl<T extends Display> extends ParticleImpl<T> {
    private static final MethodHandle setTeleportDuration = findSetTeleportDuration();

    public DisplayParticleImpl(Class<T> type, World world) {
        super(type, world);
    }

    @SuppressWarnings("JavaLangInvokeHandleSignature")
    private static MethodHandle findSetTeleportDuration() {
        try {
            return MethodHandles.lookup()
                    .findVirtual(Display.class, "setTeleportDuration", MethodType.methodType(void.class, int.class));
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    @Override
    protected void configure(T entity) {
        super.configure(entity);
        entity.setBrightness(new Display.Brightness(15, 0));
        if (setTeleportDuration != null) {
            entity.setInterpolationDuration(1);
            try {
                setTeleportDuration.invoke(entity, 1);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void update(T entity) {
        super.update(entity);
        if (setTeleportDuration != null) {
            entity.setInterpolationDelay(0);
        }
    }
}
