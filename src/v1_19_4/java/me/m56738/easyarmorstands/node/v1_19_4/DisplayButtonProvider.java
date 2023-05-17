package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.addon.display.DisplayAddon;
import me.m56738.easyarmorstands.session.EntityButtonProvider;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;

import java.util.Locale;

public class DisplayButtonProvider<T extends Display> implements EntityButtonProvider {
    private final Class<T> type;
    private final DisplayAddon addon;
    private final DisplayRootNodeFactory<T> factory;

    public DisplayButtonProvider(Class<T> type, DisplayAddon addon, DisplayRootNodeFactory<T> factory) {
        this.type = type;
        this.addon = addon;
        this.factory = factory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DisplayButton<T> createButton(Session session, Entity entity) {
        if (!type.isAssignableFrom(entity.getClass())) {
            return null;
        }

        String typeName = entity.getType().name().toLowerCase(Locale.ROOT).replace("_", "");
        if (!session.getPlayer().hasPermission("easyarmorstands.edit." + typeName)) {
            return null;
        }

        return new DisplayButton<>(session, (T) entity, addon, factory);
    }
}
