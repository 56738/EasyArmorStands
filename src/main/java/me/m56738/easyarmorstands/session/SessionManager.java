package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.bone.ArmorStandPartBone;
import me.m56738.easyarmorstands.bone.ArmorStandPositionBone;
import me.m56738.easyarmorstands.capability.spawn.SpawnCapability;
import me.m56738.easyarmorstands.event.ArmorStandPreSpawnEvent;
import me.m56738.easyarmorstands.event.SessionInitializeEvent;
import me.m56738.easyarmorstands.event.SessionStartEvent;
import me.m56738.easyarmorstands.history.SpawnArmorStandAction;
import me.m56738.easyarmorstands.node.ArmorStandPartNode;
import me.m56738.easyarmorstands.node.BoneNode;
import me.m56738.easyarmorstands.node.ParentNode;
import me.m56738.easyarmorstands.node.YawBoneNode;
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

        Session session = new ArmorStandSession(player, armorStand);

        ParentNode root = new ParentNode(session, Component.text("Select a bone"));
        for (ArmorStandPart part : ArmorStandPart.values()) {
            ArmorStandPartBone bone = new ArmorStandPartBone(armorStand, part);

            ParentNode localNode = new ParentNode(session, part.getName().append(Component.text(" (local)")));
            localNode.addMoveNodes(session, bone, 3, true);
            localNode.addRotationNodes(session, bone, 1, true);

            ParentNode globalNode = new ParentNode(session, part.getName().append(Component.text(" (global)")));
            globalNode.addPositionNodes(session, bone, 3, true);
            globalNode.addRotationNodes(session, bone, 1, false);

            localNode.setNextNode(globalNode);
            globalNode.setNextNode(localNode);

            root.addNode(new ArmorStandPartNode(session, localNode, bone));
        }

        ArmorStandPositionBone positionBone = new ArmorStandPositionBone(armorStand);
        ParentNode positionNode = new ParentNode(session, Component.text("Position"));
        positionNode.addNode(new YawBoneNode(session, Component.text("Rotate"), NamedTextColor.GOLD, 1, positionBone));
        positionNode.addPositionNodes(session, positionBone, 3, true);
        BoneNode boneNode = new BoneNode(session, positionNode, positionBone, Component.text("Position"));
        root.addNode(boneNode);

        session.pushNode(root);

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
//        session.startMoving(cursor);
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
}
