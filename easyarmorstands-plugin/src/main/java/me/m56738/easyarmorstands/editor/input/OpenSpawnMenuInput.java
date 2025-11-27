package me.m56738.easyarmorstands.editor.input;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.input.Category;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.capability.handswap.SwapHandItemsCapability;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.NamedTextColor;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

public class OpenSpawnMenuInput implements Input {
    private static final Component NAME = Component.translatable("easyarmorstands.input.spawn");
    private static final Style STYLE = Style.style(NamedTextColor.YELLOW);
    private final Session session;
    private final ClickContext.Type clickType;
    private final Category category;

    public OpenSpawnMenuInput(Session session) {
        this.session = session;

        boolean hasSwapHands = EasyArmorStandsPlugin.getInstance().getCapability(SwapHandItemsCapability.class) != null;
        this.clickType = hasSwapHands ? ClickContext.Type.SWAP_HANDS : ClickContext.Type.LEFT_CLICK;
        this.category = hasSwapHands ? Category.PRIMARY : Category.SECONDARY;
    }

    @Override
    public ClickContext.@NotNull Type clickType() {
        return clickType;
    }

    @Override
    public Category category() {
        return category;
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
        Menu menu = EasyArmorStandsPlugin.getInstance().createSpawnMenu(session.player());
        session.player().openInventory(menu.getInventory());
    }
}
