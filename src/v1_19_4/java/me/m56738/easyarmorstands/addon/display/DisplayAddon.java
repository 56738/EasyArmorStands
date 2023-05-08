package me.m56738.easyarmorstands.addon.display;

import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.bone.v1_19_4.DisplayOffsetBone;
import me.m56738.easyarmorstands.capability.textdisplay.TextDisplayCapability;
import me.m56738.easyarmorstands.node.v1_19_4.DisplayMenuNode;
import me.m56738.easyarmorstands.property.entity.EntityGlowingProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.*;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.session.v1_19_4.DisplaySessionListener;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;

public class DisplayAddon implements Addon {
    private JOMLMapper mapper;
    private DisplayTransformationProperty displayTransformationProperty;
    private DisplayBrightnessProperty displayBrightnessProperty;
    private DisplayWidthProperty displayWidthProperty;
    private DisplayHeightProperty displayHeightProperty;
    private ItemDisplayItemProperty itemDisplayItemProperty;
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
        blockDisplayBlockProperty = new BlockDisplayBlockProperty();
        textDisplayBackgroundProperty = new TextDisplayBackgroundProperty();
        textDisplayShadowProperty = new TextDisplayShadowProperty();
        textDisplayTextProperty = new TextDisplayTextProperty(textDisplayCapability);

        plugin.getEntityPropertyRegistry().register(displayTransformationProperty);
        plugin.getEntityPropertyRegistry().register(displayBrightnessProperty);
        plugin.getEntityPropertyRegistry().register(displayWidthProperty);
        plugin.getEntityPropertyRegistry().register(displayHeightProperty);
        plugin.getEntityPropertyRegistry().register(itemDisplayItemProperty);
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
    public void editOffset(Session session, Display entity) {
        DisplayOffsetBone bone = new DisplayOffsetBone(session, entity, this);
        DisplayMenuNode node = new DisplayMenuNode(session, Component.text("Display offset", NamedTextColor.GOLD), entity);
        node.addPositionButtons(session, bone, 3, true);
        node.addRotationButtons(session, bone, 1, false);
        session.pushNode(node);
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
