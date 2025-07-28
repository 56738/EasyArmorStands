package me.m56738.easyarmorstands.display.element;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.BoundingBoxButton;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.display.editor.DisplayOffsetProvider;
import me.m56738.easyarmorstands.display.editor.DisplayRotationProvider;
import me.m56738.easyarmorstands.display.editor.node.DisplayRootNode;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.element.SimpleEntityElement;
import me.m56738.easyarmorstands.element.SimpleEntityElementType;
import me.m56738.easyarmorstands.menu.layout.ContentMenuBuilder;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

public class DisplayElement extends SimpleEntityElement {
    private final Entity entity;

    public DisplayElement(Platform platform, Entity entity, SimpleEntityElementType type) {
        super(platform, entity, type);
        this.entity = entity;
    }

    @Override
    public @NotNull Button createButton(@NotNull Session session) {
        PropertyContainer properties = getProperties();
        return new BoundingBoxButton(session, this,
                new EntityPositionProvider(properties, new DisplayOffsetProvider(properties)),
                new DisplayRotationProvider(properties));
    }

    @Override
    public @NotNull Node createNode(@NotNull Session session) {
        return new DisplayRootNode(session, this);
    }

    @Override
    public @NotNull DisplayToolProvider getTools(@NotNull ChangeContext context) {
        return new DisplayToolProvider(this, context);
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        Vector3dc position = entity.getLocation().position();
        double width = getProperties().get(DisplayPropertyTypes.BOX_WIDTH).getValue();
        double height = getProperties().get(DisplayPropertyTypes.BOX_HEIGHT).getValue();
        return BoundingBox.of(position, width, height);
    }

    @Override
    public void openMenu(@NotNull Player player) {
        EasyArmorStandsPlugin.getInstance().openMenu(PaperPlayer.toNative(player), this, ContentMenuBuilder::new);
    }
}
