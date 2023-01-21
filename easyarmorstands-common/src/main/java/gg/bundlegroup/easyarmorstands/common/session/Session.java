package gg.bundlegroup.easyarmorstands.common.session;

import gg.bundlegroup.easyarmorstands.common.handle.BoneHandle;
import gg.bundlegroup.easyarmorstands.common.handle.Handle;
import gg.bundlegroup.easyarmorstands.common.handle.PositionHandle;
import gg.bundlegroup.easyarmorstands.common.manipulator.Manipulator;
import gg.bundlegroup.easyarmorstands.common.platform.EasArmorEntity;
import gg.bundlegroup.easyarmorstands.common.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.common.platform.EasFeature;
import gg.bundlegroup.easyarmorstands.common.platform.EasItem;
import gg.bundlegroup.easyarmorstands.common.platform.EasPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private static final double RANGE = 5;
    private final EasPlayer player;
    private final EasArmorStand entity;
    private final EasArmorStand skeleton;
    private final PositionHandle positionHandle;
    private final List<Handle> handles = new ArrayList<>();

    private int clickTicks = 5;
    private Handle handle;
    private Manipulator manipulator;
    private int manipulatorIndex = -1;

    public Session(EasPlayer player, EasArmorStand entity) {
        this.player = player;
        this.entity = entity;
        this.positionHandle = new PositionHandle(this);
        this.handles.add(new BoneHandle(this,
                EasArmorStand.Part.HEAD,
                Component.text("Head"),
                new Vector3d(0, 23, 0),
                new Vector3d(0, 7, 0)));
        this.handles.add(new BoneHandle(this,
                EasArmorStand.Part.BODY,
                Component.text("Body"),
                new Vector3d(0, 24, 0),
                new Vector3d(0, -12, 0)));
        this.handles.add(new BoneHandle(this,
                EasArmorStand.Part.LEFT_ARM,
                Component.text("Left arm"),
                new Vector3d(5, 22, 0),
                new Vector3d(0, -10, 0)));
        this.handles.add(new BoneHandle(this,
                EasArmorStand.Part.RIGHT_ARM,
                Component.text("Right arm"),
                new Vector3d(-5, 22, 0),
                new Vector3d(0, -10, 0)));
        this.handles.add(new BoneHandle(this,
                EasArmorStand.Part.LEFT_LEG,
                Component.text("Left leg"),
                new Vector3d(1.9, 12, 0),
                new Vector3d(0, -11, 0)));
        this.handles.add(new BoneHandle(this,
                EasArmorStand.Part.RIGHT_LEG,
                Component.text("Right leg"),
                new Vector3d(-1.9, 12, 0),
                new Vector3d(0, -11, 0)));
        this.handles.add(positionHandle);
        if (player.platform().hasFeature(EasFeature.ENTITY_GLOW)) {
            this.skeleton = entity.getWorld().spawnArmorStand(entity.getPosition(), entity.getYaw(), e -> {
                e.setVisible(false);
                e.setBasePlate(false);
                e.setArms(true);
                e.setPersistent(false);
                e.setGravity(false);
                e.setCanTick(false);
                updateSkeleton(e);
                for (EasPlayer other : player.platform().getPlayers()) {
                    if (!player.equals(other)) {
                        other.hideEntity(e);
                    }
                }
                e.setGlowing(true);
            });
        } else {
            this.skeleton = null;
        }
    }

    private boolean handleClick() {
        if (clickTicks > 0) {
            return false;
        }
        clickTicks = 5;
        return true;
    }

    public void handleLeftClick() {
        if (!handleClick()) {
            return;
        }
        if (manipulator == null && player.isSneaking()) {
            openMenu();
        }
        setHandle(handle, -1);
    }

    public void handleRightClick() {
        if (!handleClick()) {
            return;
        }
        update();
        if (handle == null) {
            return;
        }

        int nextIndex = manipulatorIndex + 1;
        int manipulatorCount = handle.getManipulators().size();
        if (manipulatorCount == 0) {
            setHandle(null, -1);
        }
        if (nextIndex >= manipulatorCount) {
            nextIndex = 0;
        }
        setHandle(handle, nextIndex);
    }

    public boolean update() {
        if (clickTicks > 0) {
            clickTicks--;
        }

        player.update();
        entity.update();

        Component title;
        if (manipulator != null) {
            handle.update();
            manipulator.update(isToolInOffHand());
            title = manipulator.getComponent();
        } else {
            updateTargetHandle();
            title = Component.empty();
        }

        if (skeleton != null) {
            updateSkeleton(skeleton);
        }

        if (handle != null) {
            player.showTitle(Title.title(title, handle.getComponent(),
                    Title.Times.times(Duration.ZERO, Ticks.duration(20), Duration.ZERO)));
        } else {
            player.clearTitle();
        }

        return player.isValid() && entity.isValid() && (skeleton == null || skeleton.isValid()) &&
                player.getEyePosition().distanceSquared(entity.getPosition()) < 100 * 100;
    }

    private void updateTargetHandle() {
        Handle bestHandle = null;
        double bestDistance = Double.POSITIVE_INFINITY;
        Vector3d temp = new Vector3d();
        for (Handle candidate : handles) {
            candidate.update();
            player.showPoint(candidate.getPosition(), NamedTextColor.WHITE);
            candidate.getPosition().sub(player.getEyePosition(), temp).mulTranspose(player.getEyeRotation());
            double distance = temp.z;
            // Eliminate forward part
            temp.z = 0;
            // Distance from straight line
            double deviationSquared = temp.lengthSquared();
            if (deviationSquared < 0.025) {
                if (distance > 0 && distance < bestDistance && distance < RANGE) {
                    bestHandle = candidate;
                    bestDistance = distance;
                }
            }
        }
        setHandle(bestHandle, -1);
    }

    public void stop() {
        if (skeleton != null) skeleton.remove();
        player.clearTitle();
    }

    private void updateSkeleton(EasArmorStand skeleton) {
        skeleton.teleport(entity.getPosition(), entity.getYaw(), 0);
        skeleton.setSmall(entity.isSmall());
        Vector3d pose = new Vector3d();
        for (EasArmorStand.Part part : EasArmorStand.Part.values()) {
            skeleton.setPose(part, entity.getPose(part, pose));
        }
    }

    public void setHandle(Handle handle, int manipulatorIndex) {
        Manipulator manipulator;
        if (manipulatorIndex == -1) {
            manipulator = null;
        } else {
            manipulator = handle.getManipulators().get(manipulatorIndex);
        }

        if (handle == this.handle && manipulator == this.manipulator) {
            return;
        }

        if (handle != null) {
            handle.update();
        }

        if (manipulator != null) {
            Vector3dc cursor;
            if (this.manipulator != null) {
                cursor = this.manipulator.getCursor();
            } else {
                cursor = handle.getPosition();
            }
            manipulator.start(cursor);
        }

        this.handle = handle;
        this.manipulator = manipulator;
        this.manipulatorIndex = manipulatorIndex;
    }

    public EasArmorStand getEntity() {
        return entity;
    }

    public EasPlayer getPlayer() {
        return player;
    }

    public void startMoving() {
        player.update();
        entity.update();
        setHandle(positionHandle, 0);
    }

    public void openMenu() {
        SessionInventory inventory = new SessionInventory(this, player.platform(),
                Component.text("Equipment"));
        player.openInventory(inventory.getInventory());
    }

    public boolean isToolInOffHand() {
        EasItem item = player.getItem(EasArmorEntity.Slot.OFF_HAND);
        if (item == null) {
            return false;
        }
        return item.isTool();
    }

    public void hideSkeleton(EasPlayer player) {
        if (skeleton != null) {
            player.hideEntity(skeleton);
        }
    }

    public List<Handle> getHandles() {
        return handles;
    }
}
