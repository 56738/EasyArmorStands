package me.m56738.easyarmorstands.command.element.display;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.ElementSelectionNode;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.element.ElementTypeRegistry;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.ItemDisplayPropertyTypes;
import me.m56738.easyarmorstands.command.requirement.RequireElementSelection;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.command.util.ElementSelection;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.group.Group;
import me.m56738.easyarmorstands.group.node.GroupRootNode;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.ElementCreateAction;
import me.m56738.easyarmorstands.history.action.ElementDestroyAction;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.util.ArmorStandPartInfo;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.EulerAngle;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.annotations.processing.CommandContainer;
import org.joml.Math;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

@CommandContainer
public class ConvertCommand {
    @Command("eas convert")
    @Permission(Permissions.CONVERT)
    @CommandDescription("easyarmorstands.command.description.convert")
    @RequireElementSelection
    public void convert(
            EasPlayer sender,
            Session session,
            ElementSelection selection,
            ElementTypeRegistry elementTypeRegistry) {
        ElementType itemDisplayType = elementTypeRegistry.get(EntityType.ITEM_DISPLAY.key());

        List<Element> createdElements = new ArrayList<>();
        List<Action> allActions = new ArrayList<>();
        boolean foundArmorStand = false;
        int count = 0;
        boolean isInverted = Bukkit.getBukkitVersion().equals("1.19.4-R0.1-SNAPSHOT");
        for (Element element : selection.elements()) {
            if (!(element instanceof ArmorStandElement)) {
                continue;
            }
            foundArmorStand = true;
            ArmorStand entity = ((ArmorStandElement) element).getEntity();
            EntityEquipment equipment = entity.getEquipment();

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
                headMatrix.rotateY(org.joml.Math.PI);
            } else {
                headMatrix.translate(0, 0.25, 0);
                headMatrix.scale(0.625);
            }

            rightMatrix.translate(-0.0625, -0.625, 0.125);
            rightMatrix.rotateX(org.joml.Math.PI / 2);

            leftMatrix.translate(0.0625, -0.625, 0.125);
            leftMatrix.rotateX(org.joml.Math.PI / 2);

            if (isInverted) {
                headMatrix.rotateY(org.joml.Math.PI);
                rightMatrix.rotateY(org.joml.Math.PI);
                leftMatrix.rotateY(Math.PI);
            }

            List<Action> actions = new ArrayList<>();
            convert(sender, entity, equipment.getHelmet(), ArmorStandPart.HEAD, ItemDisplay.ItemDisplayTransform.HEAD, headMatrix, actions, createdElements, itemDisplayType);
            convert(sender, entity, equipment.getItemInMainHand(), ArmorStandPart.RIGHT_ARM, ItemDisplay.ItemDisplayTransform.THIRDPERSON_RIGHTHAND, rightMatrix, actions, createdElements, itemDisplayType);
            convert(sender, entity, equipment.getItemInOffHand(), ArmorStandPart.LEFT_ARM, ItemDisplay.ItemDisplayTransform.THIRDPERSON_LEFTHAND, leftMatrix, actions, createdElements, itemDisplayType);
            if (!actions.isEmpty()) {
                actions.add(new ElementDestroyAction(element));
                entity.remove();
                count++;
            }
            allActions.addAll(actions);
        }

        sender.history().push(allActions, Message.component("easyarmorstands.history.converted-armor-stand"));

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
            ChangeContext context = new EasPlayer(session.player());
            for (Element element : createdElements) {
                if (element instanceof EditableElement editableElement) {
                    if (context.canEditElement(editableElement)) {
                        group.addMember(editableElement);
                    }
                }
            }
        } else {
            // Select the created elements
            ElementSelectionNode selectionNode = session.findNode(ElementSelectionNode.class);
            if (selectionNode != null) {
                selectionNode.selectElements(createdElements.stream()
                        .filter(element -> element instanceof SelectableElement)
                        .map(element -> (SelectableElement) element)
                        .toList());
            }
        }
    }

    private boolean isSkull(ItemStack item) {
        return item != null && item.getItemMeta() instanceof SkullMeta;
    }

    private void convert(ChangeContext context, ArmorStand entity, ItemStack item, ArmorStandPart part, ItemDisplay.ItemDisplayTransform itemTransform, Matrix4dc matrix, List<Action> actions, List<Element> elements, ElementType itemDisplayType) {
        if (item == null || item.getType().isAir()) {
            return;
        }

        ArmorStandPartInfo info = ArmorStandPartInfo.of(part);
        Location location = entity.getLocation();
        Vector3d offset = info.getOffset(ArmorStandSize.get(entity), 1).rotateY(Util.getRoundedYawAngle(location.getYaw()), new Vector3d());
        location.add(offset.x, offset.y, offset.z);

        EulerAngle angle = part.getPose(entity);
        Matrix4d transform = new Matrix4d()
                .rotateY(Util.getRoundedYawAngle(location.getYaw()))
                .rotateZYX(-angle.getZ(), -angle.getY(), angle.getX())
                .mul(matrix);

        location.setYaw(0);
        location.setPitch(0);

        PropertyMap properties = new PropertyMap();
        properties.put(EntityPropertyTypes.LOCATION, location);
        properties.put(ItemDisplayPropertyTypes.ITEM, item);
        properties.put(ItemDisplayPropertyTypes.TRANSFORM, itemTransform);
        properties.put(DisplayPropertyTypes.TRANSLATION, transform.getTranslation(new Vector3d()).get(new Vector3f()));
        properties.put(DisplayPropertyTypes.LEFT_ROTATION, transform.getUnnormalizedRotation(new Quaternionf()));
        properties.put(DisplayPropertyTypes.SCALE, transform.getScale(new Vector3d()).get(new Vector3f()));

        if (!context.canCreateElement(itemDisplayType, properties)) {
            return;
        }

        Element element = itemDisplayType.createElement(properties);
        if (element == null) {
            return;
        }

        actions.add(new ElementCreateAction(element));
        elements.add(element);
    }
}
