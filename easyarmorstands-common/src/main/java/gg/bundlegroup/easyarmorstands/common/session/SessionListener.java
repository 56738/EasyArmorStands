package gg.bundlegroup.easyarmorstands.common.session;

import gg.bundlegroup.easyarmorstands.common.handle.BoneHandle;
import gg.bundlegroup.easyarmorstands.common.handle.PositionHandle;
import gg.bundlegroup.easyarmorstands.common.manipulator.BoneAxisMoveManipulator;
import gg.bundlegroup.easyarmorstands.common.manipulator.BoneAxisRotateManipulator;
import gg.bundlegroup.easyarmorstands.common.manipulator.PositionAxisManipulator;
import gg.bundlegroup.easyarmorstands.common.manipulator.PositionRotateManipulator;
import gg.bundlegroup.easyarmorstands.common.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.common.platform.EasItem;
import gg.bundlegroup.easyarmorstands.common.platform.EasListener;
import gg.bundlegroup.easyarmorstands.common.platform.EasPlatform;
import gg.bundlegroup.easyarmorstands.common.platform.EasPlayer;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class SessionListener implements EasListener {
    private final EasPlatform platform;
    private final SessionManager manager;

    public SessionListener(EasPlatform platform, SessionManager manager) {
        this.platform = platform;
        this.manager = manager;
    }

    private Session createSession(EasPlayer player, EasArmorStand armorStand) {
        Session session = new Session(player, armorStand);
        session.addHandle("position", createPositionHandle(session));
        session.addHandle("head", createBoneHandle(
                session,
                EasArmorStand.Part.HEAD,
                Component.text("Head"),
                new Vector3d(0, 23, 0),
                new Vector3d(0, 7, 0)));
        session.addHandle("body", createBoneHandle(
                session,
                EasArmorStand.Part.BODY,
                Component.text("Body"),
                new Vector3d(0, 24, 0),
                new Vector3d(0, -12, 0)));
        session.addHandle("leftarm", createBoneHandle(
                session,
                EasArmorStand.Part.LEFT_ARM,
                Component.text("Left arm"),
                new Vector3d(5, 22, 0),
                new Vector3d(0, -10, 0)));
        session.addHandle("rightarm", createBoneHandle(
                session,
                EasArmorStand.Part.RIGHT_ARM,
                Component.text("Right arm"),
                new Vector3d(-5, 22, 0),
                new Vector3d(0, -10, 0)));
        session.addHandle("leftleg", createBoneHandle(
                session,
                EasArmorStand.Part.LEFT_LEG,
                Component.text("Left leg"),
                new Vector3d(1.9, 12, 0),
                new Vector3d(0, -11, 0)));
        session.addHandle("rightleg", createBoneHandle(
                session,
                EasArmorStand.Part.RIGHT_LEG,
                Component.text("Right leg"),
                new Vector3d(-1.9, 12, 0),
                new Vector3d(0, -11, 0)
        ));
        return session;
    }

    private PositionHandle createPositionHandle(Session session) {
        PositionHandle handle = new PositionHandle(session);
        handle.addManipulator("rotate", new PositionRotateManipulator(handle,
                "Rotate", NamedTextColor.GOLD));
        handle.addManipulator("x", new PositionAxisManipulator(handle,
                "X", NamedTextColor.RED, new Vector3d(1, 0, 0)));
        handle.addManipulator("y", new PositionAxisManipulator(handle,
                "Y", NamedTextColor.GREEN, new Vector3d(0, 1, 0)));
        handle.addManipulator("z", new PositionAxisManipulator(handle,
                "Z", NamedTextColor.BLUE, new Vector3d(0, 0, 1)));
        return handle;
    }

    private BoneHandle createBoneHandle(Session session, EasArmorStand.Part part, Component component, Vector3dc offset, Vector3dc length) {
        BoneHandle handle = new BoneHandle(session, part, component, offset, length);
        handle.addManipulator("x", new BoneAxisRotateManipulator(handle,
                "X", NamedTextColor.RED, new Vector3d(1, 0, 0)));
        handle.addManipulator("y", new BoneAxisRotateManipulator(handle,
                "Y", NamedTextColor.GREEN, new Vector3d(0, 1, 0)));
        handle.addManipulator("z", new BoneAxisRotateManipulator(handle,
                "Z", NamedTextColor.BLUE, new Vector3d(0, 0, 1)));
        handle.addManipulator("mx", new BoneAxisMoveManipulator(handle,
                "Move X", NamedTextColor.RED, new Vector3d(1, 0, 0)));
        handle.addManipulator("my", new BoneAxisMoveManipulator(handle,
                "Move Y", NamedTextColor.GREEN, new Vector3d(0, 1, 0)));
        handle.addManipulator("mz", new BoneAxisMoveManipulator(handle,
                "Move Z", NamedTextColor.BLUE, new Vector3d(0, 0, 1)));
        return handle;
    }

    private boolean isTool(EasPlayer player, EasItem item) {
        return item.isTool() && player.hasPermission("easyarmorstands.edit");
    }

    private boolean startEditing(EasPlayer player, EasArmorStand armorStand, EasItem item, boolean cancelled) {
        if (cancelled || !isTool(player, item)) {
            return false;
        }

        Session oldSession = manager.getSession(armorStand);
        if (oldSession != null) {
            String who = oldSession.getPlayer().get(Identity.NAME).orElse("Someone else");
            player.sendMessage(Component.text()
                    .color(NamedTextColor.RED)
                    .append(Component.text(who + " is editing this armor stand")));
            return true;
        }

        if (platform.canStartSession(player, armorStand)) {
            manager.start(player, createSession(player, armorStand));
        }

        return true;
    }

    @Override
    public boolean onLeftClick(EasPlayer player, EasItem item) {
        Session session = manager.getSession(player);
        if (session != null) {
            session.handleLeftClick();
            return true;
        }
        return isTool(player, item);
    }

    @Override
    public boolean onLeftClickArmorStand(EasPlayer player, EasArmorStand armorStand, EasItem item, boolean cancelled) {
        Session session = manager.getSession(player);
        if (session != null) {
            session.handleLeftClick();
            return true;
        }
        return startEditing(player, armorStand, item, cancelled);
    }

    @Override
    public boolean onRightClick(EasPlayer player, EasItem item) {
        Session session = manager.getSession(player);
        if (session != null) {
            session.handleRightClick();
            return true;
        }
        return isTool(player, item);
    }

    @Override
    public boolean onRightClickArmorStand(EasPlayer player, EasArmorStand armorStand, EasItem item, boolean cancelled) {
        Session session = manager.getSession(player);
        if (session != null) {
            session.handleRightClick();
            return true;
        }
        return startEditing(player, armorStand, item, cancelled);
    }

    @Override
    public void onScroll(EasPlayer player, int from, int to) {
        Session session = manager.getSession(player);
        if (session != null) {
            if (!session.isToolInOffHand()) {
                manager.stop(player);
            }
        }
    }

    @Override
    public void onLogin(EasPlayer player) {
        manager.hideSkeletons(player);
    }

    @Override
    public void onJoin(EasPlayer player) {
        manager.hideSkeletons(player);
    }

    @Override
    public void onQuit(EasPlayer player) {
        manager.stop(player);
    }
}
