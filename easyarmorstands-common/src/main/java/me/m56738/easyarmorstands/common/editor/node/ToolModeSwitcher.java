package me.m56738.easyarmorstands.common.editor.node;

import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.util.ToolManager;
import me.m56738.easyarmorstands.api.editor.util.ToolMode;
import me.m56738.easyarmorstands.common.editor.input.SwitchToolModeInput;
import net.kyori.adventure.text.Component;

public class ToolModeSwitcher {
    private final ToolManager toolManager;

    public ToolModeSwitcher(ToolManager toolManager) {
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
        ToolMode mode = toolManager.getMode();
        ToolMode nextMode = toolManager.getNextMode();
        if (nextMode != mode) {
            toolManager.setMode(nextMode);
        }
    }
}
