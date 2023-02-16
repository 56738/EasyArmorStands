package me.m56738.easyarmorstands.core.bone;

import me.m56738.easyarmorstands.core.platform.EasArmorStand;
import me.m56738.easyarmorstands.core.session.ArmorStandSession;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class PositionBone extends AbstractBone {
    private final ArmorStandSession session;
    private final Vector3d position = new Vector3d();

    public PositionBone(ArmorStandSession session) {
        super(session);
        this.session = session;
    }

    @Override
    public void refresh() {
        EasArmorStand entity = session.getEntity();
        double y = 1.25;
        if (entity.isSmall()) {
            y /= 2;
        }
        entity.getPosition().add(0, y, 0, position);
    }

    @Override
    public Vector3dc getPosition() {
        return position;
    }

    @Override
    public Component getName() {
        return Component.text("Position");
    }

    @Override
    public @NotNull ArmorStandSession getSession() {
        return session;
    }
}
