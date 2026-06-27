package me.m56738.easyarmorstands.editor.tool.mode;

import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import net.kyori.adventure.text.Component;

public class GlobalToolMode extends AbstractDefaultToolMode {
    private static final Component NAME = Component.translatable("easyarmorstands.node.global");

    @Override
    public Component name() {
        return NAME;
    }

    @Override
    public ToolContext createContext(ToolProvider provider) {
        return ToolContext.of(provider.position(), RotationProvider.identity());
    }
}
