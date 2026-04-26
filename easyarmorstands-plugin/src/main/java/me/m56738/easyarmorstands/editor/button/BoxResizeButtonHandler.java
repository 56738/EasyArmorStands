package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.ButtonHandler;
import me.m56738.easyarmorstands.api.editor.button.ButtonHandlerContext;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.editor.input.SelectLayerInput;
import me.m56738.easyarmorstands.editor.layer.AxisMoveToolLayer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class BoxResizeButtonHandler implements ButtonHandler {
    private final Session session;
    private final Component name;
    private final ParticleColor color;
    private final AxisMoveTool tool;

    public BoxResizeButtonHandler(Session session, Component name, ParticleColor color, AxisMoveTool tool) {
        this.session = session;
        this.name = name;
        this.color = color;
        this.tool = tool;
    }

    @Override
    public @NotNull Component getName() {
        return name;
    }

    @Override
    public void onUpdate(ButtonHandlerContext context) {
        context.addInput(new SelectLayerInput(session, this::createLayer));
    }

    private Layer createLayer() {
        return new AxisMoveToolLayer(session, tool.start(), name, color, 3, tool.getPosition(), tool.getRotation(), tool.getAxis());
    }
}
