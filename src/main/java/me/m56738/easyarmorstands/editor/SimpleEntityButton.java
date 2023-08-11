package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.node.SimpleButton;
import me.m56738.easyarmorstands.particle.ParticleColor;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.joml.Vector3dc;

public class SimpleEntityButton extends SimpleButton {
    private final Entity entity;
    private final Component name;

    public SimpleEntityButton(Session session, Entity entity) {
        super(session, ParticleColor.WHITE);
        this.entity = entity;
        this.name = Component.text(entity.getUniqueId().toString().substring(0, 8));
    }

    @Override
    protected Vector3dc getPosition() {
        return Util.toVector3d(entity.getLocation());
    }

    @Override
    public Component getName() {
        return name;
    }
}
