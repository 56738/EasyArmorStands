package me.m56738.easyarmorstands.addon.display;

import cloud.commandframework.brigadier.CloudBrigadierManager;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.editor.EntityObjectProviderRegistry;
import me.m56738.easyarmorstands.node.v1_19_4.BlockDataArgumentParser;
import me.m56738.easyarmorstands.node.v1_19_4.DisplayObjectProvider;
import me.m56738.easyarmorstands.node.v1_19_4.DisplayRootNode;
import me.m56738.easyarmorstands.node.v1_19_4.DisplayRootNodeFactory;
import me.m56738.easyarmorstands.session.v1_19_4.DisplaySessionListener;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;

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
        DisplaySessionListener listener = new DisplaySessionListener(this);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        plugin.getServer().getPluginManager().registerEvents(new DisplayListener(mapper), plugin);

        EntityObjectProviderRegistry registry = plugin.getEntityObjectProviderRegistry();
        register(registry, ItemDisplay.class, DisplayRootNode::new);
        register(registry, BlockDisplay.class, DisplayRootNode::new);
        register(registry, TextDisplay.class, DisplayRootNode::new);

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

    private <T extends Display> void register(EntityObjectProviderRegistry registry, Class<T> type, DisplayRootNodeFactory<T> factory) {
        registry.register(new DisplayObjectProvider<>(type, factory));
    }

    public JOMLMapper getMapper() {
        return mapper;
    }
}
