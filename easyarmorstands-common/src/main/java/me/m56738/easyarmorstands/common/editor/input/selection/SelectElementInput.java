package me.m56738.easyarmorstands.common.editor.input.selection;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

public class SelectElementInput implements Input {
    private static final Component NAME = Component.translatable("easyarmorstands.input.select");
    private static final Style STYLE = Style.style(NamedTextColor.GREEN);
    private final CommonPlatform platform;
    private final Session session;
    private final SelectableElement element;

    public SelectElementInput(CommonPlatform platform, Session session, SelectableElement element) {
        this.platform = platform;
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
    public void execute(@NotNull ClickContext context) {
        if (!platform.canSelectElement(session.player(), element)) {
            return;
        }
        Node node = element.createNode(session);
        session.pushNode(node);
    }
}
