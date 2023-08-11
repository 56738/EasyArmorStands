package me.m56738.easyarmorstands.addon.display;

import cloud.commandframework.brigadier.CloudBrigadierManager;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.capability.textdisplay.TextDisplayCapability;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.node.v1_19_4.BlockDataArgumentParser;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplayAlignmentProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplayBackgroundProperty;
import me.m56738.easyarmorstands.session.v1_19_4.DisplaySessionListener;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.block.data.BlockData;

public class DisplayAddon implements Addon {
    private JOMLMapper mapper;

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

        // new DisplayTranslationProperty(entity, mapper)
        // new DisplayLeftRotationProperty(entity, mapper)
        // new DisplayScaleProperty(entity, mapper)
        // new DisplayRightRotationProperty(entity, mapper)
        // new DisplayBillboardProperty(entity)
        // new DisplayBrightnessProperty(entity)
        // new DisplayWidthProperty(entity)
        // new DisplayHeightProperty(entity)
        // new ItemDisplayItemProperty(entity)
        // new ItemDisplayTransformProperty(entity);
        // new BlockDisplayBlockProperty(entity)
        if (TextDisplayAlignmentProperty.isSupported()) {
            // new TextDisplayAlignmentProperty(entity)
        }
        if (TextDisplayBackgroundProperty.isSupported()) {
            // new TextDisplayBackgroundProperty(entity)
        }
        // new TextDisplayLineWidthProperty(entity)
        // new TextDisplaySeeThroughProperty(entity)
        // new TextDisplayShadowProperty(entity)
        // new TextDisplayTextProperty(entity, textDisplayCapability)

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
}
