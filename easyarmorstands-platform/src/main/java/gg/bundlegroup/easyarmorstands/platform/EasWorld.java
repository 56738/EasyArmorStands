package gg.bundlegroup.easyarmorstands.platform;

import org.joml.Vector3dc;

import java.util.function.Consumer;

public interface EasWorld extends EasWrapper {
    EasArmorStand spawnArmorStand(Vector3dc position, float yaw, Consumer<EasArmorStand> configure);
}
