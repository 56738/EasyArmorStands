package me.m56738.easyarmorstands.paper.listener;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.editor.layer.ElementSelectionLayer;
import me.m56738.easyarmorstands.api.element.ElementDiscoverySource;
import me.m56738.easyarmorstands.editor.layer.EntityElementDiscoverySource;
import me.m56738.easyarmorstands.platform.paper.entity.PaperEntity;
import me.m56738.easyarmorstands.platform.paper.entity.PaperPlayer;
import me.m56738.easyarmorstands.session.SessionImpl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerHideEntityEvent;
import org.bukkit.event.player.PlayerShowEntityEvent;

public class PaperVisibilityListener implements Listener {
    private final EasyArmorStandsCommon eas;

    public PaperVisibilityListener(EasyArmorStandsCommon eas) {
        this.eas = eas;
    }

    @EventHandler
    public void onShow(PlayerShowEntityEvent event) {
        onVisibilityChanged(event.getPlayer(), event.getEntity());
    }

    @EventHandler
    public void onHide(PlayerHideEntityEvent event) {
        onVisibilityChanged(event.getPlayer(), event.getEntity());
    }

    private void onVisibilityChanged(Player player, Entity entity) {
        eas.platform().getScheduler().runTask(() -> updateEntityVisibility(player, entity));
    }

    private void updateEntityVisibility(Player player, Entity entity) {
        SessionImpl session = eas.sessionManager().getSession(PaperPlayer.fromNative(player));
        if (session == null) {
            return;
        }
        ElementSelectionLayer layer = session.findLayer(ElementSelectionLayer.class);
        if (layer == null) {
            return;
        }
        for (ElementDiscoverySource source : layer.getSources()) {
            if (source instanceof EntityElementDiscoverySource entitySource) {
                layer.refreshEntry(entitySource.getEntry(PaperEntity.fromNative(entity)));
            }
        }
    }
}
