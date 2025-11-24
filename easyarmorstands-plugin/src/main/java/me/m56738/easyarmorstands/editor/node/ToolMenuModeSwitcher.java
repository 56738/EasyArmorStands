package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.util.ToolMenuManager;
import me.m56738.easyarmorstands.api.editor.util.ToolMenuMode;
import me.m56738.easyarmorstands.editor.input.selection.SwitchToolModeInput;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;

public class ToolMenuModeSwitcher {
    private final ToolMenuManager toolManager;

    public ToolMenuModeSwitcher(ToolMenuManager toolManager) {
        this.toolManager = toolManager;
    }

    public Component getActionBar() {
        return toolManager.getMode().getName();
    }

    public void onUpdate(UpdateContext context) {
        if (toolManager.getMode() != toolManager.getNextMode()) {
            context.addInput(new SwitchToolModeInput(this));
        }
    }

    public void switchMode() {
        ToolMenuMode mode = toolManager.getMode();
        ToolMenuMode nextMode = toolManager.getNextMode();
        if (nextMode != mode) {
            toolManager.setMode(nextMode);
        }
    }
}
