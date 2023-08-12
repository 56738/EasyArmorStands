package me.m56738.easyarmorstands.command;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Greedy;
import cloud.commandframework.annotations.specifier.Range;
import cloud.commandframework.bukkit.arguments.selector.SingleEntitySelector;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.command.annotation.RequireEntity;
import me.m56738.easyarmorstands.command.annotation.RequireSession;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.element.DestroyableElement;
import me.m56738.easyarmorstands.element.Element;
import me.m56738.easyarmorstands.element.ElementType;
import me.m56738.easyarmorstands.element.MenuElement;
import me.m56738.easyarmorstands.event.PlayerCreateElementEvent;
import me.m56738.easyarmorstands.event.PlayerDestroyElementEvent;
import me.m56738.easyarmorstands.history.action.ElementCreateAction;
import me.m56738.easyarmorstands.history.action.ElementDestroyAction;
import me.m56738.easyarmorstands.node.ValueNode;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandCanTickProperty;
import me.m56738.easyarmorstands.property.entity.EntityCustomNameProperty;
import me.m56738.easyarmorstands.property.entity.EntityCustomNameVisibleProperty;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.AlignAxis;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.joml.Vector3d;
import org.joml.Vector3dc;

@CommandMethod("eas")
public class SessionCommands {
    public static void showText(Audience audience, Component type, Component text, String command) {
        String serialized = MiniMessage.miniMessage().serializeOr(text, "");
        audience.sendMessage(type
                .append(Component.text(": "))
                .append(Component.text("[Edit]", NamedTextColor.GRAY)
                        .hoverEvent(Component.text("Click to edit"))
                        .clickEvent(ClickEvent.suggestCommand(command + " " + serialized)))
                .append(Component.space())
                .append(Component.text("[Copy]", NamedTextColor.GRAY)
                        .hoverEvent(Component.text("Click to copy"))
                        .clickEvent(ClickEvent.copyToClipboard(serialized)))
                .append(Component.space())
                .append(Component.text("[Syntax]", NamedTextColor.GRAY)
                        .hoverEvent(Component.text("Click to open the MiniMessage documentation"))
                        .clickEvent(ClickEvent.openUrl("https://docs.advntr.dev/minimessage/format.html"))));
        audience.sendMessage(text);
    }

    @CommandMethod("open")
    @CommandPermission("easyarmorstands.open")
    @CommandDescription("Open the menu")
    public void open(EasPlayer sender, Element element) {
        if (!(element instanceof MenuElement)) {
            sender.sendMessage(Component.text("You're not editing an entity which allows opening a menu.", NamedTextColor.RED));
            return;
        }
        MenuElement menuElement = (MenuElement) element;
        menuElement.openMenu(sender.get());
    }

    @CommandMethod("open <entity>")
    @CommandPermission("easyarmorstands.open")
    @CommandDescription("Open the menu of an entity")
    public void open(EasPlayer sender, @Argument("entity") SingleEntitySelector selector) {
        Entity entity = selector.getEntity();
        if (entity == null) {
            sender.sendMessage(Component.text("Entity not found.", NamedTextColor.RED));
            return;
        }
        Element element = EasyArmorStands.getInstance().getEntityElementProviderRegistry().getElement(entity);
        if (element == null) {
            sender.sendMessage(Component.text("Cannot edit this entity.", NamedTextColor.RED));
            return;
        }
        if (!(element instanceof MenuElement)) {
            sender.sendMessage(Component.text("This entity doesn't have a menu.", NamedTextColor.RED));
            return;
        }
        MenuElement menuElement = (MenuElement) element;
        menuElement.openMenu(sender.get());
    }

    @CommandMethod("clone")
    @CommandPermission("easyarmorstands.clone")
    @CommandDescription("Spawn a copy of the selected entity")
    @RequireSession
    @RequireEntity
    public void clone(EasPlayer sender, Element element) {
        Player player = sender.get();
        ElementType type = element.getType();
        PropertyContainer properties = PropertyContainer.immutable(element.getProperties());

        PlayerCreateElementEvent event = new PlayerCreateElementEvent(player, type, properties);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            sender.sendMessage(Component.text("Unable to spawn entity", NamedTextColor.RED));
            return;
        }

        Element clone = type.createElement(properties);
        EasyArmorStands.getInstance().getHistory(player).push(new ElementCreateAction(clone));

