package me.m56738.easyarmorstands.common.editor.input;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.api.element.MenuElement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

public class OpenElementMenuInput implements Input {
    private static final Component NAME = Component.translatable("easyarmorstands.input.menu");
    private static final Style STYLE = Style.style(NamedTextColor.YELLOW);
    private final Session session;
    private final MenuElement element;

    public OpenElementMenuInput(Session session, MenuElement element) {
        this.session = session;
        this.element = element;
    }

    @Override
    public ClickContext.@NotNull Type clickType() {
        return ClickContext.Type.SWAP_HANDS;
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
    public void execute(@NotNull ClickContext context) {
        element.openMenu(session.player());
    }
}
