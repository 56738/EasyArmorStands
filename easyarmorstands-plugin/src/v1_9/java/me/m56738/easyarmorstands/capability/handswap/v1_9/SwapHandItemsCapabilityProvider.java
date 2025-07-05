package me.m56738.easyarmorstands.capability.handswap.v1_9;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.handswap.SwapHandItemsCapability;
import me.m56738.easyarmorstands.capability.handswap.SwapHandItemsListener;
import me.m56738.easyarmorstands.config.version.override.BeforeMinorVersionCondition;
import me.m56738.easyarmorstands.config.version.override.VersionOverrideCondition;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class SwapHandItemsCapabilityProvider implements CapabilityProvider<SwapHandItemsCapability> {
    @Override
    public boolean isSupported() {
        try {
            Class.forName("org.bukkit.event.player.PlayerSwapHandItemsEvent");
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
    public SwapHandItemsCapability create(Plugin plugin) {
        return new SwapHandItemsCapabilityImpl(plugin);
    }

    private static class SwapHandItemsCapabilityImpl implements SwapHandItemsCapability {
        private static final VersionOverrideCondition BEFORE_1_12 = new BeforeMinorVersionCondition(12);
        private static final VersionOverrideCondition BEFORE_1_16 = new BeforeMinorVersionCondition(16);
        private final Map<SwapHandItemsListener, SwapListener> listeners = new HashMap<>();
        private final Plugin plugin;
        private final Component key;

        public SwapHandItemsCapabilityImpl(Plugin plugin) {
            this.plugin = plugin;
            if (BEFORE_1_12.testCondition()) {
                key = Component.text("F");
            } else if (BEFORE_1_16.testCondition()) {
                key = Component.keybind("key.swapHands");
            } else {
                key = Component.keybind("key.swapOffhand");
            }
        }

        @Override
        public Component key() {
            return key;
        }

        @Override
        public void addListener(SwapHandItemsListener listener) {
            SwapListener swapListener = new SwapListener(listener);
            if (listeners.putIfAbsent(listener, swapListener) == null) {
                plugin.getServer().getPluginManager().registerEvents(swapListener, plugin);
            }
        }

        @Override
        public void removeListener(SwapHandItemsListener listener) {
            SwapListener swapListener = listeners.remove(listener);
            if (swapListener != null) {
                HandlerList.unregisterAll(swapListener);
            }
        }

        private static class SwapListener implements Listener {
            private final SwapHandItemsListener listener;

            private SwapListener(SwapHandItemsListener listener) {
                this.listener = listener;
            }

            @EventHandler
            public void onSwap(PlayerSwapHandItemsEvent event) {
                if (listener.handleSwap(event.getPlayer())) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
