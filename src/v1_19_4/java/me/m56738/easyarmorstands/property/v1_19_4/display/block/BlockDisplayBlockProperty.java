package me.m56738.easyarmorstands.property.v1_19_4.display.block;

import cloud.commandframework.arguments.parser.ArgumentParser;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.node.v1_19_4.BlockDataArgumentParser;
import me.m56738.easyarmorstands.property.LegacyEntityPropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockDisplayBlockProperty implements LegacyEntityPropertyType<BlockDisplay, BlockData> {
    @Override
    public BlockData getValue(BlockDisplay entity) {
        return entity.getBlock();
    }

    @Override
    public TypeToken<BlockData> getValueType() {
        return TypeToken.get(BlockData.class);
    }

    @Override
    public void setValue(BlockDisplay entity, BlockData value) {
        entity.setBlock(value);
    }

    @Override
    public @NotNull String getName() {
        return "block";
    }

    @Override
    public @NotNull Class<BlockDisplay> getEntityType() {
        return BlockDisplay.class;
    }

    @Override
    public ArgumentParser<EasCommandSender, BlockData> getArgumentParser() {
        return new BlockDataArgumentParser<>();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("block");
    }

    @Override
    public @NotNull Component getValueName(BlockData value) {
        return Component.text(value.getAsString());
    }

    @Override
    public @NotNull String getValueClipboardContent(BlockData value) {
        return value.getAsString(true);
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.display.block";
    }
}
