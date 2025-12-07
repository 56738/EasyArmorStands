package me.m56738.easyarmorstands.common.editor.input.selection;

import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.input.Category;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.api.element.ElementDiscoveryEntry;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.common.editor.node.ElementSelectionNodeImpl;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

public class RemoveElementFromGroupInput implements Input {
    private static final Component NAME = Component.translatable("easyarmorstands.input.select.group.remove");
    private static final Style STYLE = Style.style(NamedTextColor.RED);
    private final ElementSelectionNodeImpl node;
    private final ElementDiscoveryEntry discoveryEntry;
    private final SelectableElement element;

    public RemoveElementFromGroupInput(ElementSelectionNodeImpl node, ElementDiscoveryEntry discoveryEntry, SelectableElement element) {
        this.node = node;
        this.discoveryEntry = discoveryEntry;
        this.element = element;
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
        node.removeFromGroup(discoveryEntry, element);
    }
}
