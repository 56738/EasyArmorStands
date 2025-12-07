package me.m56738.easyarmorstands.common.editor.input.selection;

import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.common.editor.node.ElementSelectionNodeImpl;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

public class ClearGroupSelectionInput implements Input {
    private static final Component NAME = Component.translatable("easyarmorstands.input.select.group.clear");
    private static final Style STYLE = Style.style(NamedTextColor.RED);
    private final ElementSelectionNodeImpl node;

    public ClearGroupSelectionInput(ElementSelectionNodeImpl node) {
        this.node = node;
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
        return ClickContext.Type.LEFT_CLICK;
    }

    @Override
    public void execute(@NotNull ClickContext context) {
        node.clearGroupSelection();
    }
}
