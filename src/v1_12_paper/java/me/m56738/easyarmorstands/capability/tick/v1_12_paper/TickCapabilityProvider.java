package me.m56738.easyarmorstands.capability.tick.v1_12_paper;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.tick.TickCapability;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.Plugin;

public class TickCapabilityProvider implements CapabilityProvider<TickCapability> {
    @Override
    public boolean isSupported() {
        try {
            ArmorStand.class.getMethod("canTick");
            ArmorStand.class.getMethod("setCanTick", boolean.class);
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
    public TickCapability create(Plugin plugin) {
        return new TickCapabilityImpl();
    }

    private static class TickCapabilityImpl implements TickCapability {
        @Override
        public boolean canTick(ArmorStand armorStand) {
            return armorStand.canTick();
        }

        @Override
        public void setCanTick(ArmorStand armorStand, boolean canTick) {
            armorStand.setCanTick(canTick);
        }
    }
}
