package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.Entity;
import org.joml.Vector3dc;

public class SimpleEntityButton extends SimpleButton {
    private final Entity entity;

    public SimpleEntityButton(Session session, Entity entity) {
        super(session, ParticleColor.WHITE);
        this.entity = entity;
    }

    @Override
    protected Vector3dc getPosition() {
        return Util.toVector3d(entity.getLocation());
    }
}
