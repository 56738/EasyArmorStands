package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.node.AxisAlignedBoxButton;
import me.m56738.easyarmorstands.node.Button;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class DisplayButton<T extends Display> extends AxisAlignedBoxButton implements Button {
    private final T entity;

    public DisplayButton(Session session, T entity) {
        super(session);
        this.entity = entity;
    }

    @Override
    protected Vector3dc getPosition() {
        Location location = entity.getLocation();
        return new Vector3d(location.getX(), location.getY() + entity.getDisplayHeight() / 2, location.getZ());
    }

    @Override
    protected Vector3dc getSize() {
        double width = entity.getDisplayWidth();
        double height = entity.getDisplayHeight();
        return new Vector3d(width, height, width);
    }

    @Override
    public Component getName() {
        return Component.text(Util.getId(entity.getUniqueId()));
    }
}
