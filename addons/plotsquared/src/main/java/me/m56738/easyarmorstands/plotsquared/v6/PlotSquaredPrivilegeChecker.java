package me.m56738.easyarmorstands.plotsquared.v6;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.region.RegionPrivilegeChecker;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlotSquaredPrivilegeChecker implements RegionPrivilegeChecker {
    private final PlotAPI api;

    public PlotSquaredPrivilegeChecker(PlotAPI api) {
        this.api = api;
    }

    @Override
    public boolean isAllowed(Player player, Location location) {
        com.plotsquared.core.location.Location plotLocation = BukkitUtil.adapt(location);
        PlotArea area = api.getPlotSquared().getPlotAreaManager().getPlotArea(plotLocation);
        if (area == null) {
            // Not in a plot area
            return true;
        }
        Plot plot = area.getPlot(plotLocation);
        if (plot == null) {
            // In a plot area, but not in a plot
            return false;
        }
        // In a plot
        return plot.isAdded(BukkitUtil.adapt(player).getUUID());
    }

    @Override
    public boolean canBypass(Player player) {
        return player.hasPermission(Permissions.PLOTSQUARED_BYPASS);
    }

    @Override
    public void sendCreateError(@NotNull Player player, @NotNull PropertyContainer properties) {
        player.sendMessage(Message.error("easyarmorstands.error.plotsquared.deny-create"));
    }

    @Override
    public void sendDestroyError(@NotNull Player player, @NotNull Element element) {
        player.sendMessage(Message.error("easyarmorstands.error.plotsquared.deny-destroy"));
    }

    @Override
    public void sendEditError(@NotNull Player player, @NotNull Element element) {
        player.sendMessage(Message.error("easyarmorstands.error.plotsquared.deny-select"));
    }
}
