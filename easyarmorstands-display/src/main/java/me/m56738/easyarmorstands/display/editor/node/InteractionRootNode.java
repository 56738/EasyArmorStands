package me.m56738.easyarmorstands.display.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.AddContext;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.RemoveContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.editor.util.ToolMenuManager;
import me.m56738.easyarmorstands.api.editor.util.ToolMenuMode;
import me.m56738.easyarmorstands.api.particle.BoundingBoxParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.display.editor.box.InteractionBoxEditor;
import me.m56738.easyarmorstands.display.element.InteractionElement;
import me.m56738.easyarmorstands.editor.input.OpenElementMenuInput;
import me.m56738.easyarmorstands.editor.input.ReturnInput;
import me.m56738.easyarmorstands.editor.node.BoxResizeToolManager;
import me.m56738.easyarmorstands.editor.node.PropertyMenuNode;
import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class InteractionRootNode extends PropertyMenuNode implements ElementNode {
    private final Session session;
    private final InteractionElement element;
    private final BoundingBoxParticle boxParticle;
    private final Property<Location> locationProperty;
    private final Property<Float> widthProperty;
    private final Property<Float> heightProperty;
    private final boolean allowMenu;

    public InteractionRootNode(Session session, InteractionElement element) {
        super(session, session.properties(element));
        this.session = session;
        this.element = element;
        this.boxParticle = session.particleProvider().createAxisAlignedBox();
        this.locationProperty = properties().get(EntityPropertyTypes.LOCATION);
        this.widthProperty = properties().get(DisplayPropertyTypes.BOX_WIDTH);
        this.heightProperty = properties().get(DisplayPropertyTypes.BOX_HEIGHT);
        this.allowMenu = session.player().hasPermission(Permissions.OPEN);
        new ToolMenuManager(session, this, element.getTools(properties())).setMode(ToolMenuMode.GLOBAL);
        new BoxResizeToolManager(session, this, new InteractionBoxEditor(properties()));
    }

    @Override
    public void onAdd(@NotNull AddContext context) {
        boxParticle.setColor(ParticleColor.GRAY);
        updateBoundingBox();
        session.addParticle(boxParticle);
    }

    @Override
    public void onRemove(@NotNull RemoveContext context) {
        session.removeParticle(boxParticle);
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
            context.addInput(new OpenElementMenuInput(session, element));
        }
        context.addInput(new ReturnInput(session));
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
        Vector3d position = Util.toVector3d(location);
        boxParticle.setBoundingBox(BoundingBox.of(position, width, height));
    }

    @Override
    public @NotNull InteractionElement getElement() {
        return element;
    }
}
