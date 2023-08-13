package me.m56738.easyarmorstands.addon.display;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Greedy;
import cloud.commandframework.annotations.specifier.Range;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.bone.v1_19_4.DisplayBone;
import me.m56738.easyarmorstands.bone.v1_19_4.DisplayBoxBone;
import me.m56738.easyarmorstands.command.SessionCommands;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.element.Element;
import me.m56738.easyarmorstands.element.EntityElement;
import me.m56738.easyarmorstands.element.EntityElementType;
import me.m56738.easyarmorstands.event.PlayerCreateElementEvent;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.ElementCreateAction;
import me.m56738.easyarmorstands.history.action.ElementDestroyAction;
import me.m56738.easyarmorstands.node.v1_19_4.DisplayMenuNode;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertyRegistry;
import me.m56738.easyarmorstands.property.UnknownPropertyException;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayBrightnessProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayHeightProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayLeftRotationProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayRightRotationProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayScaleProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayTranslationProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayWidthProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.block.BlockDisplayBlockProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.item.ItemDisplayItemProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.item.ItemDisplayTransformProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplayBackgroundProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplayLineWidthProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplayTextProperty;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.ArmorStandSize;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.EulerAngle;
import org.joml.Math;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.ArrayList;
import java.util.List;

import static me.m56738.easyarmorstands.command.SessionCommands.getElementOrError;
import static me.m56738.easyarmorstands.command.SessionCommands.getSessionOrError;

@CommandMethod("eas")
public class DisplayCommands {
    private final DisplayAddon addon;

    public DisplayCommands(DisplayAddon addon) {
        this.addon = addon;
    }

    @CommandMethod("block <value>")
    @CommandPermission("easyarmorstands.property.display.block")
    @CommandDescription("Set the block of the selected block display")
    public void setBlock(EasPlayer sender, @Argument("value") BlockData value) {
        Element element = getElementOrError(sender);
        if (element == null) {
            return;
        }
        PropertyContainer properties = PropertyContainer.tracked(element, sender.get());
        Property<BlockData> property = properties.getOrNull(BlockDisplayBlockProperty.TYPE);
        if (property == null) {
            sender.sendMessage(Component.text("Cannot change the displayed block of this entity."));
            return;
        }
        if (property.setValue(value)) {
            properties.commit();
            sender.sendMessage(Component.text("Changed block to ", NamedTextColor.GREEN)
                    .append(property.getType().getValueComponent(value)));
        } else {
            sender.sendMessage(Component.text("Unable to change block", NamedTextColor.RED));
        }
    }

    @CommandMethod("brightness block <value>")
    @CommandPermission("easyarmorstands.property.display.brightness")
    @CommandDescription("Set the block light level of the selected display")
    public void setBlockBrightness(EasPlayer sender, @Argument("value") @Range(min = "0", max = "15") int value) {
        Element element = getElementOrError(sender);
        if (element == null) {
            return;
        }
        PropertyContainer properties = PropertyContainer.tracked(element, sender.get());
        Location location = properties.get(EntityLocationProperty.TYPE).getValue();
        Property<Display.Brightness> property = properties.getOrNull(DisplayBrightnessProperty.TYPE);
        if (property == null) {
            sender.sendMessage(Component.text("Cannot change the brightness of this entity.", NamedTextColor.RED));
            return;
        }
        Display.Brightness brightness = property.getValue();
        if (brightness != null) {
            brightness = new Display.Brightness(value, brightness.getSkyLight());
        } else {
            brightness = new Display.Brightness(value, location.getBlock().getLightFromSky());
        }
        if (property.setValue(brightness)) {
            properties.commit();
            sender.sendMessage(Component.text("Changed block brightness to ", NamedTextColor.GREEN)
                    .append(Component.text(value)));
        } else {
            sender.sendMessage(Component.text("Unable to change brightness", NamedTextColor.RED));
        }
    }

