package gg.bundlegroup.easyarmorstands;

import net.kyori.adventure.text.Component;
import org.joml.Vector3dc;

import java.util.List;

public interface Handle {
    void update();

    List<Manipulator> getManipulators();

    Vector3dc getPosition();

    Component getComponent();
}
