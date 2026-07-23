package me.m56738.easyarmorstands.paper.listener;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.paper.entity.PaperPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final EasyArmorStandsCommon eas;

    public PlayerListener(EasyArmorStandsCommon eas) {
        this.eas = eas;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = PaperPlayer.fromNative(event.getPlayer());
        eas.getHistoryManager().remove(player);
    }
}
