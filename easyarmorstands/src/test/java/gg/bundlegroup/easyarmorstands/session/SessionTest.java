package gg.bundlegroup.easyarmorstands.session;

import gg.bundlegroup.easyarmorstands.BoneType;
import gg.bundlegroup.easyarmorstands.platform.test.TestArmorStand;
import gg.bundlegroup.easyarmorstands.platform.test.TestPlatform;
import gg.bundlegroup.easyarmorstands.platform.test.TestPlayer;
import gg.bundlegroup.easyarmorstands.platform.test.TestWorld;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

    @Test
    public void test() {
        sessionListener.onRightClickArmorStand(player, entity);
        Session session = sessionManager.getSession(player);
        Assertions.assertNotNull(session, "session not created");
        Assertions.assertNotNull(session.getSkeleton(), "skeleton not created");

        Vector3d position = new Vector3d(0, 0, 0);
        entity.move(position, 0, 0);

        Vector3dc offset = new Vector3d(0, 0, 3);

        BoneType boneType = BoneType.BODY;
        position.add(boneType.offset(entity))
                .sub(0, TestPlayer.EYE_HEIGHT, 0)
                .sub(offset);

        Vector3d direction = new Vector3d();

        for (int d = 0; d <= 360; d++) {
//            direction.set(boneType.length(entity)).rotateZ(Math.toRadians(d)).add(offset);
//            player.move(position, direction);
//            sessionListener.onRightClick(player);
//            sessionManager.update();
//            Assertions.assertEquals(boneType, session.getBoneType(), "target bone type");
//            sessionListener.onRightClick(player);
//            if (d % 90 == 0) {
//                Vector3d newPose = entity.getPose(boneType.part(), new Vector3d());
//                System.out.println(Math.toDegrees(newPose.y));
//            }
        }

        sessionListener.onScroll(player, 0, 1);
        Assertions.assertNull(sessionManager.getSession(player), "session not stopped");
        Assertions.assertFalse(session.getSkeleton().isValid(), "skeleton not removed");
    }
}
