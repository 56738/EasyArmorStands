package me.m56738.easyarmorstands.editor.input;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.capability.handswap.SwapHandItemsCapability;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.NamedTextColor;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

public class OpenElementMenuInput implements Input {
    private static final Component NAME = Component.translatable("easyarmorstands.input.menu");
    private static final Style STYLE = Style.style(NamedTextColor.YELLOW);
    private final Session session;
    private final MenuElement element;
    private final ClickContext.Type clickType;
    private final boolean requireSneak;

    public OpenElementMenuInput(Session session, MenuElement element) {
        this.session = session;
        this.element = element;

        boolean hasSwapHands = EasyArmorStandsPlugin.getInstance().getCapability(SwapHandItemsCapability.class) != null;
        this.clickType = hasSwapHands ? ClickContext.Type.SWAP_HANDS : ClickContext.Type.LEFT_CLICK;
        this.requireSneak = !hasSwapHands;
    }

    @Override
    public ClickContext.@NotNull Type clickType() {
        return clickType;
    }

    @Override
    public boolean requireSneak() {
        return requireSneak;
    }

    @Override
    public boolean allowSneak() {
        return requireSneak;
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
