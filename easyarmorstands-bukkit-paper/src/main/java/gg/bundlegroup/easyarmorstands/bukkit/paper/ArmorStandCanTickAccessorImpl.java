package gg.bundlegroup.easyarmorstands.bukkit.paper;

import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.ArmorStandCanTickAccessor;
import org.bukkit.entity.ArmorStand;

public class ArmorStandCanTickAccessorImpl implements ArmorStandCanTickAccessor {
    @Override
    public boolean canTick(ArmorStand armorStand) {
        return armorStand.canTick();
    }

    @Override
    public void setCanTick(ArmorStand armorStand, boolean canTick) {
        armorStand.setCanTick(canTick);
    }

    public static class Provider implements ArmorStandCanTickAccessor.Provider {
        @Override
        public boolean isSupported() {
            try {
                ArmorStand.class.getDeclaredMethod("canTick");
                ArmorStand.class.getDeclaredMethod("setCanTick", boolean.class);
                return true;
            } catch (NoSuchMethodException e) {
                return false;
            }
        }

        @Override
        public ArmorStandCanTickAccessor create() {
            return new ArmorStandCanTickAccessorImpl();
        }
    }
}