        sender.sendMessage(Component.text("Entity cloned", NamedTextColor.GREEN));
    }

    @CommandMethod("spawn")
    @CommandPermission("easyarmorstands.spawn")
    @CommandDescription("Open the spawn menu")
    public void spawn(EasPlayer sender) {
        Session.openSpawnMenu(sender.get());
    }

    @CommandMethod("destroy")
    @CommandPermission("easyarmorstands.destroy")
    @CommandDescription("Destroy the selected entity")
    @RequireSession
    @RequireEntity
    public void destroy(EasCommandSender sender, Session session) {
        Player player = session.getPlayer();

        Element element = session.getElement();
        if (!(element instanceof DestroyableElement)) {
            sender.sendMessage(Component.text("You're not editing an entity which can be destroyed", NamedTextColor.RED));
            return;
        }

        PlayerDestroyElementEvent event = new PlayerDestroyElementEvent(player, element);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            sender.sendMessage(Component.text("Unable to destroy", NamedTextColor.RED));
            return;
        }

        EasyArmorStands.getInstance().getHistoryManager().getHistory(player).push(new ElementDestroyAction(element));
        ((DestroyableElement) element).destroy();
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
    @CommandDescription("Change the position snapping increment")
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
    @CommandDescription("Move the selected entity to the middle of the block")
    @RequireSession
    @RequireEntity
    public void align(
            EasCommandSender sender,
            PropertyContainer container,
            @Argument(value = "axis", defaultValue = "all") AlignAxis axis,
            @Argument(value = "value") @Range(min = "0.001", max = "1") Double value,
            @Argument(value = "offset") @Range(min = "-1", max = "1") Double offset
    ) {
        Property<Location> property = container.get(EntityLocationProperty.TYPE);
        Vector3d offsetVector = new Vector3d();
        if (value == null) {
            // None specified: Snap to the middle of the bottom of a block
            value = 1.0;
            offsetVector.set(0.5, 0.0, 0.5);
        } else if (offset != null) {
            offsetVector.set(offset, offset, offset);
        }
        Location location = property.getValue();
        Vector3dc position = axis.snap(Util.toVector3d(location), value, offsetVector, new Vector3d());
        location.setX(position.x());
        location.setY(position.y());
        location.setZ(position.z());
        if (!property.setValue(location)) {
            sender.sendMessage(Component.text("Unable to move", NamedTextColor.RED));
            return;
        }
        container.commit();
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
    @CommandDescription("Teleport the selected entity")
    @RequireSession
    @RequireEntity
    public void position(EasCommandSender sender, PropertyContainer container,
                         @Argument("position") Location location) {
        Property<Location> property = container.get(EntityLocationProperty.TYPE);
        Location oldLocation = property.getValue();
        location.setYaw(oldLocation.getYaw());
        location.setPitch(oldLocation.getPitch());
        if (!property.setValue(location)) {
            sender.sendMessage(Component.text("Unable to move", NamedTextColor.RED));
            return;
        }
        container.commit();
        sender.sendMessage(Component.text("Moved to ", NamedTextColor.GREEN)
                .append(Util.formatLocation(location)));
    }

    @CommandMethod("yaw <yaw>")
    @CommandPermission("easyarmorstands.property.location")
    @CommandDescription("Set the yaw of the selected entity")
    @RequireSession
    @RequireEntity
    public void setYaw(EasCommandSender sender, PropertyContainer container,
                       @Argument("yaw") float yaw) {
        Property<Location> property = container.get(EntityLocationProperty.TYPE);
        Location location = property.getValue();
        location.setYaw(yaw);
        if (!property.setValue(location)) {
            sender.sendMessage(Component.text("Unable to move", NamedTextColor.RED));
            return;
        }
        container.commit();
        sender.sendMessage(Component.text("Changed yaw to ", NamedTextColor.GREEN)
                .append(Util.formatAngle(yaw)));
    }

    @CommandMethod("pitch <pitch>")
    @CommandPermission("easyarmorstands.property.location")
    @CommandDescription("Set the pitch of the selected entity")
    @RequireSession
    @RequireEntity
    public void setPitch(EasCommandSender sender, PropertyContainer container,
                         @Argument("pitch") float pitch) {
        Property<Location> property = container.get(EntityLocationProperty.TYPE);
        Location location = property.getValue();
        location.setPitch(pitch);
        if (!property.setValue(location)) {
            sender.sendMessage(Component.text("Unable to move", NamedTextColor.RED));
            return;
        }
        container.commit();
        sender.sendMessage(Component.text("Changed pitch to ", NamedTextColor.GREEN)
                .append(Util.formatAngle(pitch)));
    }

    @CommandMethod("name")
    @CommandPermission("easyarmorstands.property.name")
    @CommandDescription("Show the custom name of the selected entity")
    @RequireSession
    @RequireEntity
    public void showName(EasCommandSender sender, PropertyContainer container) {
        Property<Component> property = container.get(EntityCustomNameProperty.TYPE);
        Component text = property.getValue();
        showText(sender, Component.text("Custom name", NamedTextColor.YELLOW), text, "/eas name set");
    }

    @CommandMethod("name set <value>")
    @CommandPermission("easyarmorstands.property.name")
    @CommandDescription("Set the custom name of the selected entity")
    @RequireSession
    @RequireEntity
    public void setName(EasCommandSender sender, PropertyContainer container,
                        @Argument("value") @Greedy String input) {
        Property<Component> nameProperty = container.get(EntityCustomNameProperty.TYPE);
        Property<Boolean> nameVisibleProperty = container.get(EntityCustomNameVisibleProperty.TYPE);
        Component name = MiniMessage.miniMessage().deserialize(input);
        boolean hadName = nameProperty.getValue() != null;
        if (!nameProperty.setValue(name)) {
            sender.sendMessage(Component.text("Unable to change the name", NamedTextColor.RED));
            return;
        }
        if (!hadName) {
            nameVisibleProperty.setValue(true);
        }
        container.commit();
        sender.sendMessage(Component.text("Changed name to ", NamedTextColor.GREEN)
                .append(name.colorIfAbsent(NamedTextColor.WHITE)));
    }

    @CommandMethod("name clear")
    @CommandPermission("easyarmorstands.property.name")
    @CommandDescription("Remove the custom name of the selected entity")
    @RequireSession
    @RequireEntity
    public void clearName(EasCommandSender sender, PropertyContainer container) {
        Property<Component> nameProperty = container.get(EntityCustomNameProperty.TYPE);
        Property<Boolean> nameVisibleProperty = container.get(EntityCustomNameVisibleProperty.TYPE);
        if (!nameProperty.setValue(null)) {
            sender.sendMessage(Component.text("Unable to remove the name", NamedTextColor.RED));
            return;
        }
        nameVisibleProperty.setValue(false);
        container.commit();
        sender.sendMessage(Component.text("Removed the custom name", NamedTextColor.GREEN));
    }

    @CommandMethod("name visible <value>")
    @CommandPermission("easyarmorstands.property.name.visible")
    @CommandDescription("Change the custom name visibility of the selected entity")
    @RequireSession
    @RequireEntity
    public void setNameVisible(EasCommandSender sender, PropertyContainer container,
                               @Argument("value") boolean visible) {
        Property<Boolean> property = container.get(EntityCustomNameVisibleProperty.TYPE);
        if (!property.setValue(visible)) {
            sender.sendMessage(Component.text("Unable to change the name visibility", NamedTextColor.RED));
            return;
        }
        container.commit();
        sender.sendMessage(Component.text("Changed the custom name visibility to ", NamedTextColor.GREEN)
                .append(property.getType().getValueComponent(visible)));
    }

    @CommandMethod("cantick <value>")
    @CommandPermission("easyarmorstands.property.armorstand.cantick")
    @CommandDescription("Toggle whether the selected armor stand should be ticked")
    @RequireSession
    @RequireEntity(ArmorStand.class)
    public void setCanTick(EasCommandSender sender, PropertyContainer container,
                           @Argument("value") boolean canTick) {
        Property<Boolean> property = container.getOrNull(ArmorStandCanTickProperty.TYPE);
        if (property == null) {
            sender.sendMessage(Component.text("Armor stand ticking can only be disabled on Paper servers", NamedTextColor.RED));
            return;
        }
        if (!property.setValue(canTick)) {
            sender.sendMessage(Component.text("Unable to change the armor stand ticking status", NamedTextColor.RED));
            return;
        }
        container.commit();
        sender.sendMessage(Component.text("Changed the armor stand ticking to ", NamedTextColor.GREEN)
                .append(property.getType().getValueComponent(canTick)));
    }

    // TODO Restore /eas reset?
//    @SuppressWarnings({"rawtypes", "unchecked"})
//    @CommandMethod("reset <property>")
//    @CommandPermission("easyarmorstands.edit")
//    @CommandDescription("Reset a property of the selected entity")
//    @RequireSession
//    @RequireEntity
//    public void resetProperty(EasCommandSender sender, Session session, Entity entity, @Argument("property") ResettableEntityProperty property) {
//        Object value = property.getResetValue();
//        if (!session.tryChange(property.bind(entity), value)) {
//            sender.sendMessage(Component.text("Unable to change the property", NamedTextColor.RED));
//            return;
//        }
//        session.commit();
//        sender.sendMessage(Component.text("Reset ", NamedTextColor.GREEN)
//                .append(property.getDisplayName()));
//    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @CommandMethod("set <value>")
    @CommandPermission("easyarmorstands.edit")
    @CommandDescription("Set the value of the selected tool")
    public void set(
            EasCommandSender sender,
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
