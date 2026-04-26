package me.m56738.easyarmorstands.api.editor.node;

import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.ButtonHandler;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface NodeShowContext {
    void addButton(Button button, ButtonHandler handler);
}
