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
import me.m56738.easyarmorstands.command.annotation.RequireEntity;
import me.m56738.easyarmorstands.command.annotation.RequireSession;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.EntityDestroyAction;
import me.m56738.easyarmorstands.history.action.EntitySpawnAction;
import me.m56738.easyarmorstands.node.v1_19_4.DisplayMenuNode;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayBrightnessProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayHeightProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayRightRotationProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayScaleProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayWidthProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.block.BlockDisplayBlockProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplayBackgroundProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplayLineWidthProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplayTextProperty;
import me.m56738.easyarmorstands.session.EntitySpawner;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.ArmorStandSize;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.audience.Audience;
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
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.ArrayList;
import java.util.List;

@CommandMethod("eas")
public class DisplayCommands {
    private final DisplayAddon addon;

    public DisplayCommands(DisplayAddon addon) {
        this.addon = addon;
    }

    @CommandMethod("block <value>")
    @CommandPermission("easyarmorstands.property.display.block")
    @CommandDescription("Set the block of the selected block display")
    @RequireSession
    @RequireEntity(BlockDisplay.class)
    public void setBlock(Audience sender, PropertyContainer container, @Argument("value") BlockData value) {
        Property<BlockData> property = container.get(BlockDisplayBlockProperty.TYPE);
        if (property.setValue(value)) {
            container.commit();
            sender.sendMessage(Component.text("Changed block to ", NamedTextColor.GREEN)
                    .append(property.getType().getValueComponent(value)));
        } else {
            sender.sendMessage(Component.text("Unable to change block", NamedTextColor.RED));
        }
    }

    @CommandMethod("brightness block <value>")
    @CommandPermission("easyarmorstands.property.display.brightness")
    @CommandDescription("Set the block light level of the selected display")
    @RequireSession
    @RequireEntity(Display.class)
    public void setBlockBrightness(Audience sender, PropertyContainer container, @Argument("value") @Range(min = "0", max = "15") int value) {
        Location location = container.get(EntityLocationProperty.TYPE).getValue();
        Property<Display.Brightness> property = container.get(DisplayBrightnessProperty.TYPE);
        Display.Brightness brightness = property.getValue();
        if (brightness != null) {
            brightness = new Display.Brightness(value, brightness.getSkyLight());
        } else {
            brightness = new Display.Brightness(value, location.getBlock().getLightFromSky());
        }
        if (property.setValue(brightness)) {
            container.commit();
            sender.sendMessage(Component.text("Changed block brightness to ", NamedTextColor.GREEN)
                    .append(Component.text(value)));
        } else {
            sender.sendMessage(Component.text("Unable to change brightness", NamedTextColor.RED));
        }
    }

    @CommandMethod("brightness sky <value>")
    @CommandPermission("easyarmorstands.property.display.brightness")
    @CommandDescription("Set the sky light level of the selected display")
    @RequireSession
    @RequireEntity(Display.class)
    public void setSkyBrightness(Audience sender, PropertyContainer container, @Argument("value") @Range(min = "0", max = "15") int value) {
        Location location = container.get(EntityLocationProperty.TYPE).getValue();
        Property<Display.Brightness> property = container.get(DisplayBrightnessProperty.TYPE);
        Display.Brightness brightness = property.getValue();
        if (brightness != null) {
            brightness = new Display.Brightness(brightness.getBlockLight(), value);
        } else {
            brightness = new Display.Brightness(location.getBlock().getLightFromBlocks(), value);
        }
        if (property.setValue(brightness)) {
            container.commit();
            sender.sendMessage(Component.text("Changed sky brightness to ", NamedTextColor.GREEN)
                    .append(Component.text(value)));
        } else {
            sender.sendMessage(Component.text("Unable to change brightness", NamedTextColor.RED));
        }
    }

