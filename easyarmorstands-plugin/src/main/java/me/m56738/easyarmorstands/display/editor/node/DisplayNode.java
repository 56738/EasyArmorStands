package me.m56738.easyarmorstands.display.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.AddContext;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.RemoveContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.AbstractElementNode;
import me.m56738.easyarmorstands.api.particle.BoundingBoxParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.display.element.DisplayElement;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.joml.Vector3d;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class DisplayNode extends AbstractElementNode<DisplayElement<?>> {
    private final Session session;
    private final BoundingBoxParticle boxParticle;
    private final Property<Location> locationProperty;
    private final Property<Float> widthProperty;
    private final Property<Float> heightProperty;
    private boolean showBoundingBoxIfInactive;
    private boolean canShow;
    private boolean isVisible;
    private boolean isActive;

    public DisplayNode(Session session, DisplayElement<?> element) {
        super(session, element);
        this.session = session;
        this.boxParticle = session.particleProvider().createAxisAlignedBox();
        this.locationProperty = getProperties().get(EntityPropertyTypes.LOCATION);
        this.widthProperty = getProperties().get(DisplayPropertyTypes.BOX_WIDTH);
        this.heightProperty = getProperties().get(DisplayPropertyTypes.BOX_HEIGHT);
    }

    @Override
    public void onAdd(AddContext context) {
        canShow = true;
        boxParticle.setColor(ParticleColor.GRAY);
        updateBoundingBox();
        super.onAdd(context);
    }

    @Override
    public void onRemove(RemoveContext context) {
        canShow = false;
        if (isVisible) {
            session.removeParticle(boxParticle);
            isVisible = false;
        }
        super.onRemove(context);
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

    private void updateBoundingBox() {
        float width = widthProperty.getValue();
        float height = heightProperty.getValue();
        Location location = locationProperty.getValue();
        Vector3d position = Util.toVector3d(location);
        boolean visible = canShow && width != 0 && height != 0 && (isActive || showBoundingBoxIfInactive);
        if (visible) {
            boxParticle.setBoundingBox(BoundingBox.of(position, width, height));
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
