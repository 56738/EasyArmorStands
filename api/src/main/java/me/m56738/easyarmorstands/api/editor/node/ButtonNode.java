package me.m56738.easyarmorstands.api.editor.node;

import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.ButtonHandler;

public class ButtonNode implements Node {
    private final Button button;
    private final ButtonHandler handler;

    public ButtonNode(Button button, ButtonHandler handler) {
        this.button = button;
        this.handler = handler;
    }

    @Override
    public void onShow(NodeShowContext context) {
        context.addButton(button, handler);
    }
}
