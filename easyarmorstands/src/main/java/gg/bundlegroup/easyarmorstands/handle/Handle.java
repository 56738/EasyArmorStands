package gg.bundlegroup.easyarmorstands.handle;

import gg.bundlegroup.easyarmorstands.manipulator.Manipulator;
import net.kyori.adventure.text.Component;
import org.joml.Vector3dc;

import java.util.List;

public interface Handle {
    void update();

    List<Manipulator> getManipulators();

    Vector3dc getPosition();

    Component getComponent();
}
