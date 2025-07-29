package me.m56738.easyarmorstands.display.command;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.context.ManagedChangeContext;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.ElementSelectionNode;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.entity.display.Brightness;
import me.m56738.easyarmorstands.api.platform.entity.display.ItemDisplayTransform;
import me.m56738.easyarmorstands.api.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.api.platform.world.Block;
import me.m56738.easyarmorstands.api.platform.world.BlockData;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.ItemDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.util.Color;
import me.m56738.easyarmorstands.api.util.EulerAngles;
import me.m56738.easyarmorstands.command.SessionCommands;
import me.m56738.easyarmorstands.command.annotation.PropertyPermission;
import me.m56738.easyarmorstands.command.requirement.RequireElement;
import me.m56738.easyarmorstands.command.requirement.RequireElementSelection;
import me.m56738.easyarmorstands.command.util.ElementSelection;
import me.m56738.easyarmorstands.common.group.Group;
import me.m56738.easyarmorstands.common.group.node.GroupRootNode;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.common.platform.command.PlayerCommandSource;
import me.m56738.easyarmorstands.common.util.ArmorStandPartInfo;
import me.m56738.easyarmorstands.common.util.Util;
import me.m56738.easyarmorstands.display.editor.node.DisplayBoxNode;
import me.m56738.easyarmorstands.display.editor.node.DisplayNode;
import me.m56738.easyarmorstands.display.editor.node.DisplayShearNode;
import me.m56738.easyarmorstands.display.element.DisplayElement;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.element.SimpleEntityElement;
import me.m56738.easyarmorstands.element.SimpleEntityElementType;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.ElementCreateAction;
import me.m56738.easyarmorstands.history.action.ElementDestroyAction;
import me.m56738.easyarmorstands.paper.api.platform.adapter.PaperLocationAdapter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.incendo.cloud.annotation.specifier.Greedy;
import org.incendo.cloud.annotation.specifier.Range;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.minecraft.extras.annotation.specifier.Decoder;
import org.joml.Math;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Command("eas")
public class DisplayCommands {
    @Command("block <value>")
    @PropertyPermission("easyarmorstands:block_display/block")
    @CommandDescription("easyarmorstands.command.description.block")
    @RequireElementSelection
    public void setBlock(PlayerCommandSource source, ElementSelection selection, @Argument("value") BlockData value) {
        Player sender = source.source();
        try (ManagedChangeContext context = EasyArmorStandsPlugin.getInstance().changeContext().create(sender)) {
            PropertyContainer properties = context.getProperties(selection.elements());
            Property<BlockData> property = properties.getOrNull(BlockDisplayPropertyTypes.BLOCK);
            if (property == null) {
                sender.sendMessage(Message.error("easyarmorstands.error.block-unsupported"));
                return;
            }
            if (property.setValue(value)) {
                sender.sendMessage(Message.success("easyarmorstands.success.changed-block",
                        property.getType().getValueComponent(value)));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            }
        }
    }

    @Command("brightness block <value>")
    @PropertyPermission("easyarmorstands:display/brightness")
    @CommandDescription("easyarmorstands.command.description.brightness.block")
    @RequireElementSelection
    public void setBlockBrightness(PlayerCommandSource source, ElementSelection selection, @Argument("value") @Range(min = "0", max = "15") int blockLight) {
        Player sender = source.source();
        try (ManagedChangeContext context = EasyArmorStandsPlugin.getInstance().changeContext().create(sender)) {
            PropertyContainer properties = context.getProperties(selection.elements());
            Location location = properties.get(EntityPropertyTypes.LOCATION).getValue();
            Property<Optional<Brightness>> property = properties.getOrNull(DisplayPropertyTypes.BRIGHTNESS);
            if (property == null) {
                sender.sendMessage(Message.error("easyarmorstands.error.brightness-unsupported"));
                return;
            }
            int skyLight = property.getValue()
                    .map(Brightness::sky)
                    .orElseGet(() -> (int) PaperLocationAdapter.toNative(location).getBlock().getLightFromSky());
            Optional<Brightness> brightness = Optional.of(Brightness.of(blockLight, skyLight));
            if (property.setValue(brightness)) {
                sender.sendMessage(Message.success("easyarmorstands.success.changed-brightness",
                        property.getType().getValueComponent(brightness)));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            }
        }
    }

