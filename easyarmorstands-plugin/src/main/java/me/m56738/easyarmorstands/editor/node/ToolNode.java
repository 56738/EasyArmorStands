package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.tool.ToolSession;
import me.m56738.easyarmorstands.editor.input.tool.AbortInput;
import me.m56738.easyarmorstands.editor.input.tool.ConfirmInput;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.TextComponent;
import org.jetbrains.annotations.NotNull;

public abstract class ToolNode implements Node {
    private final Session session;
    private final ToolSession toolSession;
    private final Component name;

    public ToolNode(Session session, ToolSession toolSession, Component name) {
        this.session = session;
        this.toolSession = toolSession;
        this.name = name;
    }

    @Override
    public void onExit(@NotNull ExitContext context) {
        toolSession.commit(toolSession.getDescription());
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        updateActionBar(context);
        context.addInput(new ConfirmInput(session));
        context.addInput(new AbortInput(session, toolSession));
    }

    protected void updateActionBar(@NotNull UpdateContext context) {
        TextComponent.Builder builder = Component.text();
        builder.append(name);
        Component state = toolSession.getStatus();
        if (state != null) {
            builder.append(Component.text(": "));
            builder.append(state);
        }
        context.setActionBar(builder);
    }

    @Override
    public boolean isValid() {
        return toolSession.isValid();
    }

    public @NotNull Component getName() {
        return name;
    }
}
