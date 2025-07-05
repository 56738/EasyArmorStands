package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.util.ToolMenuManager;
import me.m56738.easyarmorstands.api.editor.util.ToolMenuMode;
import me.m56738.easyarmorstands.capability.handswap.SwapHandItemsCapability;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class ToolMenuModeSwitcher {
    private final ToolMenuManager toolManager;
    private final SwapHandItemsCapability swapHandItemsCapability = EasyArmorStandsPlugin.getInstance().getCapability(SwapHandItemsCapability.class);

    public ToolMenuModeSwitcher(ToolMenuManager toolManager) {
        this.toolManager = toolManager;
    }

    public Component getActionBar() {
        TextComponent.Builder builder = Component.text()
                .append(toolManager.getMode().getName());
        if (swapHandItemsCapability != null) {
            Component swapHandsButton = swapHandItemsCapability.key();
            builder.append(Component.text()
                    .appendSpace()
                    .append(Component.text('['))
                    .append(swapHandsButton)
                    .append(Component.text(']'))
                    .appendSpace()
                    .append(Message.component("easyarmorstands.node.switch-mode"))
                    .color(NamedTextColor.GRAY));
        }
        return builder.build();
    }

    public boolean onClick(ClickContext context) {
        if (context.type() == ClickContext.Type.SWAP_HANDS) {
            return switchMode();
        } else if (swapHandItemsCapability == null && context.type() == ClickContext.Type.RIGHT_CLICK) {
            return switchMode();
        } else {
            return false;
        }
    }

    private boolean switchMode() {
        ToolMenuMode mode = toolManager.getMode();
        ToolMenuMode nextMode = toolManager.getNextMode();
        if (nextMode != mode) {
            toolManager.setMode(nextMode);
            return true;
        }
        return false;
    }
}
