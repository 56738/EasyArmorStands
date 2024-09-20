package me.m56738.easyarmorstands.command;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.ScaleButton;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleToolSession;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.element.EntityElement;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.command.annotation.PropertyPermission;
import me.m56738.easyarmorstands.command.requirement.RequireElement;
import me.m56738.easyarmorstands.command.requirement.RequireElementSelection;
import me.m56738.easyarmorstands.command.requirement.RequireSession;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.command.util.ElementSelection;
import me.m56738.easyarmorstands.editor.node.ValueNode;
import me.m56738.easyarmorstands.group.Group;
import me.m56738.easyarmorstands.group.node.GroupRootNode;
import me.m56738.easyarmorstands.history.action.ElementCreateAction;
import me.m56738.easyarmorstands.history.action.ElementDestroyAction;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.property.TrackedPropertyContainer;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandCanTickProperty;
import me.m56738.easyarmorstands.session.SessionImpl;
import me.m56738.easyarmorstands.session.SessionSnapper;
import me.m56738.easyarmorstands.util.AlignAxis;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotation.specifier.Greedy;
import org.incendo.cloud.annotation.specifier.Range;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Default;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.bukkit.data.MultipleEntitySelector;
import org.incendo.cloud.bukkit.data.SingleEntitySelector;
import org.incendo.cloud.minecraft.extras.annotation.specifier.Decoder;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Command("eas")
public class SessionCommands {
    public static void showText(Audience audience, Component type, @Nullable Component text, String command) {
        String serialized = MiniMessage.miniMessage().serializeOr(text, "");
        audience.sendMessage(Component.text()
                .append(Message.title(type))
                .append(Component.space())
                .append(Message.chatButton("easyarmorstands.button.text.edit")
                        .hoverEvent(Message.hover("easyarmorstands.click-to-edit"))
                        .clickEvent(ClickEvent.suggestCommand(command + " " + serialized)))
                .append(Component.space())
                .append(Message.chatButton("easyarmorstands.button.text.copy")
                        .hoverEvent(Message.hover("easyarmorstands.click-to-copy"))
                        .clickEvent(ClickEvent.copyToClipboard(serialized)))
                .append(Component.space())
                .append(Message.chatButton("easyarmorstands.button.text.syntax-help")
                        .hoverEvent(Message.hover("easyarmorstands.click-to-open-minimessage"))
                        .clickEvent(ClickEvent.openUrl("https://docs.advntr.dev/minimessage/format.html"))));
        if (text == null) {
            audience.sendMessage(Message.hint("easyarmorstands.hint.not-set"));
        } else {
            audience.sendMessage(text);
        }
    }

    @Command("open")
    @Permission(Permissions.OPEN)
    @CommandDescription("easyarmorstands.command.description.open")
    @RequireElement
    public void open(EasPlayer sender, Element element) {
        if (!(element instanceof MenuElement)) {
            sender.sendMessage(Message.error("easyarmorstands.error.menu-unsupported"));
            return;
        }
        MenuElement menuElement = (MenuElement) element;
        menuElement.openMenu(sender.get());
    }

    @Command("open <entity>")
    @Permission(Permissions.OPEN)
    @CommandDescription("easyarmorstands.command.description.open.entity")
    public void open(EasPlayer sender, @Argument("entity") SingleEntitySelector selector) {
        Entity entity = selector.single();
        Element element = EasyArmorStandsPlugin.getInstance().entityElementProviderRegistry().getElement(entity);
        if (element == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.unsupported-entity"));
            return;
        }
        if (!(element instanceof MenuElement)) {
            sender.sendMessage(Message.error("easyarmorstands.error.menu-unsupported"));
            return;
        }
        MenuElement menuElement = (MenuElement) element;
        if (!menuElement.canEdit(sender.get())) {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-edit"));
            return;
        }
        menuElement.openMenu(sender.get());
    }

