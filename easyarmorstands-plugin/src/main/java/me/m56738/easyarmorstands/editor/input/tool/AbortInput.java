package me.m56738.easyarmorstands.editor.input.tool;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.api.editor.tool.ToolSession;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.NamedTextColor;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

public class AbortInput implements Input {
    private static final Component NAME = Component.translatable("easyarmorstands.input.tool.abort");
    private static final Style STYLE = Style.style(NamedTextColor.GRAY);
    private final Session session;
    private final ToolSession toolSession;

    public AbortInput(Session session, ToolSession toolSession) {
        this.session = session;
        this.toolSession = toolSession;
    }

    @Override
    public @NotNull Component name() {
        return NAME;
    }

    @Override
    public @NotNull Style style() {
        return STYLE;
    }

    @Override
    public ClickContext.@NotNull Type clickType() {
        return ClickContext.Type.LEFT_CLICK;
    }

    @Override
    public void execute(@NotNull ClickContext context) {
        toolSession.revert();
        session.popNode();
    }
}
