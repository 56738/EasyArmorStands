package me.m56738.easyarmorstands.editor.input;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.editor.layer.LayerFactory;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

public class SelectLayerInput implements Input {
    private static final Component NAME = Component.translatable("easyarmorstands.input.select");
    private static final Style STYLE = Style.style(NamedTextColor.GREEN);
    private final Session session;
    private final LayerFactory layerFactory;

    public SelectLayerInput(Session session, LayerFactory layerFactory) {
        this.session = session;
        this.layerFactory = layerFactory;
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
        Layer layer = layerFactory.createLayer();
        session.pushLayer(layer);
    }
}
