package me.m56738.easyarmorstands.editor.layer;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.ButtonHandler;
import me.m56738.easyarmorstands.api.editor.button.PointButton;
import me.m56738.easyarmorstands.api.editor.layer.AbstractLayer;
import me.m56738.easyarmorstands.api.editor.node.ButtonNode;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.editor.box.BoundingBoxEditor;
import me.m56738.easyarmorstands.editor.box.BoxSidePositionProvider;
import me.m56738.easyarmorstands.editor.button.BoxResizeButtonHandler;
import me.m56738.easyarmorstands.editor.tool.BoxResizeTool;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class BoxResizeToolManager {
    private final Session session;
    private final AbstractLayer layer;
    private final BoundingBoxEditor editor;

    public BoxResizeToolManager(Session session, AbstractLayer layer, BoundingBoxEditor editor) {
        this.session = session;
        this.layer = layer;
        this.editor = editor;
        for (Axis axis : Axis.values()) {
            addAxisEnd(axis, false);
            addAxisEnd(axis, true);
        }
    }

    private void addAxisEnd(Axis axis, boolean end) {
        Component name;
        if (axis == Axis.Y) {
            name = Message.component("easyarmorstands.node.display.box.height");
        } else {
            name = Message.component("easyarmorstands.node.display.box.width");
        }
        BoxSidePositionProvider positionProvider = new BoxSidePositionProvider(editor, axis, end);
        RotationProvider rotationProvider = RotationProvider.identity();
        ParticleColor color = ParticleColor.AQUA;
        BoxResizeTool tool = new BoxResizeTool(editor, positionProvider, rotationProvider, axis, end);
        PointButton button = new PointButton(session, tool, tool);
        button.setPriority(2);
        button.setColor(color);
        ButtonHandler handler = new BoxResizeButtonHandler(session, name.color(NamedTextColor.AQUA), color, tool);
        layer.addNode(new ButtonNode(button, handler));
    }
}
