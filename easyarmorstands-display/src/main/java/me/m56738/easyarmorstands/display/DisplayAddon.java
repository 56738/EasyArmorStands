package me.m56738.easyarmorstands.display;

import com.mojang.brigadier.arguments.StringArgumentType;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.element.EntityElementProviderRegistry;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.display.command.BlockDataArgumentParser;
import me.m56738.easyarmorstands.display.command.DisplayCommands;
import me.m56738.easyarmorstands.display.editor.DisplaySessionListener;
import me.m56738.easyarmorstands.display.element.DisplayElementProvider;
import me.m56738.easyarmorstands.display.element.DisplayElementType;
import me.m56738.easyarmorstands.display.element.InteractionElementProvider;
import me.m56738.easyarmorstands.display.element.InteractionElementType;
import me.m56738.easyarmorstands.display.element.TextDisplayElementType;
import me.m56738.easyarmorstands.display.menu.DisplayBoxSlotType;
import me.m56738.easyarmorstands.display.menu.DisplaySpawnSlotType;
import me.m56738.easyarmorstands.display.menu.InteractionSpawnSlotType;
import me.m56738.easyarmorstands.display.property.display.DefaultDisplayPropertyTypes;
import me.m56738.easyarmorstands.util.JOMLMapper;
import net.kyori.adventure.key.Key;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;
import org.incendo.cloud.brigadier.CloudBrigadierManager;
import org.incendo.cloud.minecraft.extras.parser.TextColorParser;

public class DisplayAddon {
    private final DisplayElementType<ItemDisplay> itemDisplayType;
    private final DisplayElementType<BlockDisplay> blockDisplayType;
    private final DisplayElementType<TextDisplay> textDisplayType;
    private final InteractionElementType interactionType;

    public DisplayAddon() {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();

        JOMLMapper mapper;
        try {
            mapper = new JOMLMapper();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        new DefaultDisplayPropertyTypes(plugin.propertyTypeRegistry());

        DisplaySessionListener listener = new DisplaySessionListener();
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        plugin.getServer().getPluginManager().registerEvents(new DisplayListener(mapper), plugin);

        itemDisplayType = new DisplayElementType<>(EntityType.ITEM_DISPLAY, ItemDisplay.class);
        blockDisplayType = new DisplayElementType<>(EntityType.BLOCK_DISPLAY, BlockDisplay.class);
        textDisplayType = new TextDisplayElementType();
        interactionType = new InteractionElementType();

        EasyArmorStands.get().menuSlotTypeRegistry().register(new DisplaySpawnSlotType(Key.key("easyarmorstands", "spawn/item_display"), itemDisplayType));
        EasyArmorStands.get().menuSlotTypeRegistry().register(new DisplaySpawnSlotType(Key.key("easyarmorstands", "spawn/block_display"), blockDisplayType));
        EasyArmorStands.get().menuSlotTypeRegistry().register(new DisplaySpawnSlotType(Key.key("easyarmorstands", "spawn/text_display"), textDisplayType));
        EasyArmorStands.get().menuSlotTypeRegistry().register(new DisplayBoxSlotType());
        EasyArmorStands.get().menuSlotTypeRegistry().register(new InteractionSpawnSlotType(interactionType));

        EntityElementProviderRegistry registry = plugin.entityElementProviderRegistry();
        registry.register(new DisplayElementProvider<>(itemDisplayType));
        registry.register(new DisplayElementProvider<>(blockDisplayType));
        registry.register(new DisplayElementProvider<>(textDisplayType));
        registry.register(new InteractionElementProvider(interactionType));

        plugin.getCommandManager().parserRegistry().registerParserSupplier(TypeToken.get(BlockData.class),
                p -> new BlockDataArgumentParser<>());

        if (plugin.getCommandManager().hasBrigadierManager()) {
            CloudBrigadierManager<EasCommandSender, ?> brigadierManager = plugin.getCommandManager().brigadierManager();
            try {
                BlockDataArgumentParser.registerBrigadier(brigadierManager);
            } catch (Throwable e) {
                plugin.getLogger().warning("Failed to register Brigadier mappings for block data arguments");
            }
            brigadierManager.registerMapping(new TypeToken<TextColorParser<EasCommandSender>>() {
            }, builder -> builder.cloudSuggestions().toConstant(StringArgumentType.greedyString()));
        }

        plugin.getAnnotationParser().parse(new DisplayCommands(this));
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

    public InteractionElementType getInteractionType() {
        return interactionType;
    }
}