    @CommandMethod("brightness here")
    @CommandPermission("easyarmorstands.property.display.brightness")
    @CommandDescription("Apply the light level at your location to the selected display")
    @RequireSession
    @RequireEntity(Display.class)
    public void setLocalBrightness(EasPlayer sender, PropertyContainer container) {
        Block block = sender.get().getLocation().getBlock();
        Display.Brightness brightness = new Display.Brightness(block.getLightFromBlocks(), block.getLightFromSky());
        Property<Display.Brightness> property = container.get(DisplayBrightnessProperty.TYPE);
        if (property.setValue(brightness)) {
            container.commit();
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
    @RequireSession
    @RequireEntity(Display.class)
    public void setDefaultBrightness(Audience sender, PropertyContainer container) {
        Property<Display.Brightness> property = container.get(DisplayBrightnessProperty.TYPE);
        if (property.setValue(null)) {
            container.commit();
            sender.sendMessage(Component.text("Removed custom brightness settings", NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Unable to change brightness", NamedTextColor.RED));
        }
    }

    @CommandMethod("box width <width>")
    @CommandPermission("easyarmorstands.property.display.size")
    @CommandDescription("Set the bounding box width of the selected display")
    @RequireSession
    @RequireEntity(Display.class)
    public void setBoxWidth(Audience sender, PropertyContainer container, @Argument("width") float value) {
        Property<Float> widthProperty = container.get(DisplayWidthProperty.TYPE);
        if (widthProperty.setValue(value)) {
            Property<Float> heightProperty = container.get(DisplayHeightProperty.TYPE);
            if (heightProperty.getValue() == 0f) {
                heightProperty.setValue(value);
            }
            container.commit();
            sender.sendMessage(Component.text("Changed bounding box width to ", NamedTextColor.GREEN)
                    .append(Component.text(value)));
        } else {
            sender.sendMessage(Component.text("Unable to change bounding box size", NamedTextColor.RED));
        }
    }

    @CommandMethod("box height <height>")
    @CommandPermission("easyarmorstands.property.display.size")
    @CommandDescription("Set the bounding box height of the selected display")
    @RequireSession
    @RequireEntity(Display.class)
    public void setBoxHeight(Audience sender, PropertyContainer container, @Argument("height") float value) {
        Property<Float> heightProperty = container.get(DisplayHeightProperty.TYPE);
        if (heightProperty.setValue(value)) {
            Property<Float> widthProperty = container.get(DisplayWidthProperty.TYPE);
            if (widthProperty.getValue() == 0f) {
                widthProperty.setValue(value);
            }
            container.commit();
            sender.sendMessage(Component.text("Changed bounding box height to ", NamedTextColor.GREEN)
                    .append(Component.text(value)));
        } else {
            sender.sendMessage(Component.text("Unable to change bounding box size", NamedTextColor.RED));
        }
    }

    @CommandMethod("box remove")
    @CommandPermission("easyarmorstands.property.display.size")
    @CommandDescription("Remove the bounding box from the selected display")
    @RequireSession
    @RequireEntity(Display.class)
    public void removeBox(Audience sender, PropertyContainer container) {
        int success = 0;
        Property<Float> widthProperty = container.get(DisplayWidthProperty.TYPE);
        if (widthProperty.setValue(0f)) {
            success++;
        }
        Property<Float> heightProperty = container.get(DisplayHeightProperty.TYPE);
        if (heightProperty.setValue(0f)) {
            success++;
        }

        if (success > 0) {
            container.commit();
            sender.sendMessage(Component.text("Removed the bounding box", NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Unable to change bounding box size", NamedTextColor.RED));
        }
    }

    @CommandMethod("box move")
    @CommandPermission("easyarmorstands.property.display.translation")
    @CommandDescription("Select a tool to move the bounding box of the selected display")
    @RequireSession
    @RequireEntity(Display.class)
    public void moveBox(Session session, PropertyContainer container) {
        DisplayBoxBone bone = new DisplayBoxBone(container);
        DisplayMenuNode node = new DisplayMenuNode(session, Component.text("Bounding box", NamedTextColor.GOLD), container);
        node.setShowBoundingBoxIfInactive(true); // bounding box should remain visible while a tool node is active
        node.addPositionButtons(session, bone, 3);
        node.addCarryButton(session, bone);
        session.pushNode(node);
    }

    @CommandMethod("text")
    @CommandPermission("easyarmorstands.property.display.text")
    @CommandDescription("Show the text of the selected text display")
    @RequireSession
    @RequireEntity(TextDisplay.class)
    public void showText(Audience sender, PropertyContainer container) {
        Component text = container.get(TextDisplayTextProperty.TYPE).getValue();
        SessionCommands.showText(sender, Component.text("Text", NamedTextColor.YELLOW), text, "/eas text set");
    }

    @CommandMethod("text set <value>")
    @CommandPermission("easyarmorstands.property.display.text")
    @CommandDescription("Set the text of the selected text display")
    @RequireSession
    @RequireEntity(TextDisplay.class)
    public void setText(Audience sender, PropertyContainer container, @Argument("value") @Greedy String input) {
        Component value = MiniMessage.miniMessage().deserialize(input);
        Property<Component> property = container.get(TextDisplayTextProperty.TYPE);
        if (property.setValue(value)) {
            container.commit();
            sender.sendMessage(Component.text("Changed the text to ", NamedTextColor.GREEN)
                    .append(value));
        } else {
            sender.sendMessage(Component.text("Unable to change the text", NamedTextColor.RED));
        }
    }

    @CommandMethod("text width <value>")
    @CommandPermission("easyarmorstands.property.display.text.linewidth")
    @CommandDescription("Set the line width of the selected text display")
    @RequireSession
    @RequireEntity(TextDisplay.class)
    public void setTextWidth(Audience sender, PropertyContainer container, @Argument("value") int value) {
        Property<Integer> property = container.get(TextDisplayLineWidthProperty.TYPE);
        if (property.setValue(value)) {
            container.commit();
            sender.sendMessage(Component.text("Changed the line width to ", NamedTextColor.GREEN)
                    .append(Component.text(value)));
        } else {
            sender.sendMessage(Component.text("Unable to change the line width", NamedTextColor.RED));
        }
    }

    @CommandMethod("text background color <value>")
    @CommandPermission("easyarmorstands.property.display.text.background")
    @CommandDescription("Set the background color of the selected text display")
    @RequireSession
    @RequireEntity(TextDisplay.class)
    public void setTextBackground(Audience sender, PropertyContainer container, @Argument("value") TextColor color) {
        Color value = Color.fromRGB(color.value());
        Property<Color> property = container.get(TextDisplayBackgroundProperty.TYPE);
        if (property.setValue(value)) {
            container.commit();
            sender.sendMessage(Component.text("Changed the background color to ", NamedTextColor.GREEN)
                    .append(property.getType().getValueComponent(value)));
        } else {
            sender.sendMessage(Component.text("Unable to change the background color", NamedTextColor.RED));
        }
    }

    @CommandMethod("text background reset")
    @CommandPermission("easyarmorstands.property.display.text.background")
    @CommandDescription("Restore the default background color of the selected text display")
    @RequireSession
    @RequireEntity(TextDisplay.class)
    public void resetTextBackground(Audience sender, PropertyContainer container) {
        Property<Color> property = container.get(TextDisplayBackgroundProperty.TYPE);
        if (property.setValue(null)) {
            container.commit();
            sender.sendMessage(Component.text("Reset the background color", NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Unable to change the background color", NamedTextColor.RED));
        }
    }

    @CommandMethod("text background none")
    @CommandPermission("easyarmorstands.property.display.text.background")
    @CommandDescription("Hide the background of the selected text display")
    @RequireSession
    @RequireEntity(TextDisplay.class)
    public void hideTextBackground(Audience sender, PropertyContainer container) {
        Property<Color> property = container.get(TextDisplayBackgroundProperty.TYPE);
        Color value = Color.fromARGB(0);
        if (property.setValue(value)) {
            container.commit();
            sender.sendMessage(Component.text("Made the background invisible", NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Unable to change the background color", NamedTextColor.RED));
        }
    }

    @CommandMethod("text background alpha <value>")
    @CommandPermission("easyarmorstands.property.display.text.background")
    @CommandDescription("Set the background transparency of the selected text display")
    @RequireSession
    @RequireEntity(TextDisplay.class)
    public void hideTextBackground(Audience sender, PropertyContainer container, @Argument("value") @Range(min = "0", max = "255") int alpha) {
        Property<@Nullable Color> property = container.get(TextDisplayBackgroundProperty.TYPE);
        Color oldValue = property.getValue();
        if (oldValue == null) {
            sender.sendMessage(Component.text("No background color configured", NamedTextColor.RED));
            return;
        }

        Color value = oldValue.setAlpha(alpha);
        if (property.setValue(value)) {
            container.commit();
            sender.sendMessage(Component.text("Changed the background transparency to ", NamedTextColor.GREEN)
                    .append(Component.text(alpha)));
        } else {
            sender.sendMessage(Component.text("Unable to change the background color", NamedTextColor.RED));
        }
    }

    @CommandMethod("scale <scale>")
    @CommandPermission("easyarmorstands.property.display.scale")
    @CommandDescription("Set the scale of the selected display")
    @RequireSession
    @RequireEntity(Display.class)
    public void editScale(Audience sender, PropertyContainer container, @Argument("scale") float scale) {
        Vector3f value = new Vector3f(scale);
        Property<Vector3fc> property = container.get(DisplayScaleProperty.TYPE);
        if (property.setValue(value)) {
            container.commit();
            sender.sendMessage(Component.text("Changed scale to ", NamedTextColor.GREEN)
                    .append(property.getType().getValueComponent(value)));
        } else {
            sender.sendMessage(Component.text("Unable to change scale", NamedTextColor.RED));
        }
    }

    @CommandMethod("shear")
    @CommandPermission("easyarmorstands.property.display.shearing")
    @CommandDescription("Modify the shearing of the selected display")
    @RequireSession
    @RequireEntity(Display.class)
    public void editRightRotation(Session session, PropertyContainer container) {
        DisplayBone rotationBone = new DisplayBone(container, DisplayRightRotationProperty.TYPE);
        DisplayMenuNode node = new DisplayMenuNode(session, Component.text("Shearing", NamedTextColor.GOLD), container);
        node.addRotationButtons(session, rotationBone, 1, null);
        session.pushNode(node);
    }

    @CommandMethod("convert")
    @CommandPermission("easyarmorstands.convert")
    @CommandDescription("Convert the selected armor stand to an item display")
    @RequireSession
    @RequireEntity(ArmorStand.class)
    public void convert(Session session, ArmorStand entity) {
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
        convert(session, entity, equipment.getHelmet(), ArmorStandPart.HEAD, ItemDisplay.ItemDisplayTransform.HEAD, headMatrix, actions);
        convert(session, entity, equipment.getItemInMainHand(), ArmorStandPart.RIGHT_ARM, ItemDisplay.ItemDisplayTransform.THIRDPERSON_RIGHTHAND, rightMatrix, actions);
        convert(session, entity, equipment.getItemInOffHand(), ArmorStandPart.LEFT_ARM, ItemDisplay.ItemDisplayTransform.THIRDPERSON_LEFTHAND, leftMatrix, actions);
        if (actions.isEmpty()) {
            session.sendMessage(Component.text("Unable to convert", NamedTextColor.RED));
            return;
        }

        actions.add(new EntityDestroyAction<>(entity));
        entity.remove();

        EasyArmorStands.getInstance().getHistory(session.getPlayer()).push(actions);
    }

    private boolean isSkull(ItemStack item) {
        return item != null && item.getItemMeta() instanceof SkullMeta;
    }

    private void convert(Session session, ArmorStand entity, ItemStack item, ArmorStandPart part, ItemDisplay.ItemDisplayTransform itemTransform, Matrix4dc matrix, List<Action> actions) {
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

        EntitySpawner<ItemDisplay> spawner = EntitySpawner.of(EntityType.ITEM_DISPLAY, e -> {
            e.setItemStack(item);
            e.setItemDisplayTransform(itemTransform);
            e.setTransformation(addon.getMapper().getTransformation(
                    transform.getTranslation(new Vector3d()).get(new Vector3f()),
                    transform.getUnnormalizedRotation(new Quaternionf()),
                    transform.getScale(new Vector3d()).get(new Vector3f()),
                    new Quaternionf()
            ));
        });
        location.setYaw(0);
        location.setPitch(0);
        ItemDisplay display = EntitySpawner.trySpawn(spawner, location, session.getPlayer());
        if (display == null) {
            return;
        }

        actions.add(new EntitySpawnAction<>(location, spawner, display.getUniqueId()));
    }
}
