package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import me.m56738.easyarmorstands.node.EntityNode;
import me.m56738.easyarmorstands.node.MenuNode;
import me.m56738.easyarmorstands.particle.AxisAlignedBoxParticle;
import me.m56738.easyarmorstands.particle.ParticleColor;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class DisplayMenuNode extends MenuNode implements EntityNode {
    private final Session session;
    private final Display entity;
    private final AxisAlignedBoxParticle boxParticle;
    private boolean shouldShow;
    private boolean isVisible;

    public DisplayMenuNode(Session session, Component name, Display entity) {
        super(session, name);
        this.session = session;
        this.entity = entity;
        this.boxParticle = EasyArmorStands.getInstance().getCapability(ParticleCapability.class).createAxisAlignedBox(session.getWorld());
        this.boxParticle.setLineWidth(0.03125);
    }

    @Override
    public void onAdd() {
        shouldShow = true;
        boxParticle.setColor(ParticleColor.GRAY);
        updateBoundingBox();
    }

    @Override
    public void onRemove() {
        shouldShow = false;
        if (isVisible) {
            session.removeParticle(boxParticle);
            isVisible = false;
        }
    }

    @Override
    public void onEnter() {
        boxParticle.setColor(ParticleColor.WHITE);
        super.onEnter();
    }

    @Override
    public void onExit() {
        boxParticle.setColor(ParticleColor.GRAY);
        super.onExit();
    }

    @Override
    public void onUpdate(Vector3dc eyes, Vector3dc target) {
        updateBoundingBox();
        super.onUpdate(eyes, target);
    }

    @Override
    public void onInactiveUpdate() {
        updateBoundingBox();
        super.onInactiveUpdate();
    }

    private void updateBoundingBox() {
        float width = entity.getDisplayWidth();
        float height = entity.getDisplayHeight();
        Vector3d position = Util.toVector3d(entity.getLocation());
        boolean show = shouldShow && width != 0 && height != 0;
        if (show) {
            boxParticle.setCenter(position.add(0, height / 2, 0, new Vector3d()));
            boxParticle.setSize(new Vector3d(width, height, width));
        }
        if (isVisible != show) {
            isVisible = show;
            if (show) {
                session.addParticle(boxParticle);
            } else {
                session.removeParticle(boxParticle);
            }
        }
    }

    @Override
    public Display getEntity() {
        return entity;
    }

    @Override
    public boolean isValid() {
        return super.isValid() && entity.isValid();
    }
}
