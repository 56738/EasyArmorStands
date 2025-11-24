package me.m56738.easyarmorstands.api.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.NamedTextColor;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

class MenuButtonInput implements Input {
    private static final Component NAME = Component.translatable("easyarmorstands.input.select");
    private static final Style STYLE = Style.style(NamedTextColor.GREEN);
    private final Session session;
    private final MenuButton button;

    public MenuButtonInput(Session session, MenuButton button) {
        this.session = session;
        this.button = button;
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
        button.onClick(session, null);
    }
}
