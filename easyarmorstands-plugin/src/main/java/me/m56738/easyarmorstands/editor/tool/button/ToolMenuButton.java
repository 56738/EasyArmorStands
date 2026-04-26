package me.m56738.easyarmorstands.editor.tool.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.ButtonHandler;
import me.m56738.easyarmorstands.api.editor.button.ButtonHandlerContext;
import me.m56738.easyarmorstands.api.editor.node.tool.ToolNode;
import me.m56738.easyarmorstands.editor.input.SelectLayerInput;
import net.kyori.adventure.text.Component;

public class ToolMenuButton implements ButtonHandler {
    private final Session session;
    private final ToolNode node;
    private final Component name;

    public ToolMenuButton(Session session, ToolNode node, Component name) {
        this.session = session;
        this.node = node;
        this.name = name;
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public void onUpdate(ButtonHandlerContext context) {
        context.addInput(new SelectLayerInput(session, node::createLayer));
    }
}
