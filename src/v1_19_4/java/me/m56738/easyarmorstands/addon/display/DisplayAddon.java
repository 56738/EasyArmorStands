package me.m56738.easyarmorstands.addon.display;

import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.bone.v1_19_4.DisplayOffsetBone;
import me.m56738.easyarmorstands.capability.textdisplay.TextDisplayCapability;
import me.m56738.easyarmorstands.command.annotation.RequireEntity;
import me.m56738.easyarmorstands.command.annotation.RequireSession;
import me.m56738.easyarmorstands.event.SessionPreSpawnEvent;
import me.m56738.easyarmorstands.event.SessionSpawnEvent;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.EntityDestroyAction;
import me.m56738.easyarmorstands.history.action.EntitySpawnAction;
import me.m56738.easyarmorstands.node.v1_19_4.DisplayMenuNode;
import me.m56738.easyarmorstands.property.entity.EntityGlowingProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.*;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.session.v1_19_4.DisplaySessionListener;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.joml.Math;
import org.joml.*;

import java.util.ArrayList;
import java.util.List;

public class DisplayAddon implements Addon {
    private JOMLMapper mapper;
    private DisplayTransformationProperty displayTransformationProperty;
    private DisplayBrightnessProperty displayBrightnessProperty;
    private DisplayWidthProperty displayWidthProperty;
    private DisplayHeightProperty displayHeightProperty;
    private ItemDisplayItemProperty itemDisplayItemProperty;
    private ItemDisplayTransformProperty itemDisplayTransformProperty;
    private BlockDisplayBlockProperty blockDisplayBlockProperty;
    private TextDisplayBackgroundProperty textDisplayBackgroundProperty;
    private TextDisplayShadowProperty textDisplayShadowProperty;
    private TextDisplayTextProperty textDisplayTextProperty;

    public DisplayAddon() {
        try {
            mapper = new JOMLMapper();
        } catch (Throwable ignored) {
        }
    }

    @Override
    public boolean isSupported() {
        return mapper != null;
    }

    @Override
    public String getName() {
        return "display entity";
    }

    @Override
    public void enable(EasyArmorStands plugin) {
        TextDisplayCapability textDisplayCapability = plugin.getCapability(TextDisplayCapability.class);

        displayTransformationProperty = new DisplayTransformationProperty(mapper);
        displayBrightnessProperty = new DisplayBrightnessProperty();
        displayWidthProperty = new DisplayWidthProperty();
        displayHeightProperty = new DisplayHeightProperty(this);
        itemDisplayItemProperty = new ItemDisplayItemProperty();
        itemDisplayTransformProperty = new ItemDisplayTransformProperty();
        blockDisplayBlockProperty = new BlockDisplayBlockProperty();
        textDisplayBackgroundProperty = new TextDisplayBackgroundProperty();
        textDisplayShadowProperty = new TextDisplayShadowProperty();
        textDisplayTextProperty = new TextDisplayTextProperty(textDisplayCapability);

        plugin.getEntityPropertyRegistry().register(displayTransformationProperty);
        plugin.getEntityPropertyRegistry().register(displayBrightnessProperty);
        plugin.getEntityPropertyRegistry().register(displayWidthProperty);
        plugin.getEntityPropertyRegistry().register(displayHeightProperty);
        plugin.getEntityPropertyRegistry().register(itemDisplayItemProperty);
        plugin.getEntityPropertyRegistry().register(itemDisplayTransformProperty);
        plugin.getEntityPropertyRegistry().register(blockDisplayBlockProperty);
        plugin.getEntityPropertyRegistry().register(textDisplayBackgroundProperty);
        plugin.getEntityPropertyRegistry().register(textDisplayShadowProperty);
        plugin.getEntityPropertyRegistry().register(textDisplayTextProperty);

        DisplaySessionListener listener = new DisplaySessionListener(this);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);

        EntityGlowingProperty.addToBlacklist(EntityType.TEXT_DISPLAY);

