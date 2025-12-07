package me.m56738.easyarmorstands.common.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.AddContext;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.RemoveContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.AbstractElementNode;
import me.m56738.easyarmorstands.api.editor.util.ToolManager;
import me.m56738.easyarmorstands.api.editor.util.ToolMode;
import me.m56738.easyarmorstands.api.particle.BoundingBoxParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.common.editor.box.InteractionBoxEditor;
import me.m56738.easyarmorstands.common.editor.input.OpenElementMenuInput;
import me.m56738.easyarmorstands.common.editor.input.ReturnInput;
import me.m56738.easyarmorstands.common.element.InteractionElement;
import me.m56738.easyarmorstands.common.permission.Permissions;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class InteractionRootNode extends AbstractElementNode<InteractionElement> {
    private final BoundingBoxParticle boxParticle;
    private final Property<Location> locationProperty;
    private final Property<Float> widthProperty;
    private final Property<Float> heightProperty;
    private final boolean allowMenu;

    public InteractionRootNode(Session session, InteractionElement element) {
        super(session, element);
        this.boxParticle = session.particleProvider().createAxisAlignedBox();
        this.locationProperty = getProperties().get(EntityPropertyTypes.LOCATION);
        this.widthProperty = getProperties().get(DisplayPropertyTypes.BOX_WIDTH);
        this.heightProperty = getProperties().get(DisplayPropertyTypes.BOX_HEIGHT);
        this.allowMenu = session.player().hasPermission(Permissions.OPEN);
        new ToolManager(session, this, element.getTools(getContext())).setMode(ToolMode.GLOBAL);
        new BoxResizeToolManager(session, this, new InteractionBoxEditor(getContext(), getProperties()));
    }

    @Override
    public void onAdd(@NotNull AddContext context) {
        boxParticle.setColor(ParticleColor.GRAY);
        updateBoundingBox();
        getSession().addParticle(boxParticle);
    }

    @Override
    public void onRemove(@NotNull RemoveContext context) {
        getSession().removeParticle(boxParticle);
    }

    @Override
    public void onEnter(@NotNull EnterContext context) {
        boxParticle.setColor(ParticleColor.WHITE);
        updateBoundingBox();
        super.onEnter(context);
    }

    @Override
    public void onExit(@NotNull ExitContext context) {
        boxParticle.setColor(ParticleColor.GRAY);
        updateBoundingBox();
        super.onExit(context);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        updateBoundingBox();
        super.onUpdate(context);
        if (allowMenu) {
            context.addInput(new OpenElementMenuInput(getSession(), getElement()));
        }
        context.addInput(new ReturnInput(getSession()));
    }

    @Override
    public void onInactiveUpdate(@NotNull UpdateContext context) {
        updateBoundingBox();
        super.onInactiveUpdate(context);
    }

    private void updateBoundingBox() {
        float width = widthProperty.getValue();
        float height = heightProperty.getValue();
        Location location = locationProperty.getValue();
        Vector3dc position = location.position();
        boxParticle.setBoundingBox(BoundingBox.of(position, width, height));
    }
}