    @Command("group <entities>")
    @Permission(Permissions.GROUP)
    @CommandDescription("easyarmorstands.command.description.group")
    @RequireSession
    public void group(EasPlayer sender, Session session, @Argument("entities") MultipleEntitySelector selector) {
        Group group = new Group(session);
        for (Entity entity : selector.values()) {
            Element element = EasyArmorStandsPlugin.getInstance().entityElementProviderRegistry().getElement(entity);
            if (element instanceof EditableElement) {
                EditableElement editableElement = (EditableElement) element;
                if (sender.canEditElement(editableElement)) {
                    if (group.getMembers().size() >= EasyArmorStandsPlugin.getInstance().getConfiguration().editorSelectionLimit) {
                        sender.sendMessage(Message.error("easyarmorstands.error.group-too-big"));
                        return;
                    }
                    group.addMember(editableElement);
                }
            }
        }
        if (group.getMembers().isEmpty()) {
            return;
        }
        GroupRootNode node = new GroupRootNode(group);
        session.pushNode(node);
    }

    @Command("clone")
    @Permission(Permissions.CLONE)
    @CommandDescription("easyarmorstands.command.description.clone")
    @RequireElementSelection
    public void clone(EasPlayer sender, ElementSelection selection) {
        List<Element> clones = new ArrayList<>();
        for (Element element : selection.elements()) {
            ElementType type = element.getType();
            PropertyContainer properties = PropertyContainer.immutable(element.getProperties());
            if (sender.canCreateElement(type, properties)) {
                Element clone = type.createElement(properties);
                if (clone != null) {
                    clones.add(clone);
                }
            }
        }

        int count = clones.size();
        Component description;
        if (count == 0) {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-clone"));
            return;
        } else if (count == 1) {
            sender.sendMessage(Message.success("easyarmorstands.success.entity-cloned"));
            description = Message.component("easyarmorstands.history.clone-element");
        } else {
            sender.sendMessage(Message.success("easyarmorstands.success.entity-cloned.multiple", Component.text(count)));
            description = Message.component("easyarmorstands.history.clone-group");
        }

        List<ElementCreateAction> actions = new ArrayList<>();
        for (Element clone : clones) {
            actions.add(new ElementCreateAction(clone));
        }
        sender.history().push(actions, description);
    }

    @Command("spawn")
    @Permission(Permissions.SPAWN)
    @CommandDescription("easyarmorstands.command.description.spawn")
    public void spawn(EasPlayer sender) {
        Player player = sender.get();
        Menu menu = EasyArmorStandsPlugin.getInstance().createSpawnMenu(player);
        player.openInventory(menu.getInventory());
    }

    @Command("destroy")
    @Permission(Permissions.DESTROY)
    @CommandDescription("easyarmorstands.command.description.destroy")
    @RequireElementSelection
    public void destroy(EasPlayer sender, ElementSelection selection) {
        List<ElementDestroyAction> actions = new ArrayList<>();
        for (Element element : selection.elements()) {
            if (!(element instanceof DestroyableElement)) {
                continue;
            }

            DestroyableElement destroyableElement = (DestroyableElement) element;
            if (!sender.canDestroyElement(destroyableElement)) {
                continue;
            }

            actions.add(new ElementDestroyAction(element));
            destroyableElement.destroy();
        }

        int count = actions.size();
        sender.history().push(actions, Message.component("easyarmorstands.history.destroy-elements", Component.text(count)));

        if (count > 1) {
            sender.sendMessage(Message.success("easyarmorstands.success.entity-destroyed.multiple", Component.text(count)));
        } else if (count == 1) {
            sender.sendMessage(Message.success("easyarmorstands.success.entity-destroyed"));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.destroy-unsupported"));
        }
    }

    @Command("snap angle [value]")
    @Permission(Permissions.SNAP)
    @CommandDescription("easyarmorstands.command.description.snap.angle")
    @RequireSession
    public void setAngleSnapIncrement(
            EasPlayer sender,
            SessionImpl session,
            @Argument(value = "value") @Range(min = "0", max = "90") Double value) {
        if (value == null) {
            value = SessionSnapper.DEFAULT_ANGLE_INCREMENT;
            if (value == session.snapper().getAngleIncrement()) {
                value = 0.0;
            }
        } else {
            value = Math.toRadians(value);
        }
        session.snapper().setAngleIncrement(value);
        sender.sendMessage(Message.success("easyarmorstands.success.snap-changed.angle", Component.text(Math.toDegrees(value))));
    }

