package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.bone.ArmorStandPartPoseBone;
import me.m56738.easyarmorstands.bone.ArmorStandPartPositionBone;
import me.m56738.easyarmorstands.bone.ArmorStandPositionBone;
import me.m56738.easyarmorstands.capability.glow.GlowCapability;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import me.m56738.easyarmorstands.capability.persistence.PersistenceCapability;
import me.m56738.easyarmorstands.capability.spawn.SpawnCapability;
import me.m56738.easyarmorstands.capability.tick.TickCapability;
import me.m56738.easyarmorstands.capability.visibility.VisibilityCapability;
import me.m56738.easyarmorstands.menu.ArmorStandMenu;
import me.m56738.easyarmorstands.particle.ParticleColor;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.joml.Vector3dc;

import java.util.EnumMap;

public class ArmorStandRootNode extends MenuNode implements EntityNode {
    private final Session session;
    private final ArmorStand entity;
    private final PositionBoneButton positionButton;
    private final EnumMap<ArmorStandPart, ArmorStandPartButton> partButtons = new EnumMap<>(ArmorStandPart.class);
    private ArmorStand skeleton;

    public ArmorStandRootNode(Session session, ArmorStand entity) {
        super(session, Component.text("Select a bone"));
        this.session = session;
        this.entity = entity;

        setRoot(true);

        for (ArmorStandPart part : ArmorStandPart.values()) {
            ArmorStandPartPositionBone positionBone = new ArmorStandPartPositionBone(session, entity, part);
            ArmorStandPartPoseBone poseBone = new ArmorStandPartPoseBone(session, entity, part);

            MenuNode localNode = new MenuNode(session, part.getDisplayName().append(Component.text(" (local)")));
            localNode.addMoveButtons(session, positionBone, poseBone, 3, true);
            localNode.addRotationButtons(session, poseBone, 1, poseBone);

            MenuNode globalNode = new MenuNode(session, part.getDisplayName().append(Component.text(" (global)")));
            globalNode.addPositionButtons(session, positionBone, 3, true);
            globalNode.addRotationButtons(session, poseBone, 1, null);

            localNode.setNextNode(globalNode);
            globalNode.setNextNode(localNode);

            ArmorStandPartButton partButton = new ArmorStandPartButton(session, entity, part, localNode);
            addButton(partButton);
            partButtons.put(part, partButton);
        }

        ArmorStandPositionBone positionBone = new ArmorStandPositionBone(session, entity);

        MenuNode positionNode = new MenuNode(session, Component.text("Position"));
        positionNode.addYawButton(session, positionBone, 1);
        positionNode.addPositionButtons(session, positionBone, 3, true);
        positionNode.addCarryButtonWithYaw(session, positionBone);

        this.positionButton = new PositionBoneButton(session, positionBone, positionNode, Component.text("Position"), ParticleColor.YELLOW);
        addButton(this.positionButton);
    }

    @Override
    public void onUpdate(Vector3dc eyes, Vector3dc target) {
        super.onUpdate(eyes, target);
        if (skeleton != null) {
            updateSkeleton(skeleton);
        }
    }

    @Override
    public void onInactiveUpdate() {
        super.onInactiveUpdate();
        if (skeleton != null) {
            updateSkeleton(skeleton);
        }
    }

    public PositionBoneButton getPositionButton() {
        return positionButton;
    }

    public ArmorStandPartButton getPartButton(ArmorStandPart part) {
        return partButtons.get(part);
    }

    @Override
    public void onAdd() {
        if (skeleton != null) {
            skeleton.remove();
        }

        EasyArmorStands plugin = EasyArmorStands.getInstance();
        GlowCapability glowCapability = plugin.getCapability(GlowCapability.class);
        ParticleCapability particleCapability = plugin.getCapability(ParticleCapability.class);
        if (glowCapability != null && !particleCapability.isLineVisibleThroughWall()) {
            // Entity glowing is supported and particles are not visible through walls
            // Spawn a glowing skeleton instead
            SpawnCapability spawnCapability = plugin.getCapability(SpawnCapability.class);
            skeleton = spawnCapability.spawnEntity(entity.getLocation(), ArmorStand.class, e -> {
                e.setVisible(false);
                e.setBasePlate(false);
                e.setArms(true);
                e.setGravity(false);
                PersistenceCapability persistenceCapability = plugin.getCapability(PersistenceCapability.class);
                if (persistenceCapability != null) {
                    persistenceCapability.setPersistent(e, false);
                }
                TickCapability tickCapability = plugin.getCapability(TickCapability.class);
                if (tickCapability != null) {
                    tickCapability.setCanTick(e, false);
                }
                updateSkeleton(e);
                VisibilityCapability visibilityCapability = plugin.getCapability(VisibilityCapability.class);
                if (visibilityCapability != null) {
                    Player player = session.getPlayer();
                    for (Player other : Bukkit.getOnlinePlayers()) {
                        if (player != other) {
                            visibilityCapability.hideEntity(other, plugin, e);
                        }
                    }
                }
                glowCapability.setGlowing(e, true);
            });
        } else {
            skeleton = null;
        }
    }

    private void updateSkeleton(ArmorStand skeleton) {
        skeleton.teleport(entity.getLocation());
        skeleton.setSmall(entity.isSmall());
        for (ArmorStandPart part : ArmorStandPart.values()) {
            part.setPose(skeleton, part.getPose(entity));
        }
    }

    public void hideSkeleton(Player player) {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        VisibilityCapability visibilityCapability = plugin.getCapability(VisibilityCapability.class);
        if (skeleton != null && visibilityCapability != null) {
            visibilityCapability.hideEntity(player, plugin, skeleton);
        }
    }

    @Override
    public void onRemove() {
        if (skeleton != null) {
            skeleton.remove();
        }
    }

    @Override
    public boolean onClick(Vector3dc eyes, Vector3dc target, ClickContext context) {
        Player player = session.getPlayer();
        if (context.getType() == ClickType.LEFT_CLICK && player.hasPermission("easyarmorstands.open")) {
            player.openInventory(new ArmorStandMenu(session, entity).getInventory());
            return true;
        }
        return super.onClick(eyes, target, context);
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public boolean isValid() {
        return super.isValid() && entity.isValid();
    }
}
