package me.m56738.easyarmorstands.bukkit.feature.v1_18;

import io.papermc.paper.event.player.PlayerArmSwingEvent;
import me.m56738.easyarmorstands.bukkit.EasyArmorStands;
import me.m56738.easyarmorstands.bukkit.feature.ArmSwingListener;
import me.m56738.easyarmorstands.bukkit.platform.BukkitListener;
import me.m56738.easyarmorstands.bukkit.platform.BukkitPlatform;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.EntityEquipment;

public class ArmSwingListenerImpl implements ArmSwingListener {
    private final BukkitPlatform platform = EasyArmorStands.getInstance().getPlatform();

    @EventHandler
    public void onArmSwing(PlayerArmSwingEvent event) {
        EntityEquipment equipment = event.getPlayer().getEquipment();
        for (BukkitListener listener : platform.listeners()) {
            if (listener.onLeftClick(event.getPlayer(), equipment.getItem(event.getHand()))) {
                event.setCancelled(true);
            }
        }
    }

    public static class Provider implements ArmSwingListener.Provider {
        @Override
        public boolean isSupported() {
            try {
                Class.forName("io.papermc.paper.event.player.PlayerArmSwingEvent");
                return true;
            } catch (Throwable e) {
                return false;
            }
        }

        @Override
        public ArmSwingListener create() {
            return new ArmSwingListenerImpl();
        }
    }
}
