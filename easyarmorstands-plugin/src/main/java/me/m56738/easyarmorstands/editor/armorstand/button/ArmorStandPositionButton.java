package me.m56738.easyarmorstands.editor.armorstand.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import me.m56738.easyarmorstands.api.editor.button.PointButton;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandRotationProvider;
import me.m56738.easyarmorstands.editor.armorstand.node.ArmorStandPositionNode;
import me.m56738.easyarmorstands.common.editor.node.NodeFactory;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public class ArmorStandPositionButton implements MenuButton, NodeFactory {
    private final Session session;
    private final ParticleColor color;
    private final Component name;
    private final PropertyContainer container;
    private final OffsetProvider offsetProvider;
    private final ArmorStandElement element;

    public ArmorStandPositionButton(Session session, ParticleColor color, Component name, PropertyContainer container, OffsetProvider offsetProvider, ArmorStandElement element) {
        this.session = session;
        this.color = color;
        this.name = name;
        this.container = container;
        this.offsetProvider = offsetProvider;
        this.element = element;
    }

    @Override
    public @NotNull Button getButton() {
        PointButton button = new PointButton(session,
                new EntityPositionProvider(container, offsetProvider),
                new ArmorStandRotationProvider(container));
        button.setPriority(1);
        button.setColor(color);
        return button;
    }

    @Override
    public @NotNull Component getName() {
        return name;
    }

    @Override
    public void onClick(@NotNull Session session, @Nullable Vector3dc cursor) {
        session.pushNode(createNode(), cursor);
    }

    @Override
    public Node createNode() {
        return new ArmorStandPositionNode(session, container, offsetProvider, element);
    }
}
