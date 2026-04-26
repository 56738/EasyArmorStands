package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.editor.layer.LayerFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public interface LayerFactoryButton extends MenuButton, Button, LayerFactory {
    @Override
    default @NotNull Button getButton() {
        return this;
    }

    @Override
    default void onClick(@NotNull Session session, @Nullable Vector3dc cursor) {
        Layer layer = createLayer();
        if (layer != null) {
            session.pushLayer(layer, cursor);
        }
    }
}
