package gg.bundlegroup.easyarmorstands.core.platform.test;

import gg.bundlegroup.easyarmorstands.core.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.core.platform.EasWorld;
import org.joml.Vector3dc;

import java.util.function.Consumer;

public class TestWorld extends TestWrapper implements EasWorld {
    public TestWorld(TestPlatform platform) {
        super(platform);
    }

    public TestPlayer createPlayer() {
        TestPlayer player = new TestPlayer(platform(), this);
        platform().getPlayers().add(player);
        return player;
    }

    public TestArmorStand createArmorStand() {
        return new TestArmorStand(platform(), this);
    }

    @Override
    public TestArmorStand spawnArmorStand(Vector3dc position, float yaw, Consumer<EasArmorStand> configure) {
        TestArmorStand armorStand = createArmorStand();
        armorStand.move(position, yaw, 0);
        armorStand.update();
        configure.accept(armorStand);
        return armorStand;
    }
}
