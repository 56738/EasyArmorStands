package gg.bundlegroup.easyarmorstands.session;

import gg.bundlegroup.easyarmorstands.handle.BoneHandle;
import gg.bundlegroup.easyarmorstands.handle.Handle;
import gg.bundlegroup.easyarmorstands.handle.PositionHandle;
import gg.bundlegroup.easyarmorstands.manipulator.Manipulator;
import gg.bundlegroup.easyarmorstands.platform.EasArmorEntity;
import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.platform.EasFeature;
import gg.bundlegroup.easyarmorstands.platform.EasItem;
import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import org.jetbrains.annotations.Nullable;
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
                e.setSmall(entity.isSmall());
                e.setGravity(false);
                e.setCanTick(false);
                Vector3d pose = new Vector3d();
                for (EasArmorStand.Part part : EasArmorStand.Part.values()) {
                    e.setPose(part, entity.getPose(part, pose));
                }
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
        if (manipulatorIndex == -1 && player.isSneaking()) {
            openMenu();
        }
        manipulatorIndex = -1;
    }

    public void handleRightClick() {
        if (!handleClick()) {
            return;
        }
        update();
        if (handle == null) {
            return;
        }

        Vector3dc cursor;
        if (manipulatorIndex == -1) {
            cursor = handle.getPosition();
        } else {
            cursor = handle.getManipulators().get(manipulatorIndex).getCursor();
        }

        handle.update();
        manipulatorIndex++;
        List<Manipulator> manipulators = handle.getManipulators();
        if (manipulators.isEmpty()) {
            manipulatorIndex = -1;
            return;
        } else if (manipulatorIndex >= manipulators.size()) {
            manipulatorIndex = 0;
        }
        manipulators.get(manipulatorIndex).start(cursor);
    }

    public boolean update() {
        if (skeleton != null) {
            skeleton.teleport(entity.getPosition(), entity.getYaw(), 0);
        }

        if (clickTicks > 0) {
            clickTicks--;
        }

        player.update();
        entity.update();

        Component title;
        if (manipulatorIndex != -1) {
            handle.update();
            Manipulator manipulator = handle.getManipulators().get(manipulatorIndex);
            manipulator.update();
            title = manipulator.getComponent();
        } else {
            updateTargetHandle();
            title = Component.empty();
        }

        if (handle != null) {

            Title.Times times = Title.Times.times(Duration.ZERO, Ticks.duration(20), Duration.ZERO);
            player.showTitle(Title.title(title, handle.getComponent(), times));
        } else {
            player.clearTitle();
        }

        return player.isValid() && entity.isValid() && (skeleton == null || skeleton.isValid());
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
        this.handle = bestHandle;
    }

    public void stop() {
        if (skeleton != null) skeleton.remove();
        player.clearTitle();
    }

    public EasArmorStand getEntity() {
        return entity;
    }

    public @Nullable EasArmorStand getSkeleton() {
        return skeleton;
    }

    public EasPlayer getPlayer() {
        return player;
    }

    public void startMoving() {
        player.update();
        entity.update();
        manipulatorIndex = 0;
        handle = positionHandle;
        handle.update();
        handle.getManipulators().get(0).start(handle.getPosition());
    }

    public void openMenu() {
        SessionInventory inventory = new SessionInventory(entity, player.platform(),
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
}
