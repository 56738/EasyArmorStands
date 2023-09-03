package me.m56738.easyarmorstands.editor.armorstand.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.editor.button.BoundingBoxButton;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.util.EasMath;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.ArmorStand;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandButton extends BoundingBoxButton {
    private final ArmorStand entity;
    private final ArmorStandElement element;

    public ArmorStandButton(Session session, ArmorStandElement element) {
        super(session);
        this.entity = element.getEntity();
        this.element = element;
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
    protected Quaterniondc getRotation() {
        return EasMath.getEntityYawRotation(entity.getLocation().getYaw(), new Quaterniond());
    }

    @Override
    protected BoundingBox getBoundingBox() {
        return element.getBoundingBox();
    }
}
