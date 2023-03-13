package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.bone.PartBone;
import me.m56738.easyarmorstands.bone.PositionBone;
import me.m56738.easyarmorstands.capability.spawn.SpawnCapability;
import me.m56738.easyarmorstands.event.ArmorStandPreSpawnEvent;
import me.m56738.easyarmorstands.event.SessionInitializeEvent;
import me.m56738.easyarmorstands.event.SessionStartEvent;
import me.m56738.easyarmorstands.history.SpawnArmorStandAction;
import me.m56738.easyarmorstands.tool.BoneAxisMoveTool;
import me.m56738.easyarmorstands.tool.BoneAxisRotateTool;
import me.m56738.easyarmorstands.tool.PositionAxisTool;
import me.m56738.easyarmorstands.tool.PositionMoveTool;
import me.m56738.easyarmorstands.tool.PositionRotateTool;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;
import org.joml.Matrix3d;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.HashMap;
import java.util.Iterator;

public class SessionManager {
    private final HashMap<Player, Session> sessions = new HashMap<>();

    public void start(Session session) {
        final Session old = sessions.put(session.getPlayer(), session);
        if (old != null) {
            old.stop();
        }
        Bukkit.getPluginManager().callEvent(new SessionInitializeEvent(session));
    }

    public Session start(Player player, ArmorStand armorStand) {
        SessionStartEvent event = new SessionStartEvent(player, armorStand);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return null;
        }
        Session session = createSession(player, armorStand);
        start(session);
        return session;
    }

    public boolean stop(Player player) {
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

    public void hideSkeletons(Player player) {
        for (Session session : sessions.values()) {
            if (session instanceof ArmorStandSession) {
                ((ArmorStandSession) session).hideSkeleton(player);
            }
        }
    }

    public void stopAllSessions() {
        for (Session session : sessions.values()) {
            session.stop();
        }
        sessions.clear();
    }

    public Session getSession(Player player) {
        return sessions.get(player);
    }

    public ArmorStandSession getSession(ArmorStand armorStand) {
        for (Session session : sessions.values()) {
            if (session instanceof ArmorStandSession) {
                ArmorStandSession armorStandSession = (ArmorStandSession) session;
                if (armorStandSession.getEntity().equals(armorStand)) {
                    return armorStandSession;
                }
            }
        }
        return null;
    }

    public void spawnAndStart(Player player) {
        Location eyeLocation = player.getEyeLocation();
        Vector3d cursor = Util.getRotation(eyeLocation, new Matrix3d()).transform(0, 0, 2, new Vector3d());
        Vector3d position = new Vector3d(cursor);
        if (!player.isFlying()) {
            position.y = 0;
        }
        position.add(Util.toVector3d(player.getLocation()));
        ArmorStand armorStand = spawn(player, position, eyeLocation.getYaw() + 180);
        if (armorStand == null) {
            return;
        }
        Session session = start(player, armorStand);
        if (session == null) {
            armorStand.remove();
            return;
        }
        cursor.add(Util.toVector3d(eyeLocation));
        session.startMoving(cursor);
    }

    public ArmorStand spawn(Player player, Vector3dc position, float yaw) {
        Location location = new Location(player.getWorld(), position.x(), position.y(), position.z(), yaw, 0);
        ArmorStandPreSpawnEvent event = new ArmorStandPreSpawnEvent(player, location);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return null;
        }

        SpawnCapability spawnCapability = EasyArmorStands.getInstance().getCapability(SpawnCapability.class);
        ArmorStand armorStand = spawnCapability.spawnEntity(location, ArmorStand.class, e -> {
            e.setGravity(false);
            for (ArmorStandPart part : ArmorStandPart.values()) {
                part.setPose(e, EulerAngle.ZERO);
            }
        });
        EasyArmorStands.getInstance().getHistory(player).push(new SpawnArmorStandAction(armorStand));
        return armorStand;
    }

    private Session createSession(Player player, ArmorStand armorStand) {
        ArmorStandSession session = new ArmorStandSession(player, armorStand);
        session.addBone("position", createPositionBone(session));
        session.addBone("head", createPartBone(
                session,
                ArmorStandPart.HEAD,
                Component.text("Head"),
                new Vector3d(0, 23, 0),
                new Vector3d(0, 7, 0)));
        session.addBone("body", createPartBone(
                session,
                ArmorStandPart.BODY,
                Component.text("Body"),
                new Vector3d(0, 24, 0),
                new Vector3d(0, -12, 0)));
        session.addBone("leftarm", createPartBone(
                session,
                ArmorStandPart.LEFT_ARM,
                Component.text("Left arm"),
                new Vector3d(5, 22, 0),
                new Vector3d(0, -10, 0)));
        session.addBone("rightarm", createPartBone(
                session,
                ArmorStandPart.RIGHT_ARM,
                Component.text("Right arm"),
                new Vector3d(-5, 22, 0),
                new Vector3d(0, -10, 0)));
        session.addBone("leftleg", createPartBone(
                session,
                ArmorStandPart.LEFT_LEG,
                Component.text("Left leg"),
                new Vector3d(1.9, 12, 0),
                new Vector3d(0, -11, 0)));
        session.addBone("rightleg", createPartBone(
                session,
                ArmorStandPart.RIGHT_LEG,
                Component.text("Right leg"),
                new Vector3d(-1.9, 12, 0),
                new Vector3d(0, -11, 0)
        ));
        return session;
    }

    private PositionBone createPositionBone(ArmorStandSession session) {
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

    private PartBone createPartBone(ArmorStandSession session, ArmorStandPart part, Component component, Vector3dc offset, Vector3dc length) {
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