    @Command("brightness sky <value>")
    @PropertyPermission("easyarmorstands:display/brightness")
    @CommandDescription("easyarmorstands.command.description.brightness.sky")
    @RequireElementSelection
    public void setSkyBrightness(PlayerCommandSource source, ElementSelection selection, @Argument("value") @Range(min = "0", max = "15") int skyLight) {
        Player sender = source.source();
        try (ManagedChangeContext context = EasyArmorStandsPlugin.getInstance().changeContext().create(sender)) {
            PropertyContainer properties = context.getProperties(selection.elements());
            Location location = properties.get(EntityPropertyTypes.LOCATION).getValue();
            Property<Optional<Brightness>> property = properties.getOrNull(DisplayPropertyTypes.BRIGHTNESS);
            if (property == null) {
                sender.sendMessage(Message.error("easyarmorstands.error.brightness-unsupported"));
                return;
            }
            int blockLight = property.getValue()
                    .map(Brightness::sky)
                    .orElseGet(() -> (int) PaperLocationAdapter.toNative(location).getBlock().getLightFromBlocks());
            Optional<Brightness> brightness = Optional.of(Brightness.of(blockLight, skyLight));
            if (property.setValue(brightness)) {
                sender.sendMessage(Message.success("easyarmorstands.success.changed-brightness",
                        property.getType().getValueComponent(brightness)));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            }
        }
    }

    @Command("brightness here")
    @PropertyPermission("easyarmorstands:display/brightness")
    @CommandDescription("easyarmorstands.command.description.brightness.here")
    @RequireElementSelection
    public void setLocalBrightness(PlayerCommandSource source, ElementSelection selection) {
        Player sender = source.source();
        try (ManagedChangeContext context = EasyArmorStandsPlugin.getInstance().changeContext().create(sender)) {
            PropertyContainer properties = context.getProperties(selection.elements());
            Property<Optional<Brightness>> property = properties.getOrNull(DisplayPropertyTypes.BRIGHTNESS);
            if (property == null) {
                sender.sendMessage(Message.error("easyarmorstands.error.brightness-unsupported"));
                return;
            }
            Block block = sender.getLocation().getBlock();
            Optional<Brightness> brightness = Optional.of(block.getBrightness());
            if (property.setValue(brightness)) {
                sender.sendMessage(Message.success("easyarmorstands.success.changed-brightness",
                        property.getType().getValueComponent(brightness)));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            }
        }
    }

    @Command("brightness reset")
    @PropertyPermission("easyarmorstands:display/brightness")
    @CommandDescription("easyarmorstands.command.description.brightness.reset")
    @RequireElementSelection
    public void setDefaultBrightness(PlayerCommandSource source, ElementSelection selection) {
        Player sender = source.source();
        try (ManagedChangeContext context = EasyArmorStandsPlugin.getInstance().changeContext().create(sender)) {
            PropertyContainer properties = context.getProperties(selection.elements());
            Property<Optional<Brightness>> property = properties.getOrNull(DisplayPropertyTypes.BRIGHTNESS);
            if (property == null) {
                sender.sendMessage(Message.error("easyarmorstands.error.brightness-unsupported"));
                return;
            }
            if (property.setValue(Optional.empty())) {
                sender.sendMessage(Message.success("easyarmorstands.success.changed-brightness",
                        property.getType().getValueComponent(Optional.empty())));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            }
        }
    }

