package me.m56738.easyarmorstands.editor.tool.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.node.NodeShowContext;
import me.m56738.easyarmorstands.api.editor.node.tool.ToolNode;
import me.m56738.easyarmorstands.api.editor.tool.Tool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.editor.tool.button.ToolMenuButton;
import net.kyori.adventure.text.Component;

public abstract class AbstractToolNode<T extends Tool<?>> implements ToolNode {
    private final Session session;
    private final Component name;
    private final ParticleColor color;
    private final T tool;

    protected AbstractToolNode(Session session, Component name, ParticleColor color, T tool) {
        this.session = session;
        this.name = name;
        this.color = color;
        this.tool = tool;
    }

    public Session getSession() {
        return session;
    }

    public Component getName() {
        return name;
    }

    public ParticleColor getColor() {
        return color;
    }

    public T getTool() {
        return tool;
    }

    public abstract Button createButton();

    @Override
    public void onShow(NodeShowContext context) {
        context.addButton(createButton(), new ToolMenuButton(session, this, name));
    }
}