    @CommandMethod("brightness sky <value>")
    @CommandPermission("easyarmorstands.property.display.brightness")
    @CommandDescription("Set the sky light level of the selected display")
    public void setSkyBrightness(EasPlayer sender, @Argument("value") @Range(min = "0", max = "15") int value) {
        Element element = getElementOrError(sender);
        if (element == null) {
            return;
        }
        PropertyContainer properties = PropertyContainer.tracked(element, sender.get());
        Location location = properties.get(EntityLocationProperty.TYPE).getValue();
        Property<Display.Brightness> property = properties.getOrNull(DisplayBrightnessProperty.TYPE);
        if (property == null) {
            sender.sendMessage(Component.text("Cannot change the brightness of this entity.", NamedTextColor.RED));
            return;
        }
        Display.Brightness brightness = property.getValue();
        if (brightness != null) {
            brightness = new Display.Brightness(brightness.getBlockLight(), value);
        } else {
            brightness = new Display.Brightness(location.getBlock().getLightFromBlocks(), value);
        }
        if (property.setValue(brightness)) {
            properties.commit();
            sender.sendMessage(Component.text("Changed sky brightness to ", NamedTextColor.GREEN)
                    .append(Component.text(value)));
        } else {
            sender.sendMessage(Component.text("Unable to change brightness", NamedTextColor.RED));
        }
    }

    @CommandMethod("brightness here")
    @CommandPermission("easyarmorstands.property.display.brightness")
    @CommandDescription("Apply the light level at your location to the selected display")
    public void setLocalBrightness(EasPlayer sender) {
        Element element = getElementOrError(sender);
        if (element == null) {
            return;
        }
        PropertyContainer properties = PropertyContainer.tracked(element, sender.get());
        Property<Display.Brightness> property = properties.getOrNull(DisplayBrightnessProperty.TYPE);
        if (property == null) {
            sender.sendMessage(Component.text("Cannot change the brightness of this entity.", NamedTextColor.RED));
            return;
        }
        Block block = sender.get().getLocation().getBlock();
        Display.Brightness brightness = new Display.Brightness(block.getLightFromBlocks(), block.getLightFromSky());
        if (property.setValue(brightness)) {
            properties.commit();
            sender.sendMessage(Component.text("Changed entity brightness to the light level at your location", NamedTextColor.GREEN));
            sender.sendMessage(Component.text("Block light: ", NamedTextColor.GRAY).append(Component.text(brightness.getBlockLight())));
            sender.sendMessage(Component.text("Sky light: ", NamedTextColor.GRAY).append(Component.text(brightness.getSkyLight())));
        } else {
            sender.sendMessage(Component.text("Unable to change brightness", NamedTextColor.RED));
        }
    }

