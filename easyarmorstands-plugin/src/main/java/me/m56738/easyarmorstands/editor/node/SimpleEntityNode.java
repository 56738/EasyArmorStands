package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.AbstractElementNode;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.editor.util.ToolManager;
import me.m56738.easyarmorstands.api.editor.util.ToolMode;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.common.permission.Permissions;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class SimpleEntityNode extends AbstractElementNode<EditableElement> implements ElementNode {
    private final ToolManager toolManager;
    private final ToolModeSwitcher toolModeSwitcher;

    public SimpleEntityNode(Session session, EditableElement element) {
        super(session, element);
        this.toolManager = new ToolManager(session, this, element.getTools(getContext()));
        this.toolManager.setMode(ToolMode.GLOBAL);
        this.toolModeSwitcher = new ToolModeSwitcher(this.toolManager);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        context.setActionBar(toolModeSwitcher.getActionBar());
    }

    @Override
    public boolean onClick(@NotNull ClickContext context) {
        if (super.onClick(context)) {
            return true;
        }
        if (getElement() instanceof MenuElement menuElement) {
            Player player = getSession().player();
            if (context.type() == ClickContext.Type.LEFT_CLICK && player.hasPermission(Permissions.OPEN)) {
                menuElement.openMenu(player);
                return true;
            }
        }
        return toolModeSwitcher.onClick(context);
    }
}
