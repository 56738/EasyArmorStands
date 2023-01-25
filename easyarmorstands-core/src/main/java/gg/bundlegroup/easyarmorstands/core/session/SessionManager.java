package gg.bundlegroup.easyarmorstands.core.session;

import gg.bundlegroup.easyarmorstands.core.bone.PartBone;
import gg.bundlegroup.easyarmorstands.core.bone.PositionBone;
import gg.bundlegroup.easyarmorstands.core.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.core.platform.EasPlatform;
import gg.bundlegroup.easyarmorstands.core.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.core.tool.BoneAxisMoveTool;
import gg.bundlegroup.easyarmorstands.core.tool.BoneAxisRotateTool;
import gg.bundlegroup.easyarmorstands.core.tool.PositionAxisTool;
import gg.bundlegroup.easyarmorstands.core.tool.PositionMoveTool;
import gg.bundlegroup.easyarmorstands.core.tool.PositionRotateTool;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.HashMap;
import java.util.Iterator;

public class SessionManager {
    private final EasPlatform platform;
    private final HashMap<EasPlayer, Session> sessions = new HashMap<>();

    public SessionManager(EasPlatform platform) {
        this.platform = platform;
    }

    public void start(EasPlayer player, Session session) {
        final Session old = sessions.put(player, session);
        if (old != null) {
            old.stop();
        }

        platform.onSessionStarted(session);
    }

    public Session start(EasPlayer player, EasArmorStand armorStand) {
        if (!platform.canStartSession(player, armorStand)) {
            return null;
        }
        Session session = createSession(player, armorStand);
        start(player, session);
        return session;
    }

    public boolean stop(EasPlayer player) {
        Session session = sessions.remove(player);
        if (session != null) {
            session.stop();
            return true;
        }
        return false;
    }

    public void update() {
        for (Iterator<Session> iterator = sessions.values().iterator(); iterator.hasNext(); ) {
            Session session = iterator.next();
            boolean valid = session.update();
            if (!valid) {
                iterator.remove();
                session.stop();
            }
        }
    }

    public void hideSkeletons(EasPlayer player) {
        for (Session session : sessions.values()) {
            session.hideSkeleton(player);
        }
    }

    public void stopAllSessions() {
        for (Session session : sessions.values()) {
            session.stop();
        }
        sessions.clear();
    }

    public Session getSession(EasPlayer player) {
        return sessions.get(player);
    }

    public Session getSession(EasArmorStand armorStand) {
        for (Session session : sessions.values()) {
            if (session.getEntity().equals(armorStand)) {
                return session;
            }
        }
        return null;
    }

    public void spawnAndStart(EasPlayer player) {
        Vector3d cursor = player.getEyeRotation().transform(0, 0, 2, new Vector3d());
        Vector3d position = new Vector3d(cursor);
        if (!player.isFlying()) {
            position.y = 0;
        }
        position.add(player.getPosition());
        EasArmorStand armorStand = spawn(player, position, player.getYaw() + 180);
        Session session = start(player, armorStand);
        if (session == null) {
            armorStand.remove();
            return;
        }
        cursor.add(player.getEyePosition());
        session.startMoving(cursor);
    }

    public EasArmorStand spawn(EasPlayer player, Vector3dc position, float yaw) {
        if (!platform.canSpawnArmorStand(player, position)) {
            return null;
        }

        return player.getWorld().spawnArmorStand(position, yaw, e -> {
            Vector3d pose = new Vector3d();
            for (EasArmorStand.Part part : EasArmorStand.Part.values()) {
                e.setPose(part, pose);
            }
            e.setGravity(false);
        });
    }

    private Session createSession(EasPlayer player, EasArmorStand armorStand) {
        Session session = new Session(player, armorStand);
        session.addBone("position", createPositionBone(session));
        session.addBone("head", createPartBone(
                session,
                EasArmorStand.Part.HEAD,
                Component.text("Head"),
                new Vector3d(0, 23, 0),
                new Vector3d(0, 7, 0)));
        session.addBone("body", createPartBone(
                session,
                EasArmorStand.Part.BODY,
                Component.text("Body"),
                new Vector3d(0, 24, 0),
                new Vector3d(0, -12, 0)));
        session.addBone("leftarm", createPartBone(
                session,
                EasArmorStand.Part.LEFT_ARM,
                Component.text("Left arm"),
                new Vector3d(5, 22, 0),
                new Vector3d(0, -10, 0)));
        session.addBone("rightarm", createPartBone(
                session,
                EasArmorStand.Part.RIGHT_ARM,
                Component.text("Right arm"),
                new Vector3d(-5, 22, 0),
                new Vector3d(0, -10, 0)));
        session.addBone("leftleg", createPartBone(
                session,
                EasArmorStand.Part.LEFT_LEG,
                Component.text("Left leg"),
                new Vector3d(1.9, 12, 0),
                new Vector3d(0, -11, 0)));
        session.addBone("rightleg", createPartBone(
                session,
                EasArmorStand.Part.RIGHT_LEG,
                Component.text("Right leg"),
                new Vector3d(-1.9, 12, 0),
                new Vector3d(0, -11, 0)
        ));
        return session;
    }

    private PositionBone createPositionBone(Session session) {
        PositionBone bone = new PositionBone(session);
        bone.addTool("move", new PositionMoveTool(bone,
                "Move", NamedTextColor.YELLOW));
        bone.addTool("rotate", new PositionRotateTool(bone,
                "Rotate", NamedTextColor.GOLD));
        bone.addTool("x", new PositionAxisTool(bone,
                "X", NamedTextColor.RED, new Vector3d(1, 0, 0)));
        bone.addTool("y", new PositionAxisTool(bone,
                "Y", NamedTextColor.GREEN, new Vector3d(0, 1, 0)));
        bone.addTool("z", new PositionAxisTool(bone,
                "Z", NamedTextColor.BLUE, new Vector3d(0, 0, 1)));
        return bone;
    }

    private PartBone createPartBone(Session session, EasArmorStand.Part part, Component component, Vector3dc offset, Vector3dc length) {
        PartBone bone = new PartBone(session, part, component, offset, length);
        bone.addTool("x", new BoneAxisRotateTool(bone,
                "X", NamedTextColor.RED, new Vector3d(1, 0, 0)));
        bone.addTool("y", new BoneAxisRotateTool(bone,
                "Y", NamedTextColor.GREEN, new Vector3d(0, 1, 0)));
        bone.addTool("z", new BoneAxisRotateTool(bone,
                "Z", NamedTextColor.BLUE, new Vector3d(0, 0, 1)));
        bone.addTool("mx", new BoneAxisMoveTool(bone,
                "Move X", NamedTextColor.RED, new Vector3d(1, 0, 0)));
        bone.addTool("my", new BoneAxisMoveTool(bone,
                "Move Y", NamedTextColor.GREEN, new Vector3d(0, 1, 0)));
        bone.addTool("mz", new BoneAxisMoveTool(bone,
                "Move Z", NamedTextColor.BLUE, new Vector3d(0, 0, 1)));
        return bone;
    }
}
