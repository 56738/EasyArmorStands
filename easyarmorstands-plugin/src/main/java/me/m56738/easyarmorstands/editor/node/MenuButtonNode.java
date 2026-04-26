package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.node.NodeShowContext;

public class MenuButtonNode implements Node {
    private final MenuButton menuButton;

    public MenuButtonNode(MenuButton menuButton) {
        this.menuButton = menuButton;
    }

    @Override
    public void onShow(NodeShowContext context) {
        context.addButton(menuButton);
    }
}
