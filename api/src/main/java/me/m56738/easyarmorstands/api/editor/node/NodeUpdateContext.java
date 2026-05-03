package me.m56738.easyarmorstands.api.editor.node;

import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.ButtonHandler;
import me.m56738.easyarmorstands.api.editor.input.Input;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface NodeUpdateContext {
    void addInput(Input input);

    void addButton(Button button, ButtonHandler handler);

    void removeButton(Button button);
}
