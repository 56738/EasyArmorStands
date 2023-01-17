package gg.bundlegroup.easyarmorstands.platform.bukkit.v1_9;

import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntityGlowSetter;
import org.bukkit.entity.Entity;

public class EntityGlowSetterImpl implements EntityGlowSetter {
    @Override
    public void setGlowing(Entity entity, boolean glowing) {
        entity.setGlowing(glowing);
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
