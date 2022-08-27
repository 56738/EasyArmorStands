package me.m56738.easyarmorstands.module.skeleton;

import me.m56738.easyarmorstands.api.Session;
import me.m56738.easyarmorstands.api.event.PlayerEditArmorStandPoseEvent;
import me.m56738.easyarmorstands.api.event.PlayerStartArmorStandEditorEvent;
import me.m56738.easyarmorstands.api.event.PlayerStopArmorStandEditorEvent;
import me.m56738.easyarmorstands.module.Module;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class SkeletonModule implements Module, Listener {
    private final Map<Session, ArmorStand> skeletons = new HashMap<>();

    public SkeletonModule(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public String name() {
        return "skeleton";
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onStart(PlayerStartArmorStandEditorEvent event) {
        // Spawn the skeleton
        final Session session = event.getSession();
        final ArmorStand entity = session.entity();
        final ArmorStand skeleton = entity.getWorld().spawn(entity.getLocation(), ArmorStand.class);
        skeleton.setVisible(false);
        skeleton.setBasePlate(false);
        skeleton.setRemoveWhenFarAway(true);
        skeleton.setSmall(entity.isSmall());
        skeleton.setGravity(false);
        skeleton.setCollidable(false);
        skeleton.setArms(true);
        skeleton.setHeadPose(entity.getHeadPose());
        skeleton.setBodyPose(entity.getBodyPose());
        skeleton.setLeftArmPose(entity.getLeftArmPose());
        skeleton.setRightArmPose(entity.getRightArmPose());
        skeleton.setLeftLegPose(entity.getLeftLegPose());
        skeleton.setRightLegPose(entity.getRightLegPose());
        skeleton.setGlowing(true);
        final ArmorStand old = skeletons.put(session, skeleton);
        if (old != null) {
            old.remove();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEdit(PlayerEditArmorStandPoseEvent event) {
        // Apply the same modification to the skeleton
        final Session session = event.getSession();
        final ArmorStand skeleton = skeletons.get(session);
        if (skeleton == null) {
            return;
        }
        switch (event.getBone()) {
            case HEAD:
                skeleton.setHeadPose(event.getPose());
                break;
            case BODY:
                skeleton.setBodyPose(event.getPose());
                break;
            case LEFT_ARM:
                skeleton.setLeftArmPose(event.getPose());
                break;
            case RIGHT_ARM:
                skeleton.setRightArmPose(event.getPose());
                break;
            case LEFT_LEG:
                skeleton.setLeftLegPose(event.getPose());
                break;
            case RIGHT_LEG:
                skeleton.setRightLegPose(event.getPose());
                break;
        }
    }

    @EventHandler
    public void onStop(PlayerStopArmorStandEditorEvent event) {
        // Remove the skeleton
        final ArmorStand skeleton = skeletons.remove(event.getSession());
        if (skeleton != null) {
            skeleton.remove();
        }
    }

    @Override
    public void disable() {
        for (ArmorStand skeleton : skeletons.values()) {
            skeleton.remove();
        }
        skeletons.clear();
        HandlerList.unregisterAll(this);
    }
}
