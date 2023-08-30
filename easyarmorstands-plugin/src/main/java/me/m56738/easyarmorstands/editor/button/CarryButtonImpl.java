package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.CarryAxis;
import me.m56738.easyarmorstands.api.editor.button.CarryButton;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.editor.node.CarryNode;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class CarryButtonImpl extends SimpleButton implements CarryButton, NodeFactoryButton {
    private final Session session;
    private final CarryAxis carryAxis;
    private final Component name;

    public CarryButtonImpl(Session session, CarryAxis carryAxis, ParticleColor color, Component name) {
        super(session, color);
        this.session = session;
        this.carryAxis = carryAxis;
        this.name = name;
    }

    @Override
    protected Vector3dc getPosition() {
        return carryAxis.getPosition();
    }

    @Override
    protected Quaterniondc getRotation() {
        return carryAxis.getRotation();
    }

    @Override
    protected boolean isBillboard() {
        return false;
    }

    @Override
    public @NotNull Component getName() {
        return name;
    }

    @Override
    public @NotNull Node createNode() {
        return new CarryNode(session, carryAxis, name);
    }
}
