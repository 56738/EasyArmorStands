package gg.bundlegroup.easyarmorstands.bukkit.feature.v1_18;

import gg.bundlegroup.easyarmorstands.bukkit.EasyArmorStands;
import gg.bundlegroup.easyarmorstands.bukkit.feature.ArmSwingListener;
import gg.bundlegroup.easyarmorstands.bukkit.platform.BukkitListener;
import gg.bundlegroup.easyarmorstands.bukkit.platform.BukkitPlatform;
import io.papermc.paper.event.player.PlayerArmSwingEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.EntityEquipment;

public class ArmSwingListenerImpl implements ArmSwingListener {
    private final BukkitPlatform platform = EasyArmorStands.getInstance().getPlatform();

    @EventHandler
    public void onArmSwing(PlayerArmSwingEvent event) {
        EntityEquipment equipment = event.getPlayer().getEquipment();
        for (BukkitListener listener : platform.listeners()) {
            listener.onLeftClick(event.getPlayer(), equipment.getItem(event.getHand()));
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
