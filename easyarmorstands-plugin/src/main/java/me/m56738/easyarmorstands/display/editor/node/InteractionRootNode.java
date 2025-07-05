package me.m56738.easyarmorstands.display.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.AddContext;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.RemoveContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.editor.util.ToolManager;
import me.m56738.easyarmorstands.api.editor.util.ToolMode;
import me.m56738.easyarmorstands.api.particle.BoundingBoxParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.display.editor.box.InteractionBoxEditor;
import me.m56738.easyarmorstands.display.element.InteractionElement;
import me.m56738.easyarmorstands.editor.node.BoxResizeToolManager;
import me.m56738.easyarmorstands.editor.node.AbstractPropertyNode;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

public class InteractionRootNode extends AbstractPropertyNode implements ElementNode {
    private final Session session;
    private final InteractionElement element;
    private final BoundingBoxParticle boxParticle;
    private final Property<Location> locationProperty;
    private final Property<Float> widthProperty;
    private final Property<Float> heightProperty;

    public InteractionRootNode(Session session, InteractionElement element) {
        super(session, session.properties(element));
        this.session = session;
        this.element = element;
        this.boxParticle = session.particleProvider().createAxisAlignedBox();
        this.locationProperty = properties().get(EntityPropertyTypes.LOCATION);
        this.widthProperty = properties().get(DisplayPropertyTypes.BOX_WIDTH);
        this.heightProperty = properties().get(DisplayPropertyTypes.BOX_HEIGHT);
        new ToolManager(session, this, element.getTools(properties())).setMode(ToolMode.GLOBAL);
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
    }

    @Override
    public void onInactiveUpdate(@NotNull UpdateContext context) {
        updateBoundingBox();
        super.onInactiveUpdate(context);
    }

    @Override
    public boolean onClick(@NotNull ClickContext context) {
        if (super.onClick(context)) {
            return true;
        }
        Player player = session.player();
        if (context.type() == ClickContext.Type.LEFT_CLICK && player.hasPermission(Permissions.OPEN)) {
            element.openMenu(player);
            return true;
        }
        return false;
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
