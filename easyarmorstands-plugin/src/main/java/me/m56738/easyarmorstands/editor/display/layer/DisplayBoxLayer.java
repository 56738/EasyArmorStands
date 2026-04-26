package me.m56738.easyarmorstands.editor.display.layer;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.editor.util.ToolMenuManager;
import me.m56738.easyarmorstands.editor.util.ToolMenuMode;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.editor.display.DisplayBoxEditor;
import me.m56738.easyarmorstands.editor.input.ReturnInput;
import me.m56738.easyarmorstands.editor.layer.BoxResizeToolManager;
import me.m56738.easyarmorstands.editor.tool.BoxToolProvider;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

public class DisplayBoxLayer extends DisplayLayer {
    private final Session session;
    private final Component name;

    public DisplayBoxLayer(Session session, PropertyContainer properties) {
        super(session, properties);
        this.session = session;
        this.name = Message.component("easyarmorstands.node.display.box").color(NamedTextColor.GOLD);
        setShowBoundingBoxIfInactive(true); // bounding box should remain visible while a tool layer is active

        DisplayBoxEditor boxEditor = new DisplayBoxEditor(properties);
        new ToolMenuManager(session, this, new BoxToolProvider(boxEditor)).setMode(ToolMenuMode.GLOBAL);
        new BoxResizeToolManager(session, this, boxEditor);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        context.setActionBar(name);
        context.addInput(new ReturnInput(session));
    }
}
