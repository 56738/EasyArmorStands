package me.m56738.easyarmorstands.editor.input.selection;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.NamedTextColor;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

public class SelectElementInput implements Input {
    private static final Component NAME = Component.translatable("easyarmorstands.input.select");
    private static final Style STYLE = Style.style(NamedTextColor.GREEN);
    private final Session session;
    private final SelectableElement element;

    public SelectElementInput(Session session, SelectableElement element) {
        this.session = session;
        this.element = element;
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
        return ClickContext.Type.RIGHT_CLICK;
    }

    @Override
    public boolean allowSneak() {
        return false;
    }

    @Override
    public void execute(@NotNull ClickContext context) {
        ChangeContext changeContext = new EasPlayer(session.player());
        if (!changeContext.canEditElement(element)) {
            return;
        }
        Node node = element.createNode(session);
        session.pushNode(node);
    }
}
