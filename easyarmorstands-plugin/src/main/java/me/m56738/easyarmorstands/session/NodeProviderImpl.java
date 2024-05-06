package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.ElementSelectionNode;
import me.m56738.easyarmorstands.api.editor.node.NodeProvider;
import me.m56738.easyarmorstands.editor.node.ElementSelectionNodeImpl;
import org.jetbrains.annotations.NotNull;

class NodeProviderImpl implements NodeProvider {
    private final Session session;

    NodeProviderImpl(Session session) {
        this.session = session;
    }

    @Override
    public @NotNull ElementSelectionNode elementSelectionNode() {
        return new ElementSelectionNodeImpl(session);
    }
}
