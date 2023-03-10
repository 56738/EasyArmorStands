package me.m56738.easyarmorstands.capability.persistence.v1_13;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.persistence.PersistenceCapability;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

public class PersistenceCapabilityProvider implements CapabilityProvider<PersistenceCapability> {
    @Override
    public boolean isSupported() {
        try {
            Entity.class.getMethod("isPersistent");
            Entity.class.getMethod("setPersistent", boolean.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    @Override
    public PersistenceCapability create(Plugin plugin) {
        return new PersistenceCapabilityImpl();
    }

    @SuppressWarnings("deprecation")
    private static class PersistenceCapabilityImpl implements PersistenceCapability {

        @Override
        public void setPersistent(Entity entity, boolean persistent) {
            entity.setPersistent(persistent);
        }
    }
}
