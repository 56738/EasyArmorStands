package me.m56738.easyarmorstands.command;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Greedy;
import cloud.commandframework.annotations.specifier.Range;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.command.annotation.RequireEntity;
import me.m56738.easyarmorstands.command.annotation.RequireSession;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.history.action.EntityDestroyAction;
import me.m56738.easyarmorstands.history.action.EntitySpawnAction;
import me.m56738.easyarmorstands.node.ValueNode;
import me.m56738.easyarmorstands.property.ResettableEntityProperty;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandCanTickProperty;
import me.m56738.easyarmorstands.property.entity.EntityCustomNameProperty;
import me.m56738.easyarmorstands.property.entity.EntityCustomNameVisibleProperty;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.session.CloneSpawner;
import me.m56738.easyarmorstands.session.EntitySpawner;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.AlignAxis;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.joml.Vector3d;
import org.joml.Vector3dc;

@CommandMethod("eas")
public class SessionCommands {
    @CommandMethod("clone")
    @CommandPermission("easyarmorstands.clone")
    @CommandDescription("Duplicate an entity")
    @RequireSession
    @RequireEntity
    public void clone(EasPlayer sender,
                      Session session,
                      Entity entity) {
        Location location = entity.getLocation();
        CloneSpawner<Entity> spawner = new CloneSpawner<>(entity);
        Entity clone = EntitySpawner.trySpawn(spawner, location, session.getPlayer());
        if (clone == null) {
            sender.sendMessage(Component.text("Unable to spawn entity", NamedTextColor.RED));
            return;
        }

        EntitySpawnAction<Entity> action = new EntitySpawnAction<>(location, spawner, clone.getUniqueId());
        EasyArmorStands.getInstance().getHistory(sender.get()).push(action);

        sender.sendMessage(Component.text("Entity cloned", NamedTextColor.GREEN));
        session.selectEntity(clone);
    }

    @CommandMethod("spawn")
    @CommandPermission("easyarmorstands.spawn")
    @CommandDescription("Spawn an armor stand and start editing it")
    @RequireSession
    public void spawn(Session session) {
        session.openSpawnMenu();
    }

    @CommandMethod("destroy")
    @CommandPermission("easyarmorstands.destroy")
    @CommandDescription("Destroy the selected armor stand")
    @RequireSession
    @RequireEntity
    public void destroy(
            EasCommandSender sender,
            Session session,
            Entity entity) {
        Player player = session.getPlayer();
        EntityDestroyAction<?> action = new EntityDestroyAction<>(entity);
        if (!EntitySpawner.tryRemove(entity, player)) {
            return;
        }
        EasyArmorStands.getInstance().getHistory(player).push(action);
        sender.sendMessage(Component.text("Entity destroyed", NamedTextColor.GREEN));
    }

