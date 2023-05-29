package me.m56738.easyarmorstands.addon.display;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.bone.v1_19_4.DisplayBone;
import me.m56738.easyarmorstands.bone.v1_19_4.DisplayTranslationBone;
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
import me.m56738.easyarmorstands.property.v1_19_4.display.block.BlockDisplayBlockProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.item.ItemDisplayItemProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.item.ItemDisplayTransformProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplayBackgroundProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplayLineWidthProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplayShadowProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplayTextProperty;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.session.v1_19_4.DisplaySessionListener;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import net.kyori.adventure.audience.Audience;
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
    private DisplayTranslationProperty displayTranslationProperty;
    private DisplayLeftRotationProperty displayLeftRotationProperty;
    private DisplayScaleProperty displayScaleProperty;
    private DisplayRightRotationProperty displayRightRotationProperty;
    private DisplayBrightnessProperty displayBrightnessProperty;
    private DisplayWidthProperty displayWidthProperty;
    private DisplayHeightProperty displayHeightProperty;
    private ItemDisplayItemProperty itemDisplayItemProperty;
    private ItemDisplayTransformProperty itemDisplayTransformProperty;
    private BlockDisplayBlockProperty blockDisplayBlockProperty;
    private TextDisplayBackgroundProperty textDisplayBackgroundProperty;
    private TextDisplayLineWidthProperty textDisplayLineWidthProperty;
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

        displayTranslationProperty = new DisplayTranslationProperty(mapper);
        plugin.getEntityPropertyRegistry().register(displayTranslationProperty);
        displayLeftRotationProperty = new DisplayLeftRotationProperty(mapper);
        plugin.getEntityPropertyRegistry().register(displayLeftRotationProperty);
        displayScaleProperty = new DisplayScaleProperty(mapper);
        plugin.getEntityPropertyRegistry().register(displayScaleProperty);
        displayRightRotationProperty = new DisplayRightRotationProperty(mapper);
        plugin.getEntityPropertyRegistry().register(displayRightRotationProperty);
        displayBrightnessProperty = new DisplayBrightnessProperty();
        plugin.getEntityPropertyRegistry().register(displayBrightnessProperty);
        displayWidthProperty = new DisplayWidthProperty();
        plugin.getEntityPropertyRegistry().register(displayWidthProperty);
        displayHeightProperty = new DisplayHeightProperty(this);
        plugin.getEntityPropertyRegistry().register(displayHeightProperty);
        itemDisplayItemProperty = new ItemDisplayItemProperty();
        plugin.getEntityPropertyRegistry().register(itemDisplayItemProperty);
        itemDisplayTransformProperty = new ItemDisplayTransformProperty();
        plugin.getEntityPropertyRegistry().register(itemDisplayTransformProperty);
        blockDisplayBlockProperty = new BlockDisplayBlockProperty();
        plugin.getEntityPropertyRegistry().register(blockDisplayBlockProperty);
        if (TextDisplayBackgroundProperty.isSupported()) {
            textDisplayBackgroundProperty = new TextDisplayBackgroundProperty();
            plugin.getEntityPropertyRegistry().register(textDisplayBackgroundProperty);
        }
        textDisplayLineWidthProperty = new TextDisplayLineWidthProperty();
        plugin.getEntityPropertyRegistry().register(textDisplayLineWidthProperty);
        textDisplayShadowProperty = new TextDisplayShadowProperty();
        plugin.getEntityPropertyRegistry().register(textDisplayShadowProperty);
        textDisplayTextProperty = new TextDisplayTextProperty(textDisplayCapability);
        plugin.getEntityPropertyRegistry().register(textDisplayTextProperty);

        DisplaySessionListener listener = new DisplaySessionListener(this);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);

        EntityGlowingProperty.addToBlacklist(EntityType.TEXT_DISPLAY);

        EasyArmorStands.getInstance().getAnnotationParser().parse(this);
    }

    @CommandMethod("eas translation")
    @CommandPermission("easyarmorstands.property.display.transformation")
    @RequireSession
    @RequireEntity(Display.class)
    public void editTranslation(Session session, Display entity) {
        DisplayTranslationBone translationBone = new DisplayTranslationBone(session, entity, this);
        DisplayMenuNode node = new DisplayMenuNode(session, Component.text("Translation", NamedTextColor.GOLD), entity);
        node.addPositionButtons(session, translationBone, 3, true);
        node.addCarryButton(session, translationBone);
        session.pushNode(node);
    }

    @CommandMethod("eas scale <scale>")
    @CommandPermission("easyarmorstands.property.display.scale")
    @RequireSession
    @RequireEntity(Display.class)
    public void editScale(Audience sender, Session session, Display entity, @Argument("scale") float scale) {
        Vector3f value = new Vector3f(scale);
        if (session.setProperty(entity, displayScaleProperty, value)) {
            sender.sendMessage(Component.text("Changed scale to ", NamedTextColor.GREEN)
                    .append(displayScaleProperty.getValueName(value)));
        } else {
            sender.sendMessage(Component.text("Unable to change scale", NamedTextColor.RED));
        }
    }

    @CommandMethod("eas shear")
    @CommandPermission("easyarmorstands.property.display.shearing")
    @RequireSession
    @RequireEntity(Display.class)
    public void editRightRotation(Session session, Display entity) {
        DisplayBone rotationBone = new DisplayBone(session, entity, this, displayRightRotationProperty);
        DisplayMenuNode node = new DisplayMenuNode(session, Component.text("Shearing", NamedTextColor.GOLD), entity);
        node.addRotationButtons(session, rotationBone, 1, null);
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
            e.setTransformation(mapper.getTransformation(
                    transform.getTranslation(new Vector3d()).get(new Vector3f()),
                    transform.getUnnormalizedRotation(new Quaternionf()),
                    transform.getScale(new Vector3d()).get(new Vector3f()),
                    new Quaternionf()
            ));
        });
        Bukkit.getPluginManager().callEvent(new SessionSpawnEvent(session, display));
        actions.add(new EntitySpawnAction<>(display));
    }

    public JOMLMapper getMapper() {
        return mapper;
    }

    public DisplayTranslationProperty getDisplayTranslationProperty() {
        return displayTranslationProperty;
    }

    public DisplayLeftRotationProperty getDisplayLeftRotationProperty() {
        return displayLeftRotationProperty;
    }

    public DisplayScaleProperty getDisplayScaleProperty() {
        return displayScaleProperty;
    }

    public DisplayRightRotationProperty getDisplayRightRotationProperty() {
        return displayRightRotationProperty;
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

    public TextDisplayLineWidthProperty getTextDisplayLineWidthProperty() {
        return textDisplayLineWidthProperty;
    }

    public TextDisplayShadowProperty getTextDisplayShadowProperty() {
        return textDisplayShadowProperty;
    }

    public TextDisplayTextProperty getTextDisplayTextProperty() {
        return textDisplayTextProperty;
    }
}
