package me.m56738.easyarmorstands.addon.plotsquared.v6;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.location.Location;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import me.m56738.easyarmorstands.event.PlayerDestroyEntityEvent;
import me.m56738.easyarmorstands.event.PlayerEditEntityPropertyEvent;
import me.m56738.easyarmorstands.event.PlayerPreSpawnEntityEvent;
import me.m56738.easyarmorstands.event.SessionInitializeEvent;
import me.m56738.easyarmorstands.event.SessionSelectEntityEvent;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Map;
import java.util.WeakHashMap;

public class PlotSquaredListener implements Listener {
    private final PlotAPI api;
    private final Map<Player, Boolean> bypassCache = new WeakHashMap<>();
    private final String bypassPermission = "easyarmorstands.plotsquared.bypass";

    public PlotSquaredListener(PlotAPI api) {
        this.api = api;
    }

    private boolean isAllowed(Player player, org.bukkit.Location location) {
        Location plotLocation = BukkitUtil.adapt(location);
        PlotArea area = api.getPlotSquared().getPlotAreaManager().getPlotArea(plotLocation);
        if (area != null) {
            Plot plot = area.getPlot(plotLocation);
            if (plot != null) {
                return plot.isAdded(BukkitUtil.adapt(player).getUUID());
            }
        }
        return false;
    }

    @EventHandler
    public void onInitialize(SessionInitializeEvent event) {
        bypassCache.remove(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSelect(SessionSelectEntityEvent event) {
        Entity entity = event.getEntity();
        if (isAllowed(event.getPlayer(), entity.getLocation())) {
            return;
        }
        if (event.getPlayer().hasPermission(bypassPermission)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSpawn(PlayerPreSpawnEntityEvent event) {
        if (isAllowed(event.getPlayer(), event.getLocation())) {
            return;
        }
        if (event.getPlayer().hasPermission(bypassPermission)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEdit(PlayerEditEntityPropertyEvent<?, ?> event) {
        if (isAllowed(event.getPlayer(), event.getEntity().getLocation())) {
            if (!(event.getProperty() instanceof EntityLocationProperty)) {
                return;
            }
            if (isAllowed(event.getPlayer(), (org.bukkit.Location) event.getNewValue())) {
                return;
            }
        }
        if (bypassCache.computeIfAbsent(event.getPlayer(), this::canBypass)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDestroy(PlayerDestroyEntityEvent event) {
        if (isAllowed(event.getPlayer(), event.getEntity().getLocation())) {
            return;
        }
        if (bypassCache.computeIfAbsent(event.getPlayer(), this::canBypass)) {
            return;
        }
        event.setCancelled(true);
    }

    private boolean canBypass(Player player) {
        return player.hasPermission(bypassPermission);
    }
}
