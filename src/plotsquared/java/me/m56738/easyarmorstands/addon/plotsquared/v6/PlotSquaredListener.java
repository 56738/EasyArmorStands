package me.m56738.easyarmorstands.addon.plotsquared.v6;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.location.Location;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import me.m56738.easyarmorstands.event.SessionEditEntityEvent;
import me.m56738.easyarmorstands.event.SessionPreSpawnEvent;
import me.m56738.easyarmorstands.event.SessionSelectEntityEvent;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Map;
import java.util.WeakHashMap;

public class PlotSquaredListener implements Listener {
    private final PlotAPI api;
    private final Map<Session, Boolean> bypassCache = new WeakHashMap<>();
    private final String bypassPermission = "easyarmorstands.plotsquared.bypass";

    public PlotSquaredListener(PlotAPI api) {
        this.api = api;
    }

    private boolean isAllowed(Player player, Location location) {
        PlotArea area = api.getPlotSquared().getPlotAreaManager().getPlotArea(location);
        if (area != null) {
            Plot plot = area.getPlot(location);
            if (plot != null) {
                return plot.isAdded(BukkitUtil.adapt(player).getUUID());
            }
        }
        return false;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onStartSession(SessionSelectEntityEvent event) {
        Entity entity = event.getEntity();
        Location location = BukkitUtil.adapt(entity.getLocation());
        if (isAllowed(event.getPlayer(), location)) {
            return;
        }
        if (event.getPlayer().hasPermission(bypassPermission)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSpawn(SessionPreSpawnEvent event) {
        Location location = BukkitUtil.adapt(event.getLocation());
        if (isAllowed(event.getPlayer(), location)) {
            return;
        }
        if (event.getPlayer().hasPermission(bypassPermission)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMoveSession(SessionEditEntityEvent<?, ?> event) {
        if (!(event.getProperty() instanceof EntityLocationProperty)) {
            return;
        }
        Location location = BukkitUtil.adapt((org.bukkit.Location) event.getNewValue());
        if (isAllowed(event.getPlayer(), location)) {
            return;
        }
        if (bypassCache.computeIfAbsent(event.getSession(), this::canBypass)) {
            return;
        }
        event.setCancelled(true);
    }

    private boolean canBypass(Session session) {
        return session.getPlayer().hasPermission(bypassPermission);
    }
}
