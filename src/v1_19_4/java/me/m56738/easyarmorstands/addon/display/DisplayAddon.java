package me.m56738.easyarmorstands.addon.display;

import cloud.commandframework.brigadier.CloudBrigadierManager;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.element.EntityElementProviderRegistry;
import me.m56738.easyarmorstands.node.v1_19_4.BlockDataArgumentParser;
import me.m56738.easyarmorstands.node.v1_19_4.DisplayElementProvider;
import me.m56738.easyarmorstands.node.v1_19_4.DisplayElementType;
import me.m56738.easyarmorstands.node.v1_19_4.DisplayRootNode;
import me.m56738.easyarmorstands.node.v1_19_4.TextDisplayElementType;
import me.m56738.easyarmorstands.session.v1_19_4.DisplaySessionListener;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;

public class DisplayAddon implements Addon {
    private JOMLMapper mapper;
    private DisplayElementType<ItemDisplay> itemDisplayType;
    private DisplayElementType<BlockDisplay> blockDisplayType;
    private DisplayElementType<TextDisplay> textDisplayType;

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
        DisplaySessionListener listener = new DisplaySessionListener(this);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        plugin.getServer().getPluginManager().registerEvents(new DisplayListener(mapper), plugin);

        itemDisplayType = new DisplayElementType<>(ItemDisplay.class, plugin.getCapability(EntityTypeCapability.class).getName(EntityType.ITEM_DISPLAY), DisplayRootNode::new);
        blockDisplayType = new DisplayElementType<>(BlockDisplay.class, plugin.getCapability(EntityTypeCapability.class).getName(EntityType.BLOCK_DISPLAY), DisplayRootNode::new);
        textDisplayType = new TextDisplayElementType(DisplayRootNode::new);

        EntityElementProviderRegistry registry = plugin.getEntityElementProviderRegistry();
        registry.register(new DisplayElementProvider<>(itemDisplayType));
        registry.register(new DisplayElementProvider<>(blockDisplayType));
        registry.register(new DisplayElementProvider<>(textDisplayType));

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

    public DisplayElementType<ItemDisplay> getItemDisplayType() {
        return itemDisplayType;
    }

    public DisplayElementType<BlockDisplay> getBlockDisplayType() {
        return blockDisplayType;
    }

    public DisplayElementType<TextDisplay> getTextDisplayType() {
        return textDisplayType;
    }
}
