package gg.bundlegroup.easyarmorstands;

import net.kyori.adventure.text.Component;

public interface Manipulator {
    void start();

    void update();

    Component getComponent();
}
