package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.ArmorStandPartBone;
import me.m56738.easyarmorstands.bone.ArmorStandPositionBone;
import me.m56738.easyarmorstands.menu.ArmorStandMenu;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.joml.Vector3dc;

import java.util.EnumMap;

public class ArmorStandRootNode extends MenuNode implements EntityNode {
    private final Session session;
    private final ArmorStand entity;
    private final BoneButton positionNode;
    private final EnumMap<ArmorStandPart, ArmorStandPartButton> partNodes = new EnumMap<>(ArmorStandPart.class);

    public ArmorStandRootNode(Session session, ArmorStand entity) {
        super(session, Component.text("Select a bone"));
        this.session = session;
        this.entity = entity;

        setRoot(true);

        for (ArmorStandPart part : ArmorStandPart.values()) {
            ArmorStandPartBone bone = new ArmorStandPartBone(entity, part);

            MenuNode localNode = new MenuNode(session, part.getName().append(Component.text(" (local)")));
            localNode.addMoveButtons(session, bone, 3, true);
            localNode.addRotationButtons(session, bone, 1, true);

            MenuNode globalNode = new MenuNode(session, part.getName().append(Component.text(" (global)")));
            globalNode.addPositionButtons(session, bone, 3, true);
            globalNode.addRotationButtons(session, bone, 1, false);

            localNode.setNextNode(globalNode);
            globalNode.setNextNode(localNode);

            ArmorStandPartButton partNode = new ArmorStandPartButton(session, bone, localNode);
            addButton(partNode);
            partNodes.put(part, partNode);
        }

        ArmorStandPositionBone positionBone = new ArmorStandPositionBone(entity);

        MenuNode positionNode = new MenuNode(session, Component.text("Position"));
        positionNode.addButton(new YawBoneNode(session, Component.text("Rotate"), NamedTextColor.GOLD, 1, positionBone));
        positionNode.addPositionButtons(session, positionBone, 3, true);

        CarryNode carryNode = new CarryNode(session, positionBone);
        BoneButton carryBoneNode = new BoneButton(session, positionBone, carryNode, Component.text("Pick up"));
        carryBoneNode.setPriority(1);
        positionNode.addButton(carryBoneNode);

        this.positionNode = new BoneButton(session, positionBone, positionNode, Component.text("Position"));
        addButton(this.positionNode);
    }

    public BoneButton getPositionNode() {
        return positionNode;
    }

    public ArmorStandPartButton getPartNode(ArmorStandPart part) {
        return partNodes.get(part);
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
}
