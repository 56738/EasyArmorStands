package me.m56738.easyarmorstands.display.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.util.ToolManager;
import me.m56738.easyarmorstands.api.editor.util.ToolMode;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.display.editor.box.DisplayBoxEditor;
import me.m56738.easyarmorstands.editor.node.BoxResizeToolManager;
import me.m56738.easyarmorstands.editor.tool.BoxToolProvider;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

public class DisplayBoxNode extends DisplayNode {
    private final Session session;
    private final Component name;

    public DisplayBoxNode(Session session, PropertyContainer properties) {
        super(session, properties);
        this.session = session;
        this.name = Message.component("easyarmorstands.node.display.box").color(NamedTextColor.GOLD);
        setShowBoundingBoxIfInactive(true); // bounding box should remain visible while a tool node is active

        DisplayBoxEditor boxEditor = new DisplayBoxEditor(properties);
        new ToolManager(session, this, new BoxToolProvider(boxEditor)).setMode(ToolMode.GLOBAL);
        new BoxResizeToolManager(session, this, boxEditor);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        context.setActionBar(name);
    }

    @Override
    public boolean onClick(@NotNull ClickContext context) {
        if (super.onClick(context)) {
            return true;
        }
        if (context.type() == ClickContext.Type.LEFT_CLICK) {
            session.popNode();
            return true;
        }
        return false;
    }
}
