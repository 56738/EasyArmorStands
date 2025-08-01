package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.MenuNode;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.editor.box.BoundingBoxEditor;
import me.m56738.easyarmorstands.editor.box.BoxSidePositionProvider;
import me.m56738.easyarmorstands.editor.button.BoxResizeButton;
import me.m56738.easyarmorstands.editor.tool.BoxResizeTool;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.NamedTextColor;
import me.m56738.easyarmorstands.message.Message;

public class BoxResizeToolManager {
    private final Session session;
    private final MenuNode menuNode;
    private final BoundingBoxEditor editor;

    public BoxResizeToolManager(Session session, MenuNode menuNode, BoundingBoxEditor editor) {
        this.session = session;
        this.menuNode = menuNode;
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
        menuNode.addButton(new BoxResizeButton(session, name.color(NamedTextColor.AQUA), ParticleColor.AQUA, tool));
    }
}
