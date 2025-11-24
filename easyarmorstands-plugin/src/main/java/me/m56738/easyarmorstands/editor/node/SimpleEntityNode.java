package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.editor.node.MenuNode;
import me.m56738.easyarmorstands.api.editor.util.ToolMenuManager;
import me.m56738.easyarmorstands.api.editor.util.ToolMenuMode;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.editor.input.OpenElementMenuInput;
import me.m56738.easyarmorstands.editor.input.ReturnInput;
import me.m56738.easyarmorstands.permission.Permissions;
import org.jetbrains.annotations.NotNull;

public class SimpleEntityNode extends MenuNode implements ElementNode {
    private final Session session;
    private final Element element;
    private final ToolMenuManager toolManager;
    private final ToolMenuModeSwitcher toolSwitcher;
    private final boolean allowMenu;

    public SimpleEntityNode(Session session, EditableElement element) {
        super(session);
        this.session = session;
        this.element = element;
        this.toolManager = new ToolMenuManager(session, this, element.getTools(session.properties(element)));
        this.toolManager.setMode(ToolMenuMode.GLOBAL);
        this.toolSwitcher = new ToolMenuModeSwitcher(this.toolManager);
        this.allowMenu = session.player().hasPermission(Permissions.OPEN);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        context.setActionBar(toolSwitcher.getActionBar());
        toolSwitcher.onUpdate(context);
        if (allowMenu && element instanceof MenuElement) {
            context.addInput(new OpenElementMenuInput(session, (MenuElement) element));
        }
        super.onUpdate(context);
        context.addInput(new ReturnInput(session));
    }

    @Override
    public boolean isValid() {
        return element.isValid();
    }

    @Override
    public @NotNull Element getElement() {
        return element;
    }
}