    @Command("box width <width>")
    @PropertyPermission("easyarmorstands:display/box/width")
    @CommandDescription("easyarmorstands.command.description.box.width")
    @RequireElementSelection
    public void setBoxWidth(PlayerCommandSource source, ElementSelection selection, @Argument("width") float value) {
        Player sender = source.source();
        try (ManagedChangeContext context = EasyArmorStandsPlugin.getInstance().changeContext().create(sender)) {
            PropertyContainer properties = context.getProperties(selection.elements());
            Property<Float> widthProperty = properties.getOrNull(DisplayPropertyTypes.BOX_WIDTH);
            if (widthProperty == null) {
                sender.sendMessage(Message.error("easyarmorstands.error.box-unsupported"));
                return;
            }
            if (widthProperty.setValue(value)) {
                Property<Float> heightProperty = properties.getOrNull(DisplayPropertyTypes.BOX_HEIGHT);
                if (heightProperty != null && heightProperty.getValue() == 0f) {
                    heightProperty.setValue(value);
                }
                sender.sendMessage(Message.success("easyarmorstands.success.changed-box-width",
                        widthProperty.getType().getValueComponent(value)));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            }
        }
    }

    @Command("box height <height>")
    @PropertyPermission("easyarmorstands:display/box/height")
    @CommandDescription("easyarmorstands.command.description.box.height")
    @RequireElementSelection
    public void setBoxHeight(PlayerCommandSource source, ElementSelection selection, @Argument("height") float value) {
        Player sender = source.source();
        try (ManagedChangeContext context = EasyArmorStandsPlugin.getInstance().changeContext().create(sender)) {
            PropertyContainer properties = context.getProperties(selection.elements());
            Property<Float> heightProperty = properties.getOrNull(DisplayPropertyTypes.BOX_HEIGHT);
            if (heightProperty == null) {
                sender.sendMessage(Message.error("easyarmorstands.error.box-unsupported"));
                return;
            }
            if (heightProperty.setValue(value)) {
                Property<Float> widthProperty = properties.getOrNull(DisplayPropertyTypes.BOX_WIDTH);
                if (widthProperty != null && widthProperty.getValue() == 0f) {
                    widthProperty.setValue(value);
                }
                sender.sendMessage(Message.success("easyarmorstands.success.changed-box-height",
                        heightProperty.getType().getValueComponent(value)));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            }
        }
    }

    @Command("box remove")
    @PropertyPermission("easyarmorstands:display/box/height")
    @CommandDescription("easyarmorstands.command.description.box.remove")
    @RequireElementSelection
    public void removeBox(PlayerCommandSource source, ElementSelection selection) {
        Player sender = source.source();
        try (ManagedChangeContext context = EasyArmorStandsPlugin.getInstance().changeContext().create(sender)) {
            PropertyContainer properties = context.getProperties(selection.elements());

            int success = 0;
            Property<Float> widthProperty = properties.getOrNull(DisplayPropertyTypes.BOX_WIDTH);
            Property<Float> heightProperty = properties.getOrNull(DisplayPropertyTypes.BOX_HEIGHT);
            if (widthProperty == null && heightProperty == null) {
                sender.sendMessage(Message.error("easyarmorstands.error.box-unsupported"));
                return;
            }

            if (widthProperty != null && widthProperty.setValue(0f)) {
                success++;
            }
            if (heightProperty != null && heightProperty.setValue(0f)) {
                success++;
            }

            if (success > 0) {
                sender.sendMessage(Message.success("easyarmorstands.success.removed-box"));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            }
        }
    }

    @Command("box")
    @PropertyPermission("easyarmorstands:display/translation")
    @CommandDescription("easyarmorstands.command.description.box")
    @RequireElement
    public void moveBox(PlayerCommandSource source, Session session, Element element) {
        Player sender = source.source();
        if (!(element instanceof DisplayElement displayElement)) {
            sender.sendMessage(Message.error("easyarmorstands.error.box-unsupported"));
            return;
        }
        session.pushNode(new DisplayBoxNode(session, displayElement));
    }

    @Command("text")
    @PropertyPermission("easyarmorstands:text_display/text")
    @CommandDescription("easyarmorstands.command.description.text")
    @RequireElement
    public void showText(PlayerCommandSource source, Element element) {
        Player sender = source.source();
        Property<Component> property = element.getProperties().getOrNull(TextDisplayPropertyTypes.TEXT);
        if (property == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.text-unsupported"));
            return;
        }
        Component text = property.getValue();
        SessionCommands.showText(sender, TextDisplayPropertyTypes.TEXT.getName(), text, "/eas text set");
    }

