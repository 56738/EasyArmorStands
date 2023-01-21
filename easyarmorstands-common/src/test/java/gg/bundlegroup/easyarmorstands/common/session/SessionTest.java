package gg.bundlegroup.easyarmorstands.common.session;

import gg.bundlegroup.easyarmorstands.common.platform.test.TestArmorStand;
import gg.bundlegroup.easyarmorstands.common.platform.test.TestPlatform;
import gg.bundlegroup.easyarmorstands.common.platform.test.TestPlayer;
import gg.bundlegroup.easyarmorstands.common.platform.test.TestWorld;

public class SessionTest {
    private final TestPlatform platform = new TestPlatform();
    private final TestWorld world = platform.createWorld();
    private final TestPlayer player = world.createPlayer();
    private final TestArmorStand entity = world.createArmorStand();
    private final SessionManager sessionManager = new SessionManager(platform);
    private final SessionListener sessionListener = new SessionListener(platform, sessionManager);

    public SessionTest() {
        player.update();
        entity.update();
        sessionListener.onJoin(player);
    }
}
