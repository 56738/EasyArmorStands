package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.ArmorStandPartBone;
import me.m56738.easyarmorstands.bone.ArmorStandPositionBone;
import me.m56738.easyarmorstands.menu.ArmorStandMenu;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.joml.Vector3dc;

import java.util.EnumMap;

public class ArmorStandRootNode extends ParentNode {
    private final Session session;
    private final ArmorStand entity;
    private final BoneNode positionNode;
    private final EnumMap<ArmorStandPart, ArmorStandPartNode> partNodes = new EnumMap<>(ArmorStandPart.class);

    public ArmorStandRootNode(Session session, ArmorStand entity) {
        super(session, Component.text("Select a bone"));
        this.session = session;
        this.entity = entity;

        setRoot(true);

        for (ArmorStandPart part : ArmorStandPart.values()) {
            ArmorStandPartBone bone = new ArmorStandPartBone(entity, part);

            ParentNode localNode = new ParentNode(session, part.getName().append(Component.text(" (local)")));
            localNode.addMoveNodes(session, bone, 3, true);
            localNode.addRotationNodes(session, bone, 1, true);

            ParentNode globalNode = new ParentNode(session, part.getName().append(Component.text(" (global)")));
            globalNode.addPositionNodes(session, bone, 3, true);
            globalNode.addRotationNodes(session, bone, 1, false);

            localNode.setNextNode(globalNode);
            globalNode.setNextNode(localNode);

            ArmorStandPartNode partNode = new ArmorStandPartNode(session, localNode, bone);
            addNode(partNode);
            partNodes.put(part, partNode);
        }

        ArmorStandPositionBone positionBone = new ArmorStandPositionBone(entity);

        ParentNode positionNode = new ParentNode(session, Component.text("Position"));
        positionNode.addNode(new YawBoneNode(session, Component.text("Rotate"), NamedTextColor.GOLD, 1, positionBone));
        positionNode.addPositionNodes(session, positionBone, 3, true);

        CarryNode carryNode = new CarryNode(session, positionBone);
        BoneNode carryBoneNode = new BoneNode(session, carryNode, positionBone, Component.text("Pick up"));
        carryBoneNode.setPriority(1);
        positionNode.addNode(carryBoneNode);

        this.positionNode = new BoneNode(session, positionNode, positionBone, Component.text("Position"));
        addNode(this.positionNode);
    }

    public BoneNode getPositionNode() {
        return positionNode;
    }

    public ArmorStandPartNode getPartNode(ArmorStandPart part) {
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
}
