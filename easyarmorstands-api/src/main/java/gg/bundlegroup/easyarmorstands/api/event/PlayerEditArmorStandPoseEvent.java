package gg.bundlegroup.easyarmorstands.api.event;

import gg.bundlegroup.easyarmorstands.api.BoneType;
import gg.bundlegroup.easyarmorstands.api.Session;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.util.EulerAngle;

public class PlayerEditArmorStandPoseEvent extends ArmorStandSessionEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final BoneType bone;
    private final EulerAngle pose;
    private boolean cancelled;

    public PlayerEditArmorStandPoseEvent(Session session, BoneType bone, EulerAngle pose) {
        super(session);
        this.bone = bone;
        this.pose = pose;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public BoneType getBone() {
        return bone;
    }

    public EulerAngle getPose() {
        return pose;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
