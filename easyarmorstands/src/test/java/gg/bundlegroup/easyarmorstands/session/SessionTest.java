package gg.bundlegroup.easyarmorstands.session;

import gg.bundlegroup.easyarmorstands.SessionListener;
import gg.bundlegroup.easyarmorstands.SessionManager;
import gg.bundlegroup.easyarmorstands.platform.test.TestArmorStand;
import gg.bundlegroup.easyarmorstands.platform.test.TestPlatform;
import gg.bundlegroup.easyarmorstands.platform.test.TestPlayer;
import gg.bundlegroup.easyarmorstands.platform.test.TestWorld;

public class SessionTest {
    private final TestPlatform platform = new TestPlatform();
    private final TestWorld world = platform.createWorld();
    private final TestPlayer player = world.createPlayer();
    private final TestArmorStand entity = world.createArmorStand();
    private final SessionManager sessionManager = new SessionManager();
    private final SessionListener sessionListener = new SessionListener(sessionManager);

    public SessionTest() {
        player.update();
        entity.update();
        sessionListener.onJoin(player);
    }
}
