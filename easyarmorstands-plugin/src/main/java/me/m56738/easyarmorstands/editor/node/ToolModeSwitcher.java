package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.util.ToolManager;
import me.m56738.easyarmorstands.api.editor.util.ToolMode;
import me.m56738.easyarmorstands.common.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class ToolModeSwitcher {
    private final ToolManager toolManager;

    public ToolModeSwitcher(ToolManager toolManager) {
        this.toolManager = toolManager;
    }

    public Component getActionBar() {
        TextComponent.Builder builder = Component.text()
                .append(toolManager.getMode().getName())
                .append(Component.text()
                        .appendSpace()
                        .append(Component.text('['))
                        .append(Component.keybind("key.swapOffhand"))
                        .append(Component.text(']'))
                        .appendSpace()
                        .append(Message.component("easyarmorstands.node.switch-mode"))
                        .color(NamedTextColor.GRAY));
        return builder.build();
    }

    public boolean onClick(ClickContext context) {
        if (context.type() == ClickContext.Type.SWAP_HANDS) {
            return switchMode();
        } else {
            return false;
        }
    }

    private boolean switchMode() {
        ToolMode mode = toolManager.getMode();
        ToolMode nextMode = toolManager.getNextMode();
        if (nextMode != mode) {
            toolManager.setMode(nextMode);
            return true;
        }
        return false;
    }
}
