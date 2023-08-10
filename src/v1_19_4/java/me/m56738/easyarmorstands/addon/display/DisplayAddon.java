package me.m56738.easyarmorstands.addon.display;

import cloud.commandframework.brigadier.CloudBrigadierManager;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.capability.textdisplay.TextDisplayCapability;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.node.v1_19_4.BlockDataArgumentParser;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayBillboardProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayBrightnessProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayHeightProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayLeftRotationProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayRightRotationProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayScaleProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayTranslationProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayWidthProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.block.BlockDisplayBlockProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.item.ItemDisplayTransformProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplayAlignmentProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplayBackgroundProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplayLineWidthProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplaySeeThroughProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplayShadowProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplayTextProperty;
import me.m56738.easyarmorstands.session.v1_19_4.DisplaySessionListener;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.block.data.BlockData;

public class DisplayAddon implements Addon {
    private JOMLMapper mapper;
    private DisplayTranslationProperty displayTranslationProperty;
    private DisplayLeftRotationProperty displayLeftRotationProperty;
    private DisplayScaleProperty displayScaleProperty;
    private DisplayRightRotationProperty displayRightRotationProperty;
    private DisplayBillboardProperty displayBillboardProperty;
    private DisplayBrightnessProperty displayBrightnessProperty;
    private DisplayWidthProperty displayWidthProperty;
    private DisplayHeightProperty displayHeightProperty;
    private ItemDisplayTransformProperty itemDisplayTransformProperty;
    private BlockDisplayBlockProperty blockDisplayBlockProperty;
    private TextDisplayAlignmentProperty textDisplayAlignmentProperty;
    private TextDisplayBackgroundProperty textDisplayBackgroundProperty;
    private TextDisplayLineWidthProperty textDisplayLineWidthProperty;
    private TextDisplaySeeThroughProperty textDisplaySeeThroughProperty;
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
        displayBillboardProperty = new DisplayBillboardProperty();
        plugin.getEntityPropertyRegistry().register(displayBillboardProperty);
        displayBrightnessProperty = new DisplayBrightnessProperty();
        plugin.getEntityPropertyRegistry().register(displayBrightnessProperty);
        displayWidthProperty = new DisplayWidthProperty();
        plugin.getEntityPropertyRegistry().register(displayWidthProperty);
        displayHeightProperty = new DisplayHeightProperty();
        plugin.getEntityPropertyRegistry().register(displayHeightProperty);
        itemDisplayTransformProperty = new ItemDisplayTransformProperty();
        plugin.getEntityPropertyRegistry().register(itemDisplayTransformProperty);
        blockDisplayBlockProperty = new BlockDisplayBlockProperty();
        plugin.getEntityPropertyRegistry().register(blockDisplayBlockProperty);
        if (TextDisplayAlignmentProperty.isSupported()) {
            textDisplayAlignmentProperty = new TextDisplayAlignmentProperty();
            plugin.getEntityPropertyRegistry().register(textDisplayAlignmentProperty);
        }
        if (TextDisplayBackgroundProperty.isSupported()) {
            textDisplayBackgroundProperty = new TextDisplayBackgroundProperty();
            plugin.getEntityPropertyRegistry().register(textDisplayBackgroundProperty);
        }
        textDisplayLineWidthProperty = new TextDisplayLineWidthProperty();
        plugin.getEntityPropertyRegistry().register(textDisplayLineWidthProperty);
        textDisplaySeeThroughProperty = new TextDisplaySeeThroughProperty();
        plugin.getEntityPropertyRegistry().register(textDisplaySeeThroughProperty);
        textDisplayShadowProperty = new TextDisplayShadowProperty();
        plugin.getEntityPropertyRegistry().register(textDisplayShadowProperty);
        textDisplayTextProperty = new TextDisplayTextProperty(textDisplayCapability);
        plugin.getEntityPropertyRegistry().register(textDisplayTextProperty);

        DisplaySessionListener listener = new DisplaySessionListener(this);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);

        plugin.getCommandManager().parserRegistry().registerParserSupplier(TypeToken.get(BlockData.class),
                p -> new BlockDataArgumentParser<>());

        CloudBrigadierManager<EasCommandSender, ?> brigadierManager = plugin.getCommandManager().brigadierManager();
        if (brigadierManager != null) {
            try {
                BlockDataArgumentParser.registerBrigadier(brigadierManager);
            } catch (Throwable e) {
                plugin.getLogger().warning("Failed to register Brigadier mappings for block data arguments");
            }
        }

        plugin.getAnnotationParser().parse(new DisplayCommands(this));
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

    public DisplayBillboardProperty getDisplayBillboardProperty() {
        return displayBillboardProperty;
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

    public ItemDisplayTransformProperty getItemDisplayTransformProperty() {
        return itemDisplayTransformProperty;
    }

    public BlockDisplayBlockProperty getBlockDisplayBlockProperty() {
        return blockDisplayBlockProperty;
    }

    public TextDisplayAlignmentProperty getTextDisplayAlignmentProperty() {
        return textDisplayAlignmentProperty;
    }

    public TextDisplayBackgroundProperty getTextDisplayBackgroundProperty() {
        return textDisplayBackgroundProperty;
    }

    public TextDisplayLineWidthProperty getTextDisplayLineWidthProperty() {
        return textDisplayLineWidthProperty;
    }

    public TextDisplaySeeThroughProperty getTextDisplaySeeThroughProperty() {
        return textDisplaySeeThroughProperty;
    }

    public TextDisplayShadowProperty getTextDisplayShadowProperty() {
        return textDisplayShadowProperty;
    }

    public TextDisplayTextProperty getTextDisplayTextProperty() {
        return textDisplayTextProperty;
    }
}
