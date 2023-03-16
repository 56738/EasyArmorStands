package me.m56738.easyarmorstands.session.v1_19_4;

import me.m56738.easyarmorstands.bone.v1_19_4.DisplayBone;
import me.m56738.easyarmorstands.node.ParentNode;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.joml.Matrix4d;


public class DisplaySessionListener implements Listener {
//    @EventHandler
//    public void onSneak(PlayerToggleSneakEvent event) {
//        if (EasyArmorStands.getInstance().getSessionManager().getSession(event.getPlayer()) != null) {
//            return;
//        }
//
//        if (event.isSneaking()) {
//            ItemDisplay display = event.getPlayer().getWorld().spawn(event.getPlayer().getLocation(), ItemDisplay.class);
//            display.setItemStack(new ItemStack(Material.CHEST));
//            display.setPersistent(false);
//            DisplaySession session = createSession(event.getPlayer(), display);
//            EasyArmorStands.getInstance().getSessionManager().start(session);
//        }
//    }

    private DisplaySession createSession(Player player, Display entity) {
        DisplaySession session = new DisplaySession(player, entity);
        session.setTransformation(new Matrix4d().scale(1, 2, 3));

        DisplayBone bone = new DisplayBone(session);

        ParentNode localNode = new ParentNode(session, Component.text("Local"));
        localNode.addMoveNodes(session, bone, 2, false);
        localNode.addRotationNodes(session, bone, 1, true);
        localNode.addScaleNodes(session, bone, 2);

        ParentNode globalNode = new ParentNode(session, Component.text("Global"));
        globalNode.addPositionNodes(session, bone, 3, true);
        globalNode.addRotationNodes(session, bone, 1, false);

        localNode.setNextNode(globalNode);
        globalNode.setNextNode(localNode);

        session.pushNode(localNode);
        return session;
    }
}
