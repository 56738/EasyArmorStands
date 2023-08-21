package me.m56738.easyarmorstands.addon.plotsquared.v6;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.location.Location;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElement;
import me.m56738.easyarmorstands.api.event.player.PlayerCreateElementEvent;
import me.m56738.easyarmorstands.api.event.player.PlayerDestroyElementEvent;
import me.m56738.easyarmorstands.api.event.player.PlayerEditPropertyEvent;
import me.m56738.easyarmorstands.api.event.session.SessionSelectElementEvent;
import me.m56738.easyarmorstands.api.event.session.SessionStartEvent;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.audience.Audience;
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

    private static Audience audience(Player player) {
        return EasyArmorStands.getInstance().getAdventure().player(player);
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
    public void onInitialize(SessionStartEvent event) {
        bypassCache.remove(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSelect(SessionSelectElementEvent event) {
        if (isAllowed(event.getPlayer(), event.getElement().getProperties().get(EntityPropertyTypes.LOCATION).getValue())) {
            return;
        }
        if (event.getPlayer().hasPermission(bypassPermission)) {
            return;
        }
        event.setCancelled(true);
        audience(event.getPlayer()).sendMessage(Message.error("easyarmorstands.error.plotsquared.deny-select"));
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSpawn(PlayerCreateElementEvent event) {
        if (isAllowed(event.getPlayer(), event.getProperties().get(EntityPropertyTypes.LOCATION).getValue())) {
            return;
        }
        if (event.getPlayer().hasPermission(bypassPermission)) {
            return;
        }
        event.setCancelled(true);
        audience(event.getPlayer()).sendMessage(Message.error("easyarmorstands.error.plotsquared.deny-create"));
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEdit(PlayerEditPropertyEvent<?> event) {
        Element element = event.getElement();
        if (!(element instanceof EntityElement<?>)) {
            return;
        }
        Entity entity = ((EntityElement<?>) element).getEntity();
        if (isAllowed(event.getPlayer(), entity.getLocation())) {
            if (event.getProperty().getType() != EntityPropertyTypes.LOCATION) {
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
    public void onDestroy(PlayerDestroyElementEvent event) {
        if (isAllowed(event.getPlayer(), event.getElement().getProperties().get(EntityPropertyTypes.LOCATION).getValue())) {
            return;
        }
        if (bypassCache.computeIfAbsent(event.getPlayer(), this::canBypass)) {
            return;
        }
        event.setCancelled(true);
        audience(event.getPlayer()).sendMessage(Message.error("easyarmorstands.error.plotsquared.deny-destroy"));
    }

    private boolean canBypass(Player player) {
        return player.hasPermission(bypassPermission);
    }
}