    @Command("text set <value>")
    @PropertyPermission("easyarmorstands:text_display/text")
    @CommandDescription("easyarmorstands.command.description.text.set")
    @RequireElementSelection
    public void setText(PlayerCommandSource source, ElementSelection selection, @Argument("value") @Decoder.MiniMessage @Greedy Component value) {
        Player sender = source.source();
        try (ManagedChangeContext context = EasyArmorStandsPlugin.getInstance().changeContext().create(sender)) {
            PropertyContainer properties = context.getProperties(selection.elements());
            Property<Component> property = properties.getOrNull(TextDisplayPropertyTypes.TEXT);
            if (property == null) {
                sender.sendMessage(Message.error("easyarmorstands.error.text-unsupported"));
                return;
            }
            if (property.setValue(value)) {
                sender.sendMessage(Message.success("easyarmorstands.success.changed-text",
                        property.getType().getValueComponent(value)));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            }
        }
    }

    @Command("text width <value>")
    @PropertyPermission("easyarmorstands:text_display/line_width")
    @CommandDescription("easyarmorstands.command.description.text.width")
    @RequireElementSelection
    public void setTextWidth(PlayerCommandSource source, ElementSelection selection, @Argument("value") int value) {
        Player sender = source.source();
        try (ManagedChangeContext context = EasyArmorStandsPlugin.getInstance().changeContext().create(sender)) {
            PropertyContainer properties = context.getProperties(selection.elements());
            Property<Integer> property = properties.getOrNull(TextDisplayPropertyTypes.LINE_WIDTH);
            if (property == null) {
                sender.sendMessage(Message.error("easyarmorstands.error.text-line-width-unsupported"));
                return;
            }
            if (property.setValue(value)) {
                sender.sendMessage(Message.success("easyarmorstands.success.changed-text-line-width",
                        property.getType().getValueComponent(value)));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            }
        }
    }

    @Command("text background color <value>")
    @PropertyPermission("easyarmorstands:text_display/background")
    @CommandDescription("easyarmorstands.command.description.text.background.color")
    @RequireElementSelection
    public void setTextBackground(PlayerCommandSource source, ElementSelection selection, @Argument("value") TextColor color) {
        Player sender = source.source();
        try (ManagedChangeContext context = EasyArmorStandsPlugin.getInstance().changeContext().create(sender)) {
            PropertyContainer properties = context.getProperties(selection.elements());
            Property<Optional<Color>> property = properties.getOrNull(TextDisplayPropertyTypes.BACKGROUND);
            if (property == null) {
                sender.sendMessage(Message.error("easyarmorstands.error.text-background-unsupported"));
                return;
            }
            Optional<Color> value = Optional.of(Color.of(color, 0xFF));
            if (property.setValue(value)) {
                sender.sendMessage(Message.success("easyarmorstands.success.changed-text-background",
                        property.getType().getValueComponent(value)));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            }
        }
    }

    @Command("text background reset")
    @PropertyPermission("easyarmorstands:text_display/background")
    @CommandDescription("easyarmorstands.command.description.text.background.reset")
    @RequireElementSelection
    public void resetTextBackground(PlayerCommandSource source, ElementSelection selection) {
        Player sender = source.source();
        try (ManagedChangeContext context = EasyArmorStandsPlugin.getInstance().changeContext().create(sender)) {
            PropertyContainer properties = context.getProperties(selection.elements());
            Property<Optional<Color>> property = properties.getOrNull(TextDisplayPropertyTypes.BACKGROUND);
            if (property == null) {
                sender.sendMessage(Message.error("easyarmorstands.error.text-background-unsupported"));
                return;
            }
            if (property.setValue(Optional.empty())) {
                sender.sendMessage(Message.success("easyarmorstands.success.changed-text-background",
                        property.getType().getValueComponent(Optional.empty())));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            }
        }
    }

