package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.layer.ElementSelectionLayer;
import me.m56738.easyarmorstands.api.editor.layer.LayerProvider;
import me.m56738.easyarmorstands.editor.layer.ElementSelectionLayerImpl;
import org.jetbrains.annotations.NotNull;

class LayerProviderImpl implements LayerProvider {
    private final Session session;

    LayerProviderImpl(Session session) {
        this.session = session;
    }

    @Override
    public @NotNull ElementSelectionLayer elementSelectionLayer() {
        return new ElementSelectionLayerImpl(session);
    }
}
