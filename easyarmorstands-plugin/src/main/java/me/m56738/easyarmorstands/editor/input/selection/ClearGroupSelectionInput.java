package me.m56738.easyarmorstands.editor.input.selection;

import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.editor.layer.ElementSelectionLayerImpl;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

public class ClearGroupSelectionInput implements Input {
    private static final Component NAME = Component.translatable("easyarmorstands.input.select.group.clear");
    private static final Style STYLE = Style.style(NamedTextColor.RED);
    private final ElementSelectionLayerImpl layer;

    public ClearGroupSelectionInput(ElementSelectionLayerImpl layer) {
        this.layer = layer;
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
        layer.clearGroupSelection();
    }
}