    @Command("text background none")
    @PropertyPermission("easyarmorstands:text_display/background")
    @CommandDescription("easyarmorstands.command.description.text.background.none")
    @RequireElementSelection
    public void hideTextBackground(PlayerCommandSource source, ElementSelection selection) {
        Player sender = source.source();
        try (ManagedChangeContext context = EasyArmorStandsPlugin.getInstance().changeContext().create(sender)) {
            PropertyContainer properties = context.getProperties(selection.elements());
            Property<Optional<Color>> property = properties.getOrNull(TextDisplayPropertyTypes.BACKGROUND);
            if (property == null) {
                sender.sendMessage(Message.error("easyarmorstands.error.text-background-unsupported"));
                return;
            }
            Optional<Color> value = Optional.of(Color.TRANSPARENT);
            if (property.setValue(value)) {
                sender.sendMessage(Message.success("easyarmorstands.success.changed-text-background",
                        property.getType().getValueComponent(value)));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            }
        }
    }

    @Command("text background alpha <value>")
    @PropertyPermission("easyarmorstands:text_display/background")
    @CommandDescription("easyarmorstands.command.description.text.background.alpha")
    @RequireElementSelection
    public void hideTextBackground(PlayerCommandSource source, ElementSelection selection, @Argument("value") @Range(min = "0", max = "255") int alpha) {
        Player sender = source.source();
        try (ManagedChangeContext context = EasyArmorStandsPlugin.getInstance().changeContext().create(sender)) {
            PropertyContainer properties = context.getProperties(selection.elements());
            Property<Optional<Color>> property = properties.getOrNull(TextDisplayPropertyTypes.BACKGROUND);
            if (property == null) {
                sender.sendMessage(Message.error("easyarmorstands.error.text-background-unsupported"));
                return;
            }
            Optional<Color> oldValue = property.getValue();
            if (oldValue.isEmpty()) {
                sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
                return;
            }

            Optional<Color> value = oldValue.map(v -> v.withAlpha(alpha));
            if (property.setValue(value)) {
                sender.sendMessage(Message.success("easyarmorstands.success.changed-text-background-alpha",
                        Component.text(alpha)));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            }
        }
    }

    @Command("glow color <value>")
    @PropertyPermission("easyarmorstands:display/glowing/color")
    @CommandDescription("easyarmorstands.command.description.glow.color")
    @RequireElementSelection
    public void setGlowColor(PlayerCommandSource source, ElementSelection selection, @Argument("value") TextColor color) {
        Player sender = source.source();
        try (ManagedChangeContext context = EasyArmorStandsPlugin.getInstance().changeContext().create(sender)) {
            PropertyContainer properties = context.getProperties(selection.elements());
            Property<Optional<Color>> property = properties.getOrNull(DisplayPropertyTypes.GLOW_COLOR);
            if (property == null) {
                sender.sendMessage(Message.error("easyarmorstands.error.glow-color-unsupported"));
                return;
            }
            Optional<Color> value = Optional.of(Color.of(color, 0xFF));
            if (property.setValue(value)) {
                sender.sendMessage(Message.success("easyarmorstands.success.changed-glow-color",
                        property.getType().getValueComponent(value)));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            }
        }
    }

    @Command("glow reset")
    @PropertyPermission("easyarmorstands:display/glowing/color")
    @CommandDescription("easyarmorstands.command.description.glow.color.reset")
    @RequireElementSelection
    public void resetGlowColor(PlayerCommandSource source, ElementSelection selection) {
        Player sender = source.source();
        try (ManagedChangeContext context = EasyArmorStandsPlugin.getInstance().changeContext().create(sender)) {
            PropertyContainer properties = context.getProperties(selection.elements());
            Property<Optional<Color>> property = properties.getOrNull(DisplayPropertyTypes.GLOW_COLOR);
            if (property == null) {
                sender.sendMessage(Message.error("easyarmorstands.error.glow-color-unsupported"));
                return;
            }
            Optional<Color> value = Optional.empty();
            if (property.setValue(value)) {
                sender.sendMessage(Message.success("easyarmorstands.success.changed-glow-color",
                        property.getType().getValueComponent(value)));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            }
        }
    }

