package gg.bundlegroup.easyarmorstands.bukkit.feature.v1_9;

import gg.bundlegroup.easyarmorstands.bukkit.feature.EntityGlowSetter;
import org.bukkit.entity.Entity;

public class EntityGlowSetterImpl implements EntityGlowSetter {
    @Override
    public void setGlowing(Entity entity, boolean glowing) {
        entity.setGlowing(glowing);
    }

    @Override
    public boolean isGlowing(Entity entity) {
        return entity.isGlowing();
    }

    public static class Provider implements EntityGlowSetter.Provider {
        @Override
        public boolean isSupported() {
            try {
                Entity.class.getDeclaredMethod("setGlowing", boolean.class);
                return true;
            } catch (NoSuchMethodException e) {
                return false;
            }
        }

        @Override
        public EntityGlowSetter create() {
            return new EntityGlowSetterImpl();
        }
    }
}
