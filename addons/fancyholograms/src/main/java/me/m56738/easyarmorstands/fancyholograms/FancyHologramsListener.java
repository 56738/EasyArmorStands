package me.m56738.easyarmorstands.fancyholograms;

import me.m56738.easyarmorstands.api.editor.layer.ElementSelectionLayer;
import me.m56738.easyarmorstands.api.event.session.SessionStartEvent;
import me.m56738.easyarmorstands.fancyholograms.element.HologramElementDiscoverySource;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FancyHologramsListener implements Listener {
    private final HologramElementDiscoverySource discoverySource;

    public FancyHologramsListener(HologramElementDiscoverySource discoverySource) {
        this.discoverySource = discoverySource;
    }

    @EventHandler
    public void onSessionStart(SessionStartEvent event) {
        ElementSelectionLayer layer = event.getSession().findLayer(ElementSelectionLayer.class);
        if (layer != null) {
            layer.addSource(discoverySource);
        }
    }
}
