package me.m56738.easyarmorstands.command.parser;

import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;

public class BlockDataArgumentParser<C> implements ArgumentParser<C, BlockData> {
    @Override
    public @NonNull ArgumentParseResult<@NonNull BlockData> parse(@NonNull CommandContext<@NonNull C> ctx, @NonNull CommandInput commandInput) {
        String input = commandInput.peekString();
        BlockData data;
        try {
            data = Bukkit.createBlockData(input);
        } catch (IllegalArgumentException e) {
            return ArgumentParseResult.failure(e);
        }

        commandInput.readString();
        return ArgumentParseResult.success(data);
    }
}
