package me.m56738.easyarmorstands.display.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.AddContext;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.RemoveContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.particle.AxisAlignedBoxParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.node.MenuNode;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class DisplayMenuNode extends MenuNode implements ResettableNode {
    protected final PropertyContainer container;
    private final Session session;
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
        this.boxParticle = session.particleFactory().createAxisAlignedBox();
        this.locationProperty = container.get(EntityPropertyTypes.LOCATION);
        this.widthProperty = container.get(DisplayPropertyTypes.BOX_WIDTH);
        this.heightProperty = container.get(DisplayPropertyTypes.BOX_HEIGHT);
    }

    @Override
    public void onAdd(AddContext context) {
        canShow = true;
        boxParticle.setColor(ParticleColor.GRAY);
        updateBoundingBox();
    }

    @Override
    public void onRemove(RemoveContext context) {
        canShow = false;
        if (isVisible) {
            session.removeParticle(boxParticle);
            isVisible = false;
        }
    }

    @Override
    public void onEnter(EnterContext context) {
        isActive = true;
        boxParticle.setColor(ParticleColor.WHITE);
        updateBoundingBox();
        super.onEnter(context);
    }

    @Override
    public void onExit(ExitContext context) {
        isActive = false;
        boxParticle.setColor(ParticleColor.GRAY);
        updateBoundingBox();
        super.onExit(context);
    }

    @Override
    public void onUpdate(UpdateContext context) {
        updateBoundingBox();
        super.onUpdate(context);
    }

    @Override
    public void onInactiveUpdate(UpdateContext context) {
        updateBoundingBox();
        super.onInactiveUpdate(context);
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

    @Override
    public void reset() {
        container.get(DisplayPropertyTypes.TRANSLATION).setValue(new Vector3f());
        container.get(DisplayPropertyTypes.LEFT_ROTATION).setValue(new Quaternionf());
        container.get(DisplayPropertyTypes.SCALE).setValue(new Vector3f(1));
        container.get(DisplayPropertyTypes.RIGHT_ROTATION).setValue(new Quaternionf());

        Location location = locationProperty.getValue();
        location.setYaw(0);
        location.setPitch(0);
        locationProperty.setValue(location);

        container.commit();
    }
}
