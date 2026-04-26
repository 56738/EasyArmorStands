package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.ButtonHandler;
import me.m56738.easyarmorstands.api.editor.button.ButtonHandlerContext;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.editor.input.selection.SelectElementInput;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class ElementButtonHandler implements ButtonHandler {
    private final SelectableElement element;
    private final SelectElementInput selectInput;

    public ElementButtonHandler(Session session, SelectableElement element) {
        this.element = element;
        this.selectInput = new SelectElementInput(session, element, () -> element.createLayer(session));
    }

    @Override
    public void onUpdate(ButtonHandlerContext context) {
        context.addInput(selectInput);
    }

    @Override
    public @NotNull Component getName() {
        return element.getName();
    }
}
