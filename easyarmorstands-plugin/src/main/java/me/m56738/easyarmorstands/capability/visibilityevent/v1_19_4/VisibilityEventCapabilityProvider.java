package me.m56738.easyarmorstands.capability.visibilityevent.v1_19_4;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.visibilityevent.VisibilityEventCapability;
import me.m56738.easyarmorstands.capability.visibilityevent.VisibilityEventListener;
import me.m56738.easyarmorstands.util.ReflectionUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerHideEntityEvent;
import org.bukkit.event.player.PlayerShowEntityEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class VisibilityEventCapabilityProvider implements CapabilityProvider<VisibilityEventCapability> {
    @Override
    public boolean isSupported() {
        return ReflectionUtil.hasClass("org.bukkit.event.player.PlayerShowEntityEvent")
                && ReflectionUtil.hasClass("org.bukkit.event.player.PlayerHideEntityEvent");
    }

    @Override
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    @Override
    public VisibilityEventCapability create(Plugin plugin) {
        return new VisibilityEventCapabilityImpl(plugin);
    }

    private static class VisibilityEventCapabilityImpl implements VisibilityEventCapability {
        private final Map<VisibilityEventListener, VisibilityListener> listeners = new HashMap<>();
        private final Plugin plugin;

        private VisibilityEventCapabilityImpl(Plugin plugin) {
            this.plugin = plugin;
        }

        @Override
        public void addListener(VisibilityEventListener listener) {
            VisibilityListener visibilityListener = new VisibilityListener(listener);
            if (listeners.putIfAbsent(listener, visibilityListener) == null) {
                plugin.getServer().getPluginManager().registerEvents(visibilityListener, plugin);
            }
        }

        @Override
        public void removeListener(VisibilityEventListener listener) {
            VisibilityListener visibilityListener = listeners.remove(listener);
            if (visibilityListener != null) {
                HandlerList.unregisterAll(visibilityListener);
            }
        }

        @SuppressWarnings("UnstableApiUsage")
        private static class VisibilityListener implements Listener {
            private final VisibilityEventListener listener;

            private VisibilityListener(VisibilityEventListener listener) {
                this.listener = listener;
            }

            @EventHandler
            public void onShow(PlayerShowEntityEvent event) {
                listener.onVisibilityChanged(event.getPlayer(), event.getEntity(), true);
            }

            @EventHandler
            public void onHide(PlayerHideEntityEvent event) {
                listener.onVisibilityChanged(event.getPlayer(), event.getEntity(), false);
            }
        }
    }
}