        EasyArmorStands.getInstance().getAnnotationParser().parse(this);
    }

    @CommandMethod("eas offset")
    @CommandPermission("easyarmorstands.property.display.transformation")
    @RequireSession
    @RequireEntity(Display.class)
    public void editOffset(Session session, Display entity) {
        DisplayOffsetBone bone = new DisplayOffsetBone(session, entity, this);
        DisplayMenuNode node = new DisplayMenuNode(session, Component.text("Display offset", NamedTextColor.GOLD), entity);
        node.addPositionButtons(session, bone, 3, true);
        node.addRotationButtons(session, bone, 1, false);
        session.pushNode(node);
    }

    @CommandMethod("eas convert")
    @CommandPermission("easyarmorstands.convert")
    @RequireSession
    @RequireEntity(ArmorStand.class)
    public void convert(Session session, ArmorStand entity) {
        EntityEquipment equipment = entity.getEquipment();
        if (equipment == null) {
            return;
        }
        List<Action> actions = new ArrayList<>();
        convert(session, entity, equipment.getHelmet(), ArmorStandPart.HEAD, ItemDisplay.ItemDisplayTransform.HEAD,
                new Matrix4d()
                        .translation(0, 0.25, 0)
                        .rotateY(Math.PI)
                        .scale(0.625),
                actions);
        convert(session, entity, equipment.getItemInMainHand(), ArmorStandPart.RIGHT_ARM, ItemDisplay.ItemDisplayTransform.THIRDPERSON_RIGHTHAND,
                new Matrix4d()
                        .translation(-0.0625, -0.625, 0.125)
                        .rotateY(Math.PI)
                        .rotateX(-Math.PI / 2),
                actions);
        convert(session, entity, equipment.getItemInOffHand(), ArmorStandPart.LEFT_ARM, ItemDisplay.ItemDisplayTransform.THIRDPERSON_LEFTHAND,
                new Matrix4d()
                        .translation(0.0625, -0.625, 0.125)
                        .rotateY(Math.PI)
                        .rotateX(-Math.PI / 2),
                actions);
        actions.add(new EntityDestroyAction<>(entity));
        entity.remove();
        EasyArmorStands.getInstance().getHistory(session.getPlayer()).push(actions);
    }

    private void convert(Session session, ArmorStand entity, ItemStack item, ArmorStandPart part, ItemDisplay.ItemDisplayTransform itemTransform, Matrix4dc matrix, List<Action> actions) {
        if (item == null || item.getType().isAir()) {
            return;
        }

        Location location = entity.getLocation();
        Vector3d offset = part.getOffset(entity).rotateY(-Math.toRadians(location.getYaw()), new Vector3d());
        location.add(offset.x, offset.y, offset.z);

        EulerAngle angle = part.getPose(entity);
        Matrix4d transform = new Matrix4d()
                .rotateY(-Math.toRadians(location.getYaw()))
                .rotateZYX(-angle.getZ(), -angle.getY(), angle.getX())
                .mul(matrix);

        SessionPreSpawnEvent preSpawnEvent = new SessionPreSpawnEvent(session, location, EntityType.ITEM_DISPLAY);
        Bukkit.getPluginManager().callEvent(preSpawnEvent);
        if (preSpawnEvent.isCancelled()) {
            return;
        }

        ItemDisplay display = entity.getWorld().spawn(location, ItemDisplay.class, e -> {
            e.setItemStack(item);
            e.setItemDisplayTransform(itemTransform);
            e.setTransformation(mapper.getTransformation(new Matrix4f(transform)));
        });
        Bukkit.getPluginManager().callEvent(new SessionSpawnEvent(session, display));
        actions.add(new EntitySpawnAction<>(display));
    }

    public JOMLMapper getMapper() {
        return mapper;
    }

    public DisplayTransformationProperty getDisplayTransformationProperty() {
        return displayTransformationProperty;
    }

    public DisplayBrightnessProperty getDisplayBrightnessProperty() {
        return displayBrightnessProperty;
    }

    public DisplayWidthProperty getDisplayWidthProperty() {
        return displayWidthProperty;
    }

    public DisplayHeightProperty getDisplayHeightProperty() {
        return displayHeightProperty;
    }

    public ItemDisplayItemProperty getItemDisplayItemProperty() {
        return itemDisplayItemProperty;
    }

    public ItemDisplayTransformProperty getItemDisplayTransformProperty() {
        return itemDisplayTransformProperty;
    }

    public BlockDisplayBlockProperty getBlockDisplayBlockProperty() {
        return blockDisplayBlockProperty;
    }

    public TextDisplayBackgroundProperty getTextDisplayBackgroundProperty() {
        return textDisplayBackgroundProperty;
    }

    public TextDisplayShadowProperty getTextDisplayShadowProperty() {
        return textDisplayShadowProperty;
    }

    public TextDisplayTextProperty getTextDisplayTextProperty() {
        return textDisplayTextProperty;
    }
}