    @Command("shear")
    @PropertyPermission("easyarmorstands:display/right_rotation")
    @CommandDescription("easyarmorstands.command.description.shear")
    @RequireElement
    public void editRightRotation(PlayerCommandSource source, Session session, Element element) {
        Player sender = source.source();
        if (!(element instanceof DisplayElement)) {
            sender.sendMessage(Message.error("easyarmorstands.error.shearing-unsupported"));
            return;
        }
        DisplayNode node = new DisplayShearNode(session, (DisplayElement) element);
        session.pushNode(node);
    }

    @Command("convert")
    @Permission(Permissions.CONVERT)
    @CommandDescription("easyarmorstands.command.description.convert")
    @RequireElementSelection
    public void convert(PlayerCommandSource source, Session session, ElementSelection selection) {
        Player sender = source.source();
        List<SimpleEntityElement> createdElements = new ArrayList<>();
        List<Action> allActions = new ArrayList<>();
        boolean foundArmorStand = false;
        int count = 0;
        for (Element element : selection.elements()) {
            if (!(element instanceof ArmorStandElement armorStandElement)) {
                continue;
            }
            foundArmorStand = true;
            ArmorStandSize size = armorStandElement.getProperties().get(ArmorStandPropertyTypes.SIZE).getValue();
            Item helmet = armorStandElement.getProperties().get(EntityPropertyTypes.EQUIPMENT.get(EquipmentSlot.HEAD)).getValue();

            Matrix4d headMatrix = new Matrix4d();
            Matrix4d rightMatrix = new Matrix4d();
            Matrix4d leftMatrix = new Matrix4d();

            if (size == ArmorStandSize.SMALL) {
                headMatrix.scale(0.7);
                rightMatrix.scale(0.5);
                leftMatrix.scale(0.5);
            }

            if (isSkull(helmet)) {
                headMatrix.scale(1.1875);
                headMatrix.translate(0, 0.5, 0);
                headMatrix.rotateY(Math.PI);
            } else {
                headMatrix.translate(0, 0.25, 0);
                headMatrix.scale(0.625);
            }

            rightMatrix.translate(-0.0625, -0.625, 0.125);
            rightMatrix.rotateX(Math.PI / 2);

            leftMatrix.translate(0.0625, -0.625, 0.125);
            leftMatrix.rotateX(Math.PI / 2);

            List<Action> actions = new ArrayList<>();
            convert(sender, armorStandElement, size, EquipmentSlot.HEAD, ArmorStandPart.HEAD, ItemDisplayTransform.HEAD, headMatrix, actions, createdElements);
            convert(sender, armorStandElement, size, EquipmentSlot.HAND, ArmorStandPart.RIGHT_ARM, ItemDisplayTransform.THIRDPERSON_RIGHTHAND, rightMatrix, actions, createdElements);
            convert(sender, armorStandElement, size, EquipmentSlot.OFF_HAND, ArmorStandPart.LEFT_ARM, ItemDisplayTransform.THIRDPERSON_LEFTHAND, leftMatrix, actions, createdElements);
            if (!actions.isEmpty()) {
                actions.add(new ElementDestroyAction(element));
                armorStandElement.destroy();
                count++;
            }
            allActions.addAll(actions);
        }

        EasyArmorStandsPlugin.getInstance().getHistory(sender)
                .push(allActions, Message.component("easyarmorstands.history.converted-armor-stand"));

        if (!foundArmorStand) {
            // None of the elements are armor stands
            sender.sendMessage(Message.error("easyarmorstands.error.convert-unsupported"));
        } else if (count == 0) {
            // Nothing happened
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-convert"));
        } else if (count == 1) {
            sender.sendMessage(Message.success("easyarmorstands.success.armor-stand-converted"));
        } else {
            sender.sendMessage(Message.success("easyarmorstands.success.armor-stand-converted.multiple", Component.text(count)));
        }

        GroupRootNode groupRootNode = session.findNode(GroupRootNode.class);
        if (groupRootNode != null) {
            // Add created entities to the selected group
            Group group = groupRootNode.getGroup();
            session.returnToNode(groupRootNode);
            for (SimpleEntityElement element : createdElements) {
                if (EasyArmorStandsPlugin.getInstance().canSelectElement(sender, element)) {
                    group.addMember(element);
                }
            }
        } else {
            // Select the created elements
            ElementSelectionNode selectionNode = session.findNode(ElementSelectionNode.class);
            if (selectionNode != null) {
                selectionNode.selectElements(createdElements);
            }
        }
    }

