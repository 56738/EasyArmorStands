package me.m56738.easyarmorstands.common.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.AbstractNode;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.common.editor.box.BoundingBoxEditor;
import me.m56738.easyarmorstands.common.editor.box.BoxSidePositionProvider;
import me.m56738.easyarmorstands.common.editor.button.BoxResizeButton;
import me.m56738.easyarmorstands.common.editor.tool.BoxResizeTool;
import me.m56738.easyarmorstands.common.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class BoxResizeToolManager {
    private final Session session;
    private final AbstractNode node;
    private final BoundingBoxEditor editor;

    public BoxResizeToolManager(Session session, AbstractNode node, BoundingBoxEditor editor) {
        this.session = session;
        this.node = node;
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
        BoxResizeTool tool = new BoxResizeTool(editor, positionProvider, rotationProvider, axis, end);
        node.addButton(new BoxResizeButton(session, name.color(NamedTextColor.AQUA), ParticleColor.AQUA, tool));
    }
}