    @Command("snap move [value]")
    @Permission(Permissions.SNAP)
    @CommandDescription("easyarmorstands.command.description.snap.move")
    @RequireSession
    public void setSnapIncrement(
            EasPlayer sender,
            SessionImpl session,
            @Argument(value = "value") @Range(min = "0", max = "10") Double value) {
        if (value == null) {
            value = SessionSnapper.DEFAULT_POSITION_INCREMENT;
            if (value == session.snapper().getPositionIncrement()) {
                value = 0.0;
            }
        }
        session.snapper().setOffsetIncrement(value);
        session.snapper().setPositionIncrement(value);
        sender.sendMessage(Message.success("easyarmorstands.success.snap-changed.position", Component.text(value)));
    }

    @Command("align [axis] [value] [offset]")
    @Permission(Permissions.ALIGN)
    @CommandDescription("easyarmorstands.command.description.align")
    @RequireElement
    public void align(
            EasPlayer sender,
            Element element,
            @Argument(value = "axis") @Default("all") AlignAxis axis,
            @Argument(value = "value") @Range(min = "0.001", max = "1") Double value,
            @Argument(value = "offset") @Range(min = "-1", max = "1") Double offset
    ) {
        PropertyContainer properties = new TrackedPropertyContainer(element, sender);
        Property<Location> property = properties.get(EntityPropertyTypes.LOCATION);
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
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-move"));
            return;
        }
        properties.commit();
        sender.sendMessage(Message.success("easyarmorstands.success.moved", Util.formatPosition(position)));
    }

    @Command("position <position>")
    @PropertyPermission("easyarmorstands:entity/location")
    @CommandDescription("easyarmorstands.command.description.position")
    @RequireElement
    public void position(EasPlayer sender, Element element, @Argument("position") Location location) {
        PropertyContainer properties = new TrackedPropertyContainer(element, sender);
        Property<Location> property = properties.get(EntityPropertyTypes.LOCATION);
        Location oldLocation = property.getValue();
        location.setYaw(oldLocation.getYaw());
        location.setPitch(oldLocation.getPitch());
        if (!property.setValue(location)) {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-move"));
            return;
        }
        properties.commit();
        sender.sendMessage(Message.success("easyarmorstands.success.moved", Util.formatLocation(location)));
    }

    @Command("yaw <yaw>")
    @PropertyPermission("easyarmorstands:entity/location")
    @CommandDescription("easyarmorstands.command.description.yaw")
    @RequireElement
    public void setYaw(EasPlayer sender, Element element, @Argument("yaw") float yaw) {
        PropertyContainer properties = new TrackedPropertyContainer(element, sender);
        Property<Location> property = properties.get(EntityPropertyTypes.LOCATION);
        Location location = property.getValue();
        location.setYaw(yaw);
        if (!property.setValue(location)) {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-move"));
            return;
        }
        properties.commit();
        sender.sendMessage(Message.success("easyarmorstands.success.changed-yaw", Util.formatDegrees(yaw)));
    }

    @Command("pitch <pitch>")
    @PropertyPermission("easyarmorstands:entity/location")
    @CommandDescription("easyarmorstands.command.description.pitch")
    @RequireElement
    public void setPitch(EasPlayer sender, Element element, @Argument("pitch") float pitch) {
        PropertyContainer properties = new TrackedPropertyContainer(element, sender);
        Property<Location> property = properties.get(EntityPropertyTypes.LOCATION);
        Location location = property.getValue();
        location.setPitch(pitch);
        if (!property.setValue(location)) {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-move"));
            return;
        }
        properties.commit();
        sender.sendMessage(Message.success("easyarmorstands.success.changed-pitch", Util.formatDegrees(pitch)));
    }

    @Command("name")
    @PropertyPermission("easyarmorstands:entity/custom_name")
    @CommandDescription("easyarmorstands.command.description.name")
    @RequireElementSelection
    public void showName(EasPlayer sender, ElementSelection selection) {
        PropertyContainer properties = selection.properties(sender);
        Property<Optional<Component>> property = properties.getOrNull(EntityPropertyTypes.CUSTOM_NAME);
        if (property == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.name-unsupported"));
            return;
        }
        Component text = property.getValue().orElse(null);
        showText(sender, EntityPropertyTypes.CUSTOM_NAME.getName(), text, "/eas name set");
    }

    @Command("name set <value>")
    @PropertyPermission("easyarmorstands:entity/custom_name")
    @CommandDescription("easyarmorstands.command.description.name.set")
    @RequireElementSelection
    public void setName(EasPlayer sender, ElementSelection selection, @Argument("value") @Decoder.MiniMessage @Greedy Component name) {
        PropertyContainer properties = selection.properties(sender);
        Property<Optional<Component>> nameProperty = properties.getOrNull(EntityPropertyTypes.CUSTOM_NAME);
        if (nameProperty == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.name-unsupported"));
            return;
        }
        Property<Boolean> nameVisibleProperty = properties.getOrNull(EntityPropertyTypes.CUSTOM_NAME_VISIBLE);
        boolean hadName = nameProperty.getValue().isPresent();
        if (!nameProperty.setValue(Optional.of(name))) {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            return;
        }
        if (!hadName && nameVisibleProperty != null) {
            nameVisibleProperty.setValue(true);
        }
        properties.commit();
        sender.sendMessage(Message.success("easyarmorstands.success.changed-name", name.colorIfAbsent(NamedTextColor.WHITE)));
    }

    @Command("name clear")
    @PropertyPermission("easyarmorstands:entity/custom_name")
    @CommandDescription("easyarmorstands.command.description.name.clear")
    @RequireElementSelection
    public void clearName(EasPlayer sender, ElementSelection selection) {
        PropertyContainer properties = selection.properties(sender);
        if (properties == null) {
            return;
        }
        Property<Optional<Component>> nameProperty = properties.getOrNull(EntityPropertyTypes.CUSTOM_NAME);
        if (nameProperty == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.name-unsupported"));
            return;
        }
        Property<Boolean> nameVisibleProperty = properties.getOrNull(EntityPropertyTypes.CUSTOM_NAME_VISIBLE);
        if (!nameProperty.setValue(Optional.empty())) {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            return;
        }
        if (nameVisibleProperty != null) {
            nameVisibleProperty.setValue(false);
        }
        properties.commit();
        sender.sendMessage(Message.success("easyarmorstands.success.removed-name"));
    }

    @Command("name visible <value>")
    @PropertyPermission("easyarmorstands:entity/custom_name/visible")
    @CommandDescription("easyarmorstands.command.description.name.visible")
    @RequireElementSelection
    public void setNameVisible(EasPlayer sender, ElementSelection selection, @Argument("value") boolean visible) {
        PropertyContainer properties = selection.properties(sender);
        Property<Boolean> property = properties.getOrNull(EntityPropertyTypes.CUSTOM_NAME_VISIBLE);
        if (property == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.name-unsupported"));
            return;
        }
        if (!property.setValue(visible)) {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            return;
        }
        properties.commit();
        sender.sendMessage(Message.success("easyarmorstands.success.changed-name-visibility", property.getType().getValueComponent(visible)));
    }

    @Command("cantick <value>")
    @PropertyPermission("easyarmorstands:armor_stand/can_tick")
    @CommandDescription("easyarmorstands.command.description.cantick")
    @RequireElementSelection
    public void setCanTick(EasPlayer sender, ElementSelection selection, @Argument("value") boolean canTick) {
        PropertyContainer properties = selection.properties(sender);
        Property<Boolean> property = properties.getOrNull(ArmorStandPropertyTypes.CAN_TICK);
        if (property == null) {
            if (ArmorStandCanTickProperty.isSupported()) {
                sender.sendMessage(Message.error("easyarmorstands.error.can-tick-unsupported-entity"));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.can-tick-unsupported"));
            }
            return;
        }
        if (!property.setValue(canTick)) {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            return;
        }
        properties.commit();
        sender.sendMessage(Message.success("easyarmorstands.success.changed-can-tick",
                property.getType().getValueComponent(canTick)));
    }

    @Command("scale")
    @PropertyPermission("easyarmorstands:entity/scale")
    @CommandDescription("easyarmorstands.command.description.scale")
    @RequireElement
    public void editScale(EasPlayer sender, Session session, Element element) {
        ScaleTool scaleTool = null;
        if (element instanceof EditableElement) {
            PropertyContainer properties = new TrackedPropertyContainer(element, sender);
            ToolProvider tools = ((EditableElement) element).getTools(properties);
            scaleTool = tools.scale(tools.position(), tools.rotation());
        }
        if (scaleTool == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.scale-unsupported"));
            return;
        }
        ScaleButton scaleButton = session.menuEntryProvider().scale()
                .setTool(scaleTool)
                .build();
        session.pushNode(scaleButton.createNode());
    }

    @Command("scale <value>")
    @PropertyPermission("easyarmorstands:entity/scale")
    @CommandDescription("easyarmorstands.command.description.scale")
    @RequireElement
    public void setScale(EasPlayer sender, Element element, @Argument("value") double value) {
        ScaleTool scaleTool = null;
        if (element instanceof EditableElement) {
            PropertyContainer properties = new TrackedPropertyContainer(element, sender);
            ToolProvider tools = ((EditableElement) element).getTools(properties);
            scaleTool = tools.scale(tools.position(), tools.rotation());
        }
        if (scaleTool == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.scale-unsupported"));
            return;
        }
        ScaleToolSession toolSession = scaleTool.start();
        toolSession.setValue(value);
        toolSession.commit(toolSession.getDescription());
        sender.sendMessage(Message.success("easyarmorstands.success.changed-scale", Util.formatScale(value)));
    }

    @Command("reset")
    @Permission(Permissions.EDIT)
    @CommandDescription("easyarmorstands.command.description.reset")
    @RequireSession
    public void reset(EasPlayer sender, Session session) {
        Node node = session.getNode();
        if (!(node instanceof ResettableNode)) {
            sender.sendMessage(Message.error("easyarmorstands.error.reset-unsupported"));
            return;
        }
        ResettableNode resettableNode = (ResettableNode) node;
        resettableNode.reset();
        sender.sendMessage(Message.success("easyarmorstands.success.reset-value"));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Command("set <value>")
    @Permission(Permissions.EDIT)
    @CommandDescription("easyarmorstands.command.description.set")
    public void set(
            EasCommandSender sender,
            ValueNode node,
            @Argument(value = "value", parserName = "node_value") Object value
    ) {
        node.setValue(value);
        sender.sendMessage(Message.success("easyarmorstands.success.changed-value",
                node.getName(),
                node.formatValue(value)));
    }

    @Command("info")
    @Permission(Permissions.INFO)
    @CommandDescription("easyarmorstands.command.description.info")
    @RequireElement
    public void info(EasPlayer sender, Element element) {
        sender.sendMessage(Message.title("easyarmorstands.info.title"));
        sender.sendMessage(Message.hint("easyarmorstands.info.type", element.getType().getDisplayName().colorIfAbsent(NamedTextColor.WHITE)));
        if (element instanceof EntityElement) {
            Entity entity = ((EntityElement<?>) element).getEntity();
            sender.sendMessage(Component.text()
                    .append(Message.hint("easyarmorstands.info.uuid",
                            Component.text(entity.getUniqueId().toString(), NamedTextColor.WHITE)))
                    .appendSpace()
                    .append(Message.chatButton("easyarmorstands.button.text.copy")
                            .hoverEvent(Message.hover("easyarmorstands.click-to-copy"))
                            .clickEvent(ClickEvent.copyToClipboard(entity.getUniqueId().toString()))));
            sender.sendMessage(Message.hint("easyarmorstands.info.id",
                    Component.text(entity.getEntityId(), NamedTextColor.WHITE)));
        }
    }
}
