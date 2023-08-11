package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import me.m56738.easyarmorstands.node.MenuNode;
import me.m56738.easyarmorstands.particle.AxisAlignedBoxParticle;
import me.m56738.easyarmorstands.particle.ParticleColor;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayHeightProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayWidthProperty;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class DisplayMenuNode extends MenuNode {
    private final Session session;
    private final PropertyContainer container;
    private final AxisAlignedBoxParticle boxParticle;
    private final Property<Location> locationProperty;
    private final Property<Float> widthProperty;
    private final Property<Float> heightProperty;
    private boolean showBoundingBoxIfInactive;
    private boolean canShow;
    private boolean isVisible;
    private boolean isActive;

    public DisplayMenuNode(Session session, Component name, PropertyContainer container) {
        super(session, name);
        this.session = session;
        this.container = container;
        this.boxParticle = EasyArmorStands.getInstance().getCapability(ParticleCapability.class).createAxisAlignedBox(session.getWorld());
        this.locationProperty = container.get(EntityLocationProperty.TYPE);
        this.widthProperty = container.get(DisplayWidthProperty.TYPE);
        this.heightProperty = container.get(DisplayHeightProperty.TYPE);
    }

    @Override
    public void onAdd() {
        canShow = true;
        boxParticle.setColor(ParticleColor.GRAY);
        updateBoundingBox();
    }

    @Override
    public void onRemove() {
        canShow = false;
        if (isVisible) {
            session.removeParticle(boxParticle);
            isVisible = false;
        }
    }

    @Override
    public void onEnter() {
        isActive = true;
        boxParticle.setColor(ParticleColor.WHITE);
        updateBoundingBox();
        super.onEnter();
    }

    @Override
    public void onExit() {
        isActive = false;
        boxParticle.setColor(ParticleColor.GRAY);
        updateBoundingBox();
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

    @Override
    public boolean isValid() {
        return container.isValid();
    }

    private void updateBoundingBox() {
        float width = widthProperty.getValue();
        float height = heightProperty.getValue();
        Location location = locationProperty.getValue();
        Vector3d position = Util.toVector3d(location);
        boolean visible = canShow && width != 0 && height != 0 && (isActive || showBoundingBoxIfInactive);
        if (visible) {
            boxParticle.setCenter(position.add(0, height / 2, 0, new Vector3d()));
            boxParticle.setSize(new Vector3d(width, height, width));
        }
        if (isVisible != visible) {
            isVisible = visible;
            if (visible) {
                session.addParticle(boxParticle);
            } else {
                session.removeParticle(boxParticle);
            }
        }
    }

    public boolean isShowBoundingBoxIfInactive() {
        return showBoundingBoxIfInactive;
    }

    public void setShowBoundingBoxIfInactive(boolean showBoundingBoxIfInactive) {
        this.showBoundingBoxIfInactive = showBoundingBoxIfInactive;
    }
}
