package gg.bundlegroup.easyarmorstands;

import net.kyori.adventure.text.Component;
import org.joml.Vector3dc;

import java.awt.*;

public class BoneAxisManipulator extends AxisRotationManipulator {
    private final BoneHandle handle;
    private final Component component;

    public BoneAxisManipulator(BoneHandle handle, Component component, Vector3dc axis, Color color) {
        super(handle.getSession(), axis, color);
        this.handle = handle;
        this.component = component;
    }

    @Override
    public Component getComponent() {
        return component;
    }

    @Override
    protected Vector3dc getAnchor() {
        return handle.getAnchor();
    }

    @Override
    protected void refreshRotation() {
        getRotation().set(handle.getRotation());
    }

    @Override
    protected void onRotate(double angle) {
        handle.setRotation(getRotation());
    }
}
