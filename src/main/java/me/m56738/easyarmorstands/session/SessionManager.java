package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.spawn.SpawnCapability;
import me.m56738.easyarmorstands.event.ArmorStandPreSpawnEvent;
import me.m56738.easyarmorstands.event.SessionInitializeEvent;
import me.m56738.easyarmorstands.event.SessionStartEvent;
import me.m56738.easyarmorstands.history.SpawnArmorStandAction;
import me.m56738.easyarmorstands.node.ArmorStandRootNode;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
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

    public Session start(Player player) {
        Session session = new Session(player);
        session.addProvider(new ArmorStandButtonProvider());
        start(session);
        session.addProvider(new DefaultEntityButtonProvider());
        return session;
    }

    public Session start(Player player, ArmorStand armorStand) {
        SessionStartEvent event = new SessionStartEvent(player, armorStand);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return null;
        }

        Session session = start(player);
        session.addProvider(new ArmorStandButtonProvider());
        session.pushNode(new ArmorStandRootNode(session, armorStand));
        start(session);
        session.addProvider(new DefaultEntityButtonProvider());
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
        // TODO
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

    public Session getSession(Entity entity) {
        for (Session session : sessions.values()) {
            if (session.getEntity() == entity) {
                return session;
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
