package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.ArmorStandPartBone;
import me.m56738.easyarmorstands.bone.ArmorStandPositionBone;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;

import java.util.function.Supplier;

public class ArmorStandNodeFactory implements Supplier<Node> {
    private final Session session;
    private final ArmorStand entity;

    public ArmorStandNodeFactory(Session session, ArmorStand entity) {
        this.session = session;
        this.entity = entity;
    }

    @Override
    public Node get() {
        ParentNode root = new ParentNode(session, Component.text("Select a bone"));
        root.setRoot(true);

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

            root.addNode(new ArmorStandPartNode(session, localNode, bone));
        }

        ArmorStandPositionBone positionBone = new ArmorStandPositionBone(entity);

        ParentNode positionNode = new ParentNode(session, Component.text("Position"));
        positionNode.addNode(new YawBoneNode(session, Component.text("Rotate"), NamedTextColor.GOLD, 1, positionBone));
        positionNode.addPositionNodes(session, positionBone, 3, true);

        CarryNode carryNode = new CarryNode(session, positionBone);
        BoneNode carryBoneNode = new BoneNode(session, carryNode, positionBone, Component.text("Pick up"));
        carryBoneNode.setPriority(1);
        positionNode.addNode(carryBoneNode);

        root.addNode(new BoneNode(session, positionNode, positionBone, Component.text("Position")));
        return root;
    }
}
