package me.m56738.easyarmorstands.common.editor.input;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

public class OpenSpawnMenuInput implements Input {
    private static final Component NAME = Component.translatable("easyarmorstands.input.spawn");
    private static final Style STYLE = Style.style(NamedTextColor.YELLOW);
    private final CommonPlatform platform;
    private final Session session;

    public OpenSpawnMenuInput(CommonPlatform platform, Session session) {
        this.platform = platform;
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
        platform.openSpawnMenu(session.player());
    }
}
