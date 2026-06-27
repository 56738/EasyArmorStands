package me.m56738.easyarmorstands.api.editor.node;

import java.util.Collection;
import java.util.List;

public interface Node {
    static Node empty() {
        return EmptyNodeImpl.INSTANCE;
    }

    static Node composite(Collection<Node> nodes) {
        List<Node> nonEmpty = nodes.stream()
                .filter(n -> n != Node.empty())
                .toList();
        if (nonEmpty.isEmpty()) {
            return empty();
        } else if (nonEmpty.size() == 1) {
            return nonEmpty.getFirst();
        } else {
            return new CompositeNodeImpl(nonEmpty);
        }
    }

    default void onShow(NodeShowContext context) {
    }

    default void onHide(NodeHideContext context) {
    }

    default void onUpdate(NodeUpdateContext context) {
    }
}
