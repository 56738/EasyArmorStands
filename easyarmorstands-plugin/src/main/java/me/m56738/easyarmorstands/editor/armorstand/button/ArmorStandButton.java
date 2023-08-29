package me.m56738.easyarmorstands.editor.armorstand.button;

import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.editor.button.AxisAlignedBoxButton;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandButton extends AxisAlignedBoxButton {
    private final ArmorStand entity;

    public ArmorStandButton(Session session, ArmorStand entity) {
        super(session);
        this.entity = entity;
    }

    @Override
    protected Vector3dc getPosition() {
        Vector3d position = Util.toVector3d(entity.getLocation());
        if (!entity.isMarker()) {
            double offset = 1.25;
            if (entity.isSmall()) {
                offset /= 2;
            }
            position.y += offset;
        }
        return position;
    }

    @Override
    protected Vector3dc getCenter() {
        Location location = entity.getLocation();
        return new Vector3d(location.getX(), location.getY() + ArmorStandSize.get(entity).getHeight() / 2, location.getZ());
    }

    @Override
    protected Vector3dc getSize() {
        ArmorStandSize size = ArmorStandSize.get(entity);
        double width = size.getWidth();
        double height = size.getHeight();
        return new Vector3d(width, height, width);
    }

}