    @CommandMethod("snap angle [value]")
    @CommandPermission("easyarmorstands.snap")
    @CommandDescription("Change the angle snapping increment")
    @RequireSession
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
    @RequireSession
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
    @CommandDescription("Move an entity to the middle of the block")
    @RequireSession
    @RequireEntity
    public void align(
            EasCommandSender sender,
            Session session,
            Entity entity,
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
        if (!session.tryChange(entity, EasyArmorStands.getInstance().getEntityLocationProperty(), location)) {
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

    @CommandMethod("position <position>")
    @CommandPermission("easyarmorstands.property.location")
    @RequireSession
    @RequireEntity
    public void position(EasCommandSender sender, Session session, Entity entity, @Argument("position") Location location) {
        EntityLocationProperty property = EasyArmorStands.getInstance().getEntityLocationProperty();
        Location oldLocation = property.getValue(entity);
        location.setYaw(oldLocation.getYaw());
        location.setPitch(oldLocation.getPitch());
        if (!session.tryChange(entity, property, location)) {
            sender.sendMessage(Component.text("Unable to move", NamedTextColor.RED));
            return;
        }
        session.commit();
        sender.sendMessage(Component.text("Moved to ", NamedTextColor.GREEN)
                .append(Util.formatLocation(location)));
    }

    @CommandMethod("yaw <yaw>")
    @CommandPermission("easyarmorstands.property.location")
    @RequireSession
    @RequireEntity
    public void setYaw(EasCommandSender sender, Session session, Entity entity, @Argument("yaw") float yaw) {
        EntityLocationProperty property = EasyArmorStands.getInstance().getEntityLocationProperty();
        Location location = property.getValue(entity);
        location.setYaw(yaw);
        if (!session.tryChange(entity, property, location)) {
            sender.sendMessage(Component.text("Unable to move", NamedTextColor.RED));
            return;
        }
        session.commit();
        sender.sendMessage(Component.text("Changed yaw to ", NamedTextColor.GREEN)
                .append(Util.formatAngle(yaw)));
    }

    @CommandMethod("pitch <pitch>")
    @CommandPermission("easyarmorstands.property.location")
    @RequireSession
    @RequireEntity
    public void setPitch(EasCommandSender sender, Session session, Entity entity, @Argument("pitch") float pitch) {
        EntityLocationProperty property = EasyArmorStands.getInstance().getEntityLocationProperty();
        Location location = property.getValue(entity);
        location.setPitch(pitch);
        if (!session.tryChange(entity, property, location)) {
            sender.sendMessage(Component.text("Unable to move", NamedTextColor.RED));
            return;
        }
        session.commit();
        sender.sendMessage(Component.text("Changed pitch to ", NamedTextColor.GREEN)
                .append(Util.formatAngle(pitch)));
    }

    @CommandMethod("name set <value>")
    @CommandPermission("easyarmorstands.property.name")
    @RequireSession
    @RequireEntity
    public void setName(EasCommandSender sender, Session session, Entity entity, @Argument("value") @Greedy String input) {
        Component name = MiniMessage.miniMessage().deserialize(input);
        EntityCustomNameProperty property = EasyArmorStands.getInstance().getEntityCustomNameProperty();
        boolean hadName = entity.getCustomName() != null;
        if (!session.tryChange(entity, property, name)) {
            sender.sendMessage(Component.text("Unable to change the name", NamedTextColor.RED));
            return;
        }
        if (!hadName) {
            session.tryChange(entity, EasyArmorStands.getInstance().getEntityCustomNameVisibleProperty(), true);
        }
        session.commit();
        sender.sendMessage(Component.text("Changed name to ", NamedTextColor.GREEN)
                .append(name.colorIfAbsent(NamedTextColor.WHITE)));
    }

    @CommandMethod("name clear")
    @CommandPermission("easyarmorstands.property.name")
    @RequireSession
    @RequireEntity
    public void clearName(EasCommandSender sender, Session session, Entity entity) {
        EntityCustomNameProperty property = EasyArmorStands.getInstance().getEntityCustomNameProperty();
        if (!session.tryChange(entity, property, null)) {
            sender.sendMessage(Component.text("Unable to remove the name", NamedTextColor.RED));
            return;
        }
        session.tryChange(entity, EasyArmorStands.getInstance().getEntityCustomNameVisibleProperty(), false);
        session.commit();
        sender.sendMessage(Component.text("Removed the custom name", NamedTextColor.GREEN));
    }

    @CommandMethod("name visible <value>")
    @CommandPermission("easyarmorstands.property.name.visible")
    @RequireSession
    @RequireEntity
    public void setNameVisible(EasCommandSender sender, Session session, Entity entity, @Argument("value") boolean visible) {
        EntityCustomNameVisibleProperty property = EasyArmorStands.getInstance().getEntityCustomNameVisibleProperty();
        if (!session.tryChange(entity, property, visible)) {
            sender.sendMessage(Component.text("Unable to change the name visibility", NamedTextColor.RED));
            return;
        }
        session.commit();
        sender.sendMessage(Component.text("Changed the custom name visibility to ", NamedTextColor.GREEN)
                .append(property.getValueName(visible)));
    }

    @CommandMethod("cantick <value>")
    @CommandPermission("easyarmorstands.property.armorstand.cantick")
    @RequireSession
    @RequireEntity(ArmorStand.class)
    public void setCanTick(EasCommandSender sender, Session session, ArmorStand entity, @Argument("value") boolean canTick) {
        ArmorStandCanTickProperty property = EasyArmorStands.getInstance().getArmorStandCanTickProperty();
        if (!session.tryChange(entity, property, canTick)) {
            sender.sendMessage(Component.text("Unable to change the armor stand ticking status", NamedTextColor.RED));
            return;
        }
        session.commit();
        sender.sendMessage(Component.text("Changed the armor stand ticking to ", NamedTextColor.GREEN)
                .append(property.getValueName(canTick)));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @CommandMethod("reset <property>")
    @CommandPermission("easyarmorstands.edit")
    @RequireSession
    @RequireEntity
    public void resetProperty(EasCommandSender sender, Session session, Entity entity, @Argument("property") ResettableEntityProperty property) {
        Object value = property.getResetValue();
        if (!session.tryChange(entity, property, value)) {
            sender.sendMessage(Component.text("Unable to change the property", NamedTextColor.RED));
            return;
        }
        session.commit();
        sender.sendMessage(Component.text("Reset ", NamedTextColor.GREEN)
                .append(property.getDisplayName()));
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
