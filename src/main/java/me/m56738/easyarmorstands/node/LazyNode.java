package me.m56738.easyarmorstands.node;

import org.joml.Vector3dc;

import java.util.function.Supplier;

public class LazyNode implements Node {
    private final Supplier<Node> nodeSupplier;
    private Node node;

    public LazyNode(Supplier<Node> nodeSupplier) {
        this.nodeSupplier = nodeSupplier;
    }

    @Override
    public void onEnter() {
        node = nodeSupplier.get();
        node.onEnter();
    }

    @Override
    public void onExit() {
        node.onExit();
        node = null;
    }

    @Override
    public void onUpdate(Vector3dc eyes, Vector3dc target) {
        node.onUpdate(eyes, target);
    }

    @Override
    public boolean onClick(Vector3dc eyes, Vector3dc target, ClickType type) {
        return node.onClick(eyes, target, type);
    }
}
