package me.m56738.easyarmorstands.common.editor.input;

import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.input.Category;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.common.editor.node.ToolModeSwitcher;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

public class SwitchToolModeInput implements Input {
    private static final Component NAME = Component.translatable("easyarmorstands.input.switch-mode");
    private static final Style STYLE = Style.style(NamedTextColor.YELLOW);
    private final ToolModeSwitcher switcher;

    public SwitchToolModeInput(ToolModeSwitcher switcher) {
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
        return ClickContext.Type.RIGHT_CLICK;
    }

    @Override
    public Category category() {
        return Category.SECONDARY;
    }

    @Override
    public void execute(@NotNull ClickContext context) {
        switcher.switchMode();
    }
}
