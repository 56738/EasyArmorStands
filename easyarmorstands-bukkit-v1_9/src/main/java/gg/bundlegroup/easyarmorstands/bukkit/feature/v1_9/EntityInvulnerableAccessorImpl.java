package gg.bundlegroup.easyarmorstands.bukkit.feature.v1_9;

import gg.bundlegroup.easyarmorstands.bukkit.feature.EntityInvulnerableAccessor;
import org.bukkit.entity.Entity;

public class EntityInvulnerableAccessorImpl implements EntityInvulnerableAccessor {
    @Override
    public boolean isInvulnerable(Entity entity) {
        return entity.isInvulnerable();
    }

    @Override
    public void setInvulnerable(Entity entity, boolean invulnerable) {
        entity.setInvulnerable(invulnerable);
    }

    public static class Provider implements EntityInvulnerableAccessor.Provider {
        @Override
        public boolean isSupported() {
            try {
                Entity.class.getDeclaredMethod("isInvulnerable");
                Entity.class.getDeclaredMethod("setInvulnerable", boolean.class);
                return true;
            } catch (Throwable e) {
                return false;
            }
        }

        @Override
        public EntityInvulnerableAccessor create() {
            return new EntityInvulnerableAccessorImpl();
        }
    }
}
