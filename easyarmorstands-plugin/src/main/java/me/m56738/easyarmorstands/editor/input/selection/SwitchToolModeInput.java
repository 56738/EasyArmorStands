package me.m56738.easyarmorstands.editor.input.selection;

import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.editor.node.ToolMenuModeSwitcher;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.NamedTextColor;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

public class SwitchToolModeInput implements Input {
    private static final Component NAME = Component.translatable("easyarmorstands.input.switch-mode");
    private static final Style STYLE = Style.style(NamedTextColor.YELLOW);
    private final ToolMenuModeSwitcher switcher;

    public SwitchToolModeInput(ToolMenuModeSwitcher switcher) {
        this.switcher = switcher;
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
        return ClickContext.Type.SWAP_HANDS;
    }

    @Override
    public boolean requireSneak() {
        return true;
    }

    @Override
    public void execute(@NotNull ClickContext context) {
        switcher.switchMode();
    }
}
