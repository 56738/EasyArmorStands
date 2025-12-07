package me.m56738.easyarmorstands.common.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.AbstractElementNode;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.editor.util.ToolManager;
import me.m56738.easyarmorstands.api.editor.util.ToolMode;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.common.editor.input.OpenElementMenuInput;
import me.m56738.easyarmorstands.common.editor.input.ReturnInput;
import me.m56738.easyarmorstands.common.permission.Permissions;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class SimpleEntityNode extends AbstractElementNode<EditableElement> implements ElementNode {
    private final ToolManager toolManager;
    private final ToolModeSwitcher toolModeSwitcher;
    private final boolean allowMenu;

    public SimpleEntityNode(Session session, EditableElement element) {
        super(session, element);
        this.toolManager = new ToolManager(session, this, element.getTools(getContext()));
        this.toolManager.setMode(ToolMode.GLOBAL);
        this.toolModeSwitcher = new ToolModeSwitcher(this.toolManager);
        this.allowMenu = session.player().hasPermission(Permissions.OPEN);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        context.setActionBar(toolModeSwitcher.getActionBar());
        toolModeSwitcher.onUpdate(context);
        if (allowMenu && getElement() instanceof MenuElement menuElement) {
            context.addInput(new OpenElementMenuInput(getSession(), menuElement));
        }
        super.onUpdate(context);
        context.addInput(new ReturnInput(getSession()));
    }
}
