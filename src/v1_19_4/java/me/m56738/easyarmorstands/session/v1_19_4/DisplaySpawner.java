package me.m56738.easyarmorstands.session.v1_19_4;

import me.m56738.easyarmorstands.addon.display.DisplayAddon;
import me.m56738.easyarmorstands.node.Node;
import me.m56738.easyarmorstands.node.v1_19_4.DisplayButton;
import me.m56738.easyarmorstands.node.v1_19_4.DisplayRootNodeFactory;
import me.m56738.easyarmorstands.session.EntitySpawner;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.Location;
import org.bukkit.entity.Display;

import java.util.Objects;

public class DisplaySpawner<T extends Display> implements EntitySpawner<T> {
    private final Class<T> type;
    private final Session session;
    private final DisplayAddon addon;
    private final DisplayRootNodeFactory<T> factory;

    public DisplaySpawner(Class<T> type, Session session, DisplayAddon addon, DisplayRootNodeFactory<T> factory) {
        this.type = type;
        this.session = session;
        this.addon = addon;
        this.factory = factory;
    }

    @Override
    public T spawn(Location location) {
        return Objects.requireNonNull(location.getWorld()).spawn(location, type);
    }

    @Override
    public Node createNode(T entity) {
        return new DisplayButton<>(session, entity, addon, factory).createNode();
    }
}