    private boolean isSkull(Item item) {
        return item.isSkull();
    }

    private void convert(Player sender, ArmorStandElement element, ArmorStandSize size, EquipmentSlot slot, ArmorStandPart part, ItemDisplayTransform itemTransform, Matrix4dc matrix, List<Action> actions, List<SimpleEntityElement> elements) {
        Item item = element.getProperties().get(EntityPropertyTypes.EQUIPMENT.get(slot)).getValue();
        if (item.isEmpty()) {
            return;
        }

        ArmorStandPartInfo info = ArmorStandPartInfo.of(part);
        Location location = element.getProperties().get(EntityPropertyTypes.LOCATION).getValue();
        Vector3d offset = info.getOffset(size, 1).rotateY(Util.getRoundedYawAngle(location.yaw()), new Vector3d());
        location = location.withOffset(offset);

        EulerAngles angle = element.getProperties().get(ArmorStandPropertyTypes.POSE.get(part)).getValue();
        Matrix4d transform = new Matrix4d()
                .rotateY(Util.getRoundedYawAngle(location.yaw()))
                .rotateZYX(-angle.z(), -angle.y(), angle.x())
                .mul(matrix);

        location = location.withRotation(0, 0);

        PropertyMap properties = new PropertyMap();
        properties.put(EntityPropertyTypes.LOCATION, location);
        properties.put(ItemDisplayPropertyTypes.ITEM, item);
        properties.put(ItemDisplayPropertyTypes.TRANSFORM, itemTransform);
        properties.put(DisplayPropertyTypes.TRANSLATION, transform.getTranslation(new Vector3d()).get(new Vector3f()));
        properties.put(DisplayPropertyTypes.LEFT_ROTATION, transform.getUnnormalizedRotation(new Quaternionf()));
        properties.put(DisplayPropertyTypes.SCALE, transform.getScale(new Vector3d()).get(new Vector3f()));

        SimpleEntityElementType type = EasyArmorStandsPlugin.getInstance().getItemDisplayType();

        if (!EasyArmorStandsPlugin.getInstance().canCreateElement(sender, type, properties)) {
            return;
        }

        SimpleEntityElement newElement = type.createElement(properties);
        if (newElement == null) {
            return;
        }

        actions.add(new ElementCreateAction(element));
        elements.add(element);
    }

    @Command("viewrange <value>")
    @PropertyPermission("easyarmorstands:display/view_range")
    @CommandDescription("easyarmorstands.command.description.view-range")
    @RequireElementSelection
    public void setViewRange(PlayerCommandSource source, ElementSelection selection, @Argument("value") float value) {
        Player sender = source.source();
        try (ManagedChangeContext context = EasyArmorStandsPlugin.getInstance().changeContext().create(sender)) {
            PropertyContainer properties = context.getProperties(selection.elements());
            Property<Float> property = properties.getOrNull(DisplayPropertyTypes.VIEW_RANGE);
            if (property == null) {
                sender.sendMessage(Message.error("easyarmorstands.error.view-range-unsupported"));
                return;
            }
            if (property.setValue(value)) {
                sender.sendMessage(Message.success("easyarmorstands.success.changed-view-range",
                        property.getType().getValueComponent(value)));
            } else {
                sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            }
        }
    }
}