    @CommandMethod("brightness reset")
    @CommandPermission("easyarmorstands.property.display.brightness")
    @CommandDescription("Remove the custom light level from the selected display")
    public void setDefaultBrightness(EasPlayer sender) {
        Element element = getElementOrError(sender);
        if (element == null) {
            return;
        }
        PropertyContainer properties = PropertyContainer.tracked(element, sender.get());
        Property<Display.Brightness> property = properties.getOrNull(DisplayBrightnessProperty.TYPE);
        if (property == null) {
            sender.sendMessage(Component.text("Cannot change the brightness of this entity.", NamedTextColor.RED));
            return;
        }
        if (property.setValue(null)) {
            properties.commit();
            sender.sendMessage(Component.text("Removed custom brightness settings", NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Unable to change brightness", NamedTextColor.RED));
        }
    }

    @CommandMethod("box width <width>")
    @CommandPermission("easyarmorstands.property.display.size")
    @CommandDescription("Set the bounding box width of the selected display")
    public void setBoxWidth(EasPlayer sender, @Argument("width") float value) {
        Element element = getElementOrError(sender);
        if (element == null) {
            return;
        }
        PropertyContainer properties = PropertyContainer.tracked(element, sender.get());
        Property<Float> widthProperty = properties.getOrNull(DisplayWidthProperty.TYPE);
        if (widthProperty == null) {
            sender.sendMessage(Component.text("Cannot change the bounding box size of this entity.", NamedTextColor.RED));
            return;
        }
        if (widthProperty.setValue(value)) {
            Property<Float> heightProperty = properties.getOrNull(DisplayHeightProperty.TYPE);
            if (heightProperty != null && heightProperty.getValue() == 0f) {
                heightProperty.setValue(value);
            }
            properties.commit();
            sender.sendMessage(Component.text("Changed bounding box width to ", NamedTextColor.GREEN)
                    .append(Component.text(value)));
        } else {
            sender.sendMessage(Component.text("Unable to change bounding box size", NamedTextColor.RED));
        }
    }

    @CommandMethod("box height <height>")
    @CommandPermission("easyarmorstands.property.display.size")
    @CommandDescription("Set the bounding box height of the selected display")
    public void setBoxHeight(EasPlayer sender, @Argument("height") float value) {
        Element element = getElementOrError(sender);
        if (element == null) {
            return;
        }
        PropertyContainer properties = PropertyContainer.tracked(element, sender.get());
        Property<Float> heightProperty = properties.getOrNull(DisplayHeightProperty.TYPE);
        if (heightProperty == null) {
            sender.sendMessage(Component.text("Cannot change the bounding box size of this entity.", NamedTextColor.RED));
            return;
        }
        if (heightProperty.setValue(value)) {
            Property<Float> widthProperty = properties.getOrNull(DisplayWidthProperty.TYPE);
            if (widthProperty != null && widthProperty.getValue() == 0f) {
                widthProperty.setValue(value);
            }
            properties.commit();
            sender.sendMessage(Component.text("Changed bounding box height to ", NamedTextColor.GREEN)
                    .append(Component.text(value)));
        } else {
            sender.sendMessage(Component.text("Unable to change bounding box size", NamedTextColor.RED));
        }
    }

    @CommandMethod("box remove")
    @CommandPermission("easyarmorstands.property.display.size")
    @CommandDescription("Remove the bounding box from the selected display")
    public void removeBox(EasPlayer sender) {
        Element element = getElementOrError(sender);
        if (element == null) {
            return;
        }
        PropertyContainer properties = PropertyContainer.tracked(element, sender.get());

        int success = 0;
        Property<Float> widthProperty = properties.getOrNull(DisplayWidthProperty.TYPE);
        Property<Float> heightProperty = properties.getOrNull(DisplayHeightProperty.TYPE);
        if (widthProperty == null && heightProperty == null) {
            sender.sendMessage(Component.text("Cannot remove the bounding box of this entity.", NamedTextColor.RED));
        }

        if (widthProperty != null && widthProperty.setValue(0f)) {
            success++;
        }
        if (heightProperty != null && heightProperty.setValue(0f)) {
            success++;
        }

        if (success > 0) {
            properties.commit();
            sender.sendMessage(Component.text("Removed the bounding box", NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Unable to change bounding box size", NamedTextColor.RED));
        }
    }

    @CommandMethod("box move")
    @CommandPermission("easyarmorstands.property.display.translation")
    @CommandDescription("Select a tool to move the bounding box of the selected display")
    public void moveBox(EasPlayer sender) {
        Session session = getSessionOrError(sender);
        Element element = getElementOrError(sender, session);
        if (element == null) {
            return;
        }
        PropertyContainer properties = PropertyContainer.tracked(element, sender.get());
        DisplayBoxBone bone;
        DisplayMenuNode node;
        try {
            bone = new DisplayBoxBone(properties);
            node = new DisplayMenuNode(session, Component.text("Bounding box", NamedTextColor.GOLD), properties);
        } catch (UnknownPropertyException e) {
            sender.sendMessage(Component.text("Cannot move the bounding box of this entity.", NamedTextColor.RED));
            return;
        }
        node.setShowBoundingBoxIfInactive(true); // bounding box should remain visible while a tool node is active
        node.addPositionButtons(session, bone, 3);
        node.addCarryButton(session, bone);
        session.pushNode(node);
    }

    @CommandMethod("text")
    @CommandPermission("easyarmorstands.property.display.text")
    @CommandDescription("Show the text of the selected text display")
    public void showText(EasPlayer sender) {
        Element element = getElementOrError(sender);
        if (element == null) {
            return;
        }
        Property<Component> property = element.getProperties().getOrNull(TextDisplayTextProperty.TYPE);
        if (property == null) {
            sender.sendMessage(Component.text("Cannot change the text of this entity.", NamedTextColor.RED));
            return;
        }
        Component text = property.getValue();
        SessionCommands.showText(sender, Component.text("Text", NamedTextColor.YELLOW), text, "/eas text set");
    }

    @CommandMethod("text set <value>")
    @CommandPermission("easyarmorstands.property.display.text")
    @CommandDescription("Set the text of the selected text display")
    public void setText(EasPlayer sender, @Argument("value") @Greedy String input) {
        Element element = getElementOrError(sender);
        if (element == null) {
            return;
        }
        PropertyContainer properties = PropertyContainer.tracked(element, sender.get());
        Property<Component> property = properties.getOrNull(TextDisplayTextProperty.TYPE);
        if (property == null) {
            sender.sendMessage(Component.text("Cannot change the text of this entity.", NamedTextColor.RED));
            return;
        }
        Component value = MiniMessage.miniMessage().deserialize(input);
        if (property.setValue(value)) {
            properties.commit();
            sender.sendMessage(Component.text("Changed the text to ", NamedTextColor.GREEN)
                    .append(value));
        } else {
            sender.sendMessage(Component.text("Unable to change the text", NamedTextColor.RED));
        }
    }

    @CommandMethod("text width <value>")
    @CommandPermission("easyarmorstands.property.display.text.linewidth")
    @CommandDescription("Set the line width of the selected text display")
    public void setTextWidth(EasPlayer sender, @Argument("value") int value) {
        Element element = getElementOrError(sender);
        if (element == null) {
            return;
        }
        PropertyContainer properties = PropertyContainer.tracked(element, sender.get());
        Property<Integer> property = properties.getOrNull(TextDisplayLineWidthProperty.TYPE);
        if (property == null) {
            sender.sendMessage(Component.text("Cannot change the line width of this entity.", NamedTextColor.RED));
            return;
        }
        if (property.setValue(value)) {
            properties.commit();
            sender.sendMessage(Component.text("Changed the line width to ", NamedTextColor.GREEN)
                    .append(Component.text(value)));
        } else {
            sender.sendMessage(Component.text("Unable to change the line width", NamedTextColor.RED));
        }
    }

    @CommandMethod("text background color <value>")
    @CommandPermission("easyarmorstands.property.display.text.background")
    @CommandDescription("Set the background color of the selected text display")
    public void setTextBackground(EasPlayer sender, @Argument("value") TextColor color) {
        Element element = getElementOrError(sender);
        if (element == null) {
            return;
        }
        PropertyContainer properties = PropertyContainer.tracked(element, sender.get());
        Property<Color> property = properties.getOrNull(TextDisplayBackgroundProperty.TYPE);
        if (property == null) {
            sender.sendMessage(Component.text("Cannot change the text background color of this entity.", NamedTextColor.RED));
            return;
        }
        Color value = Color.fromRGB(color.value());
        if (property.setValue(value)) {
            properties.commit();
            sender.sendMessage(Component.text("Changed the background color to ", NamedTextColor.GREEN)
                    .append(property.getType().getValueComponent(value)));
        } else {
            sender.sendMessage(Component.text("Unable to change the background color", NamedTextColor.RED));
        }
    }

    @CommandMethod("text background reset")
    @CommandPermission("easyarmorstands.property.display.text.background")
    @CommandDescription("Restore the default background color of the selected text display")
    public void resetTextBackground(EasPlayer sender) {
        Element element = getElementOrError(sender);
        if (element == null) {
            return;
        }
        PropertyContainer properties = PropertyContainer.tracked(element, sender.get());
        Property<Color> property = properties.getOrNull(TextDisplayBackgroundProperty.TYPE);
        if (property == null) {
            sender.sendMessage(Component.text("Cannot change the text background color of this entity.", NamedTextColor.RED));
            return;
        }
        if (property.setValue(null)) {
            properties.commit();
            sender.sendMessage(Component.text("Reset the background color", NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Unable to change the background color", NamedTextColor.RED));
        }
    }

    @CommandMethod("text background none")
    @CommandPermission("easyarmorstands.property.display.text.background")
    @CommandDescription("Hide the background of the selected text display")
    public void hideTextBackground(EasPlayer sender) {
        Element element = getElementOrError(sender);
        if (element == null) {
            return;
        }
        PropertyContainer properties = PropertyContainer.tracked(element, sender.get());
        Property<Color> property = properties.getOrNull(TextDisplayBackgroundProperty.TYPE);
        if (property == null) {
            sender.sendMessage(Component.text("Cannot change the text background color of this entity.", NamedTextColor.RED));
            return;
        }
        Color value = Color.fromARGB(0);
        if (property.setValue(value)) {
            properties.commit();
            sender.sendMessage(Component.text("Made the background invisible", NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Unable to change the background color", NamedTextColor.RED));
        }
    }

    @CommandMethod("text background alpha <value>")
    @CommandPermission("easyarmorstands.property.display.text.background")
    @CommandDescription("Set the background transparency of the selected text display")
    public void hideTextBackground(EasPlayer sender, @Argument("value") @Range(min = "0", max = "255") int alpha) {
        Element element = getElementOrError(sender);
        if (element == null) {
            return;
        }
        PropertyContainer properties = PropertyContainer.tracked(element, sender.get());
        Property<Color> property = properties.getOrNull(TextDisplayBackgroundProperty.TYPE);
        if (property == null) {
            sender.sendMessage(Component.text("Cannot change the text background color of this entity.", NamedTextColor.RED));
            return;
        }
        Color oldValue = property.getValue();
        if (oldValue == null) {
            sender.sendMessage(Component.text("No background color configured", NamedTextColor.RED));
            return;
        }

        Color value = oldValue.setAlpha(alpha);
        if (property.setValue(value)) {
            properties.commit();
            sender.sendMessage(Component.text("Changed the background transparency to ", NamedTextColor.GREEN)
                    .append(Component.text(alpha)));
        } else {
            sender.sendMessage(Component.text("Unable to change the background color", NamedTextColor.RED));
        }
    }

    @CommandMethod("scale <scale>")
    @CommandPermission("easyarmorstands.property.display.scale")
    @CommandDescription("Set the scale of the selected display")
    public void editScale(EasPlayer sender, @Argument("scale") float scale) {
        Element element = getElementOrError(sender);
        if (element == null) {
            return;
        }
        PropertyContainer properties = PropertyContainer.tracked(element, sender.get());
        Property<Vector3fc> property = properties.getOrNull(DisplayScaleProperty.TYPE);
        if (property == null) {
            sender.sendMessage(Component.text("Cannot change the scale of this entity.", NamedTextColor.RED));
            return;
        }
        Vector3f value = new Vector3f(scale);
        if (property.setValue(value)) {
            properties.commit();
            sender.sendMessage(Component.text("Changed scale to ", NamedTextColor.GREEN)
                    .append(property.getType().getValueComponent(value)));
        } else {
            sender.sendMessage(Component.text("Unable to change scale", NamedTextColor.RED));
        }
    }

    @CommandMethod("shear")
    @CommandPermission("easyarmorstands.property.display.shearing")
    @CommandDescription("Modify the shearing of the selected display")
    public void editRightRotation(EasPlayer sender) {
        Session session = getSessionOrError(sender);
        Element element = getElementOrError(sender, session);
        if (element == null) {
            return;
        }
        PropertyContainer properties = PropertyContainer.tracked(element, sender.get());
        DisplayBone bone;
        DisplayMenuNode node;
        try {
            bone = new DisplayBone(properties, DisplayRightRotationProperty.TYPE);
            node = new DisplayMenuNode(session, Component.text("Shearing", NamedTextColor.GOLD), properties);
        } catch (UnknownPropertyException e) {
            sender.sendMessage(Component.text("Cannot edit the shearing of this entity.", NamedTextColor.RED));
            return;
        }
        node.addRotationButtons(session, bone, 1, null);
        session.pushNode(node);
    }

    @CommandMethod("convert")
    @CommandPermission("easyarmorstands.convert")
    @CommandDescription("Convert the selected armor stand to an item display")
    public void convert(EasPlayer sender) {
        Session session = getSessionOrError(sender);
        Element element = getElementOrError(sender, session);
        if (element == null) {
            return;
        }
        if (!(element instanceof ArmorStandElement)) {
            sender.sendMessage(Component.text("Only armor stands can be converted.", NamedTextColor.RED));
            return;
        }
        Player player = sender.get();
        ArmorStand entity = ((ArmorStandElement) element).getEntity();
        EntityEquipment equipment = entity.getEquipment();
        if (equipment == null) {
            return;
        }

        Matrix4d headMatrix = new Matrix4d();
        Matrix4d rightMatrix = new Matrix4d();
        Matrix4d leftMatrix = new Matrix4d();

        if (entity.isSmall()) {
            headMatrix.scale(0.7);
            rightMatrix.scale(0.5);
            leftMatrix.scale(0.5);
        }

        if (isSkull(equipment.getHelmet())) {
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

        if (Bukkit.getBukkitVersion().equals("1.19.4-R0.1-SNAPSHOT")) {
            headMatrix.rotateY(Math.PI);
            rightMatrix.rotateY(Math.PI);
            leftMatrix.rotateY(Math.PI);
        }

        List<Action> actions = new ArrayList<>();
        convert(player, entity, equipment.getHelmet(), ArmorStandPart.HEAD, ItemDisplay.ItemDisplayTransform.HEAD, headMatrix, actions);
        convert(player, entity, equipment.getItemInMainHand(), ArmorStandPart.RIGHT_ARM, ItemDisplay.ItemDisplayTransform.THIRDPERSON_RIGHTHAND, rightMatrix, actions);
        convert(player, entity, equipment.getItemInOffHand(), ArmorStandPart.LEFT_ARM, ItemDisplay.ItemDisplayTransform.THIRDPERSON_LEFTHAND, leftMatrix, actions);
        if (actions.isEmpty()) {
            session.sendMessage(Component.text("Unable to convert", NamedTextColor.RED));
            return;
        }

        actions.add(new ElementDestroyAction(element));
        entity.remove();

        EasyArmorStands.getInstance().getHistory(session.getPlayer()).push(actions);
    }

    private boolean isSkull(ItemStack item) {
        return item != null && item.getItemMeta() instanceof SkullMeta;
    }

    private void convert(Player player, ArmorStand entity, ItemStack item, ArmorStandPart part, ItemDisplay.ItemDisplayTransform itemTransform, Matrix4dc matrix, List<Action> actions) {
        if (item == null || item.getType().isAir()) {
            return;
        }

        Location location = entity.getLocation();
        Vector3d offset = part.getOffset(ArmorStandSize.get(entity)).rotateY(Util.getRoundedYawAngle(location.getYaw()), new Vector3d());
        location.add(offset.x, offset.y, offset.z);

        EulerAngle angle = part.getPose(entity);
        Matrix4d transform = new Matrix4d()
                .rotateY(Util.getRoundedYawAngle(location.getYaw()))
                .rotateZYX(-angle.getZ(), -angle.getY(), angle.getX())
                .mul(matrix);

        location.setYaw(0);
        location.setPitch(0);

        PropertyRegistry properties = new PropertyRegistry();
        properties.register(Property.of(EntityLocationProperty.TYPE, location));
        properties.register(Property.of(ItemDisplayItemProperty.TYPE, item));
        properties.register(Property.of(ItemDisplayTransformProperty.TYPE, itemTransform));
        properties.register(Property.of(DisplayTranslationProperty.TYPE, transform.getTranslation(new Vector3d()).get(new Vector3f())));
        properties.register(Property.of(DisplayLeftRotationProperty.TYPE, transform.getUnnormalizedRotation(new Quaternionf())));
        properties.register(Property.of(DisplayScaleProperty.TYPE, transform.getScale(new Vector3d()).get(new Vector3f())));

        EntityElementType<ItemDisplay> type = addon.getItemDisplayType();

        PlayerCreateElementEvent event = new PlayerCreateElementEvent(player, type, properties);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }

        EntityElement<ItemDisplay> element = type.createElement(properties);
        actions.add(new ElementCreateAction(element));
    }
}
