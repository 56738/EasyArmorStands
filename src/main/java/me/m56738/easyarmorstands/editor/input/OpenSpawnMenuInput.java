package me.m56738.easyarmorstands.editor.input;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.input.Input;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

public class OpenSpawnMenuInput implements Input {
    private static final Component NAME = Component.translatable("easyarmorstands.input.spawn");
    private static final Style STYLE = Style.style(NamedTextColor.YELLOW);
    private final EasyArmorStandsCommon eas;
    private final Session session;

    public OpenSpawnMenuInput(EasyArmorStandsCommon eas, Session session) {
        this.eas = eas;
        this.session = session;
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
        eas.openSpawnMenu(session.player());
    }
}
