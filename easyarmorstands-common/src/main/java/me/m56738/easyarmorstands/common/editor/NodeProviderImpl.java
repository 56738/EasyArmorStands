package me.m56738.easyarmorstands.common.editor;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.ElementSelectionNode;
import me.m56738.easyarmorstands.api.editor.node.NodeProvider;
import me.m56738.easyarmorstands.common.editor.node.ElementSelectionNodeImpl;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import org.jetbrains.annotations.NotNull;

class NodeProviderImpl implements NodeProvider {
    private final CommonPlatform platform;
    private final Session session;

    NodeProviderImpl(CommonPlatform platform, Session session) {
        this.platform = platform;
        this.session = session;
    }

    @Override
    public @NotNull ElementSelectionNode elementSelectionNode() {
        return new ElementSelectionNodeImpl(platform, session);
    }
}
