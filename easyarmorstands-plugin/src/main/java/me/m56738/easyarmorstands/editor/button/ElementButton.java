package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.editor.input.selection.SelectElementInput;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

import java.util.List;

public class ElementButton implements MenuButton {
    private final SelectableElement element;
    private final Button button;
    private final SelectElementInput selectInput;

    public ElementButton(Session session, SelectableElement element, Button button) {
        this.element = element;
        this.button = button;
        this.selectInput = new SelectElementInput(session, element);
    }

    @Override
    public @NotNull Button getButton() {
        return button;
    }

    @Override
    public void collectInputs(@NotNull Session session, @Nullable Vector3dc cursor, @NotNull List<@NotNull Input> inputs) {
        inputs.add(selectInput);
    }

    @Override
    public @NotNull Component getName() {
        return element.getName();
    }
}
