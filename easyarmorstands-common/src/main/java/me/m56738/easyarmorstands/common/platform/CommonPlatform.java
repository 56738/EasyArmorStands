package me.m56738.easyarmorstands.common.platform;

import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.gizmo.api.GizmoFactory;
import org.incendo.cloud.parser.ParserDescriptor;

import java.io.Closeable;

public interface CommonPlatform extends Platform, Closeable {
    GizmoFactory getGizmoFactory(Player player);

    @Override
    void close();

    <C> ParserDescriptor<C, Location> getLocationParser();
}
