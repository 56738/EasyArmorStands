package me.m56738.easyarmorstands.paper.platform;

import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperLocationAdapter;
import me.m56738.gizmo.api.GizmoFactory;
import me.m56738.gizmo.bukkit.api.BukkitGizmos;
import org.bukkit.plugin.Plugin;
import org.incendo.cloud.bukkit.parser.location.LocationParser;
import org.incendo.cloud.parser.ParserDescriptor;

import java.util.concurrent.CompletableFuture;

public class PaperPlatformImpl implements PaperPlatform, CommonPlatform {
    private final Plugin plugin;
    private final BukkitGizmos gizmos;

    public PaperPlatformImpl(Plugin plugin) {
        this.plugin = plugin;
        this.gizmos = BukkitGizmos.create(plugin);
    }

    @Override
    public String getEasyArmorStandsVersion() {
        return plugin.getPluginMeta().getVersion();
    }

    @Override
    public GizmoFactory getGizmoFactory(Player player) {
        return gizmos.player(PaperPlayer.toNative(player));
    }

    @Override
    public void close() {
        gizmos.close();
    }

    @Override
    public <C> ParserDescriptor<C, Location> getLocationParser() {
        return LocationParser.<C>locationParser().mapSuccess(Location.class,
                (context, location) ->
                        CompletableFuture.completedFuture(PaperLocationAdapter.fromNative(location)));
    }
}
