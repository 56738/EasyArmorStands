package me.m56738.easyarmorstands.command;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Range;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.history.action.EntityDestroyAction;
import me.m56738.easyarmorstands.history.action.EntitySpawnAction;
import me.m56738.easyarmorstands.menu.ArmorStandMenu;
import me.m56738.easyarmorstands.node.ValueNode;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.session.SessionManager;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.joml.Vector3d;
import org.joml.Vector3dc;

@CommandMethod("eas")
public class SessionCommands {
    private final SessionManager sessionManager;

    public SessionCommands(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

//    @CommandMethod("name [name]")
//    @CommandPermission("easyarmorstands.property.name")
//    @CommandDescription("Change the name of an armor stand")
//    public void setName(EasCommandSender sender,
//                        Session session,
//                        Entity entity,
//                        ComponentCapability capability,
//                        @Argument(value = "name", description = "MiniMessage text") @Greedy String input) {
//        setName(sender, session, entity, capability, input, MiniMessage.miniMessage());
//        if (input != null && (input.contains("&") || input.contains("§"))) {
//            sender.sendMessage(Component.text()
//                    .content("This command uses the ")
//                    .append(Component.text()
//                            .content("MiniMessage")
//                            .hoverEvent(Component.text("Open documentation"))
//                            .clickEvent(ClickEvent.openUrl("https://docs.advntr.dev/minimessage/format.html"))
//                            .decorate(TextDecoration.UNDERLINED)
//                    )
//                    .append(Component.text(" format. Use "))
//                    .append(Component.text()
//                            .content("/eas lname")
//                            .hoverEvent(Component.text("/eas lname " + input))
//                            .clickEvent(ClickEvent.runCommand("/eas lname " + input))
//                            .decorate(TextDecoration.UNDERLINED)
//                    )
//                    .append(Component.text(" with legacy (&c) color codes."))
//                    .color(NamedTextColor.GRAY)
//            );
//        }
//    }
//
//    @CommandMethod("lname [name]")
//    @CommandPermission("easyarmorstands.property.name")
//    @CommandDescription("Change the name of an armor stand")
//    public void setLegacyName(EasCommandSender sender,
//                              Session session,
//                              Entity entity,
//                              ComponentCapability capability,
//                              @Argument(value = "name", description = "Legacy text (&c)") @Greedy String input) {
//        setName(sender, session, entity, capability, input, LegacyComponentSerializer.legacyAmpersand());
//    }
//
//    private void setName(EasCommandSender sender,
//                         Session session,
//                         Entity entity,
//                         ComponentCapability capability,
//                         String input, ComponentSerializer<?, ? extends Component, String> serializer) {
//        Component name = serializer.deserializeOrNull(input);
//        capability.setCustomName(entity, name);
//        entity.setCustomNameVisible(name != null);
//        if (name != null) {
//            sender.sendMessage(Component.text()
//                    .append(Component.text("Name tag changed to ", NamedTextColor.GREEN))
//                    .append(name)
//                    .hoverEvent(Component.text(input)));
//        } else {
//            sender.sendMessage(Component.text("Name tag cleared", NamedTextColor.GREEN));
//        }
//    }

    @CommandMethod("clone")
    @CommandPermission("easyarmorstands.clone")
    @CommandDescription("Duplicate an entity")
    public void clone(EasCommandSender sender,
                      Session session,
                      Entity entity) {
        EntitySpawnAction<Entity> action = new EntitySpawnAction<>(entity);
        action.execute();
        Entity clone = action.findEntity();

        sender.sendMessage(Component.text("Entity cloned", NamedTextColor.GREEN));

        if (clone != null) {
            session.selectEntity(clone);
        }
    }

//    @CommandMethod("spawn")
//    @CommandPermission("easyarmorstands.spawn")
//    @CommandDescription("Spawn an armor stand and start editing it")
//    public void spawn(EasPlayer player) {
//        sessionManager.spawnAndStart(player.get());
//    }

    @CommandMethod("destroy")
    @CommandPermission("easyarmorstands.destroy")
    @CommandDescription("Destroy the selected armor stand")
    public void destroy(
            EasCommandSender sender,
            Session session,
            Entity entity) {
        Player player = session.getPlayer();
        EasyArmorStands.getInstance().getHistory(player).push(new EntityDestroyAction<>(entity));
        entity.remove();
        sender.sendMessage(Component.text("Entity destroyed", NamedTextColor.GREEN));
    }

    @CommandMethod("open")
    @CommandPermission("easyarmorstands.open")
    @CommandDescription("Open the menu")
    public void openMenu(EasPlayer player, Session session, ArmorStand entity) {
        player.get().openInventory(new ArmorStandMenu(session, entity).getInventory());
    }

    @CommandMethod("snap angle [value]")
    @CommandPermission("easyarmorstands.snap")
    @CommandDescription("Change the angle snapping increment")
    public void setAngleSnapIncrement(
            EasCommandSender sender,
            Session session,
            @Argument(value = "value") @Range(min = "0", max = "90") Double value) {
        if (value == null) {
            value = Session.DEFAULT_ANGLE_SNAP_INCREMENT;
            if (value == session.getAngleSnapIncrement()) {
                value = 0.0;
            }
        }
        session.setAngleSnapIncrement(value);
        sender.sendMessage(Component.text("Set angle snapping increment to " + value + "°", NamedTextColor.GREEN));
    }

    @CommandMethod("snap move [value]")
    @CommandPermission("easyarmorstands.snap")
    @CommandDescription("Change the movement snapping increment")
    public void setSnapIncrement(
            EasCommandSender sender,
            Session session,
            @Argument(value = "value") @Range(min = "0", max = "10") Double value) {
        if (value == null) {
            value = Session.DEFAULT_SNAP_INCREMENT;
            if (value == session.getSnapIncrement()) {
                value = 0.0;
            }
        }
        session.setSnapIncrement(value);
        sender.sendMessage(Component.text("Set movement snapping increment to " + value, NamedTextColor.GREEN));
    }

    @CommandMethod("align [axis] [value] [offset]")
    @CommandPermission("easyarmorstands.align")
    @CommandDescription("Move an armor stand to the middle of the block")
    public void align(
            EasCommandSender sender,
            Session session,
            Entity entity,
            EntityLocationProperty property,
            @Argument(value = "axis", defaultValue = "all") AlignAxis axis,
            @Argument(value = "value") @Range(min = "0.001", max = "1") Double value,
            @Argument(value = "offset") @Range(min = "-1", max = "1") Double offset
    ) {
        Vector3d offsetVector = new Vector3d();
        if (value == null) {
            // None specified: Snap to the middle of the bottom of a block
            value = 1.0;
            offsetVector.set(0.5, 0.0, 0.5);
        } else if (offset != null) {
            offsetVector.set(offset, offset, offset);
        }
        Vector3dc position = axis.snap(Util.toVector3d(entity.getLocation()), value, offsetVector, new Vector3d());
        Location location = entity.getLocation();
        location.setX(position.x());
        location.setY(position.y());
        location.setZ(position.z());
        if (!session.setProperty(entity, property, location)) {
            sender.sendMessage(Component.text("Unable to move", NamedTextColor.RED));
            return;
        }
        session.commit();
        sender.sendMessage(Component.text()
                .content("Moved to ")
                .append(Component.text(Util.POSITION_FORMAT.format(position.x()), TextColor.color(0xFF7777)))
                .append(Component.text(", "))
                .append(Component.text(Util.POSITION_FORMAT.format(position.y()), TextColor.color(0x77FF77)))
                .append(Component.text(", "))
                .append(Component.text(Util.POSITION_FORMAT.format(position.z()), TextColor.color(0x7777FF)))
                .color(NamedTextColor.GREEN));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @CommandMethod("set <value>")
    @CommandPermission("easyarmorstands.edit")
    public void set(
            EasCommandSender sender,
            Session session,
            ValueNode node,
            @Argument(value = "value", parserName = "node_value") Object value
    ) {
        node.setValue(value);
        sender.sendMessage(Component.text()
                .content("Set ")
                .append(node.getName())
                .append(Component.text(" to "))
                .append(node.getValueComponent(value))
                .color(NamedTextColor.GREEN));
    }
}
