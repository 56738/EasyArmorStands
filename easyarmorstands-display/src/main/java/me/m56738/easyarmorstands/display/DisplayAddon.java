package me.m56738.easyarmorstands.display;

import com.mojang.brigadier.arguments.StringArgumentType;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.element.EntityElementProviderRegistry;
import me.m56738.easyarmorstands.command.PropertyCommands;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.display.command.BlockDataArgumentParser;
import me.m56738.easyarmorstands.display.command.DisplayCommands;
import me.m56738.easyarmorstands.display.command.value.DisplayScaleAxisCommand;
import me.m56738.easyarmorstands.display.element.DisplayElementProvider;
import me.m56738.easyarmorstands.display.element.DisplayElementType;
import me.m56738.easyarmorstands.display.element.InteractionElementProvider;
import me.m56738.easyarmorstands.display.element.InteractionElementType;
import me.m56738.easyarmorstands.display.element.TextDisplayElementType;
import me.m56738.easyarmorstands.display.menu.DisplayBoxSlotType;
import me.m56738.easyarmorstands.display.menu.DisplaySpawnSlotType;
import me.m56738.easyarmorstands.display.menu.InteractionSpawnSlotType;
import me.m56738.easyarmorstands.display.property.display.DefaultDisplayPropertyTypes;
import me.m56738.easyarmorstands.lib.cloud.CommandManager;
import me.m56738.easyarmorstands.lib.cloud.brigadier.BrigadierManagerHolder;
import me.m56738.easyarmorstands.lib.cloud.brigadier.CloudBrigadierManager;
import me.m56738.easyarmorstands.lib.cloud.minecraft.extras.parser.TextColorParser;
import me.m56738.easyarmorstands.lib.geantyref.TypeToken;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.util.JOMLMapper;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;

public class DisplayAddon implements Addon {
    private final DisplayElementType<ItemDisplay> itemDisplayType;
    private final DisplayElementType<BlockDisplay> blockDisplayType;
    private final DisplayElementType<TextDisplay> textDisplayType;
    private final InteractionElementType interactionType;

    public DisplayAddon() {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();

        itemDisplayType = new DisplayElementType<>(EntityType.ITEM_DISPLAY, ItemDisplay.class);
        blockDisplayType = new DisplayElementType<>(EntityType.BLOCK_DISPLAY, BlockDisplay.class);
        textDisplayType = new TextDisplayElementType();
        interactionType = new InteractionElementType();

        EasyArmorStands.get().menuSlotTypeRegistry().register(new DisplaySpawnSlotType(Key.key("easyarmorstands", "spawn/item_display"), itemDisplayType));
        EasyArmorStands.get().menuSlotTypeRegistry().register(new DisplaySpawnSlotType(Key.key("easyarmorstands", "spawn/block_display"), blockDisplayType));
        EasyArmorStands.get().menuSlotTypeRegistry().register(new DisplaySpawnSlotType(Key.key("easyarmorstands", "spawn/text_display"), textDisplayType));
        EasyArmorStands.get().menuSlotTypeRegistry().register(new DisplayBoxSlotType());
        EasyArmorStands.get().menuSlotTypeRegistry().register(new InteractionSpawnSlotType(interactionType));

        new DefaultDisplayPropertyTypes(plugin.propertyTypeRegistry());
    }

    @SuppressWarnings("unchecked")
    private static void registerBrigadierMappings(CommandManager<EasCommandSender> commandManager, EasyArmorStandsPlugin plugin) {
        if (commandManager instanceof BrigadierManagerHolder) {
            BrigadierManagerHolder<EasCommandSender, ?> brigadierManagerHolder = (BrigadierManagerHolder<EasCommandSender, ?>) commandManager;
            if (brigadierManagerHolder.hasBrigadierManager()) {
                registerBrigadierMappings(plugin, brigadierManagerHolder);
            }
        }
    }

    private static void registerBrigadierMappings(EasyArmorStandsPlugin plugin, BrigadierManagerHolder<EasCommandSender, ?> brigadierManagerHolder) {
        CloudBrigadierManager<EasCommandSender, ?> brigadierManager = brigadierManagerHolder.brigadierManager();
        try {
            BlockDataArgumentParser.registerBrigadier(brigadierManager);
        } catch (Throwable e) {
            plugin.getLogger().warning("Failed to register Brigadier mappings for block data arguments");
        }
        brigadierManager.registerMapping(new TypeToken<TextColorParser<EasCommandSender>>() {
        }, builder -> builder.cloudSuggestions().toConstant(StringArgumentType.greedyString()));
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public void enable() {
        JOMLMapper mapper;
        try {
            mapper = new JOMLMapper();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new DisplayListener(mapper), plugin);

        EntityElementProviderRegistry registry = plugin.entityElementProviderRegistry();
        registry.register(new DisplayElementProvider<>(itemDisplayType));
        registry.register(new DisplayElementProvider<>(blockDisplayType));
        registry.register(new DisplayElementProvider<>(textDisplayType));
        registry.register(new InteractionElementProvider(interactionType));

        CommandManager<EasCommandSender> commandManager = plugin.getCommandManager();
        commandManager.parserRegistry().registerParserSupplier(TypeToken.get(BlockData.class),
                p -> new BlockDataArgumentParser<>());
        registerBrigadierMappings(commandManager, plugin);

        plugin.getAnnotationParser().parse(new DisplayCommands(this));

        for (Axis axis : Axis.values()) {
            PropertyCommands.register(commandManager, new DisplayScaleAxisCommand(axis));
        }
    }

    @Override
    public void disable() {
    }

    @Override
    public void reload() {
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
