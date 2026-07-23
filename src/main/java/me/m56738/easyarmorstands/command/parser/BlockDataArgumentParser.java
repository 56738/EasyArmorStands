package me.m56738.easyarmorstands.command.parser;

import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.platform.block.BlockData;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.parser.ParserDescriptor;

public class BlockDataArgumentParser<C> implements ArgumentParser<C, BlockData> {
    public static <C> ParserDescriptor<C, BlockData> blockDataParser() {
        return ParserDescriptor.of(new BlockDataArgumentParser<>(), BlockData.class);
    }

    @Override
    public @NonNull ArgumentParseResult<@NonNull BlockData> parse(@NonNull CommandContext<@NonNull C> ctx, @NonNull CommandInput commandInput) {
        Platform platform = ctx.inject(Platform.class).orElseThrow();

        String input = commandInput.peekString();
        BlockData data;
        try {
            data = platform.parseBlockData(input);
        } catch (IllegalArgumentException e) {
            return ArgumentParseResult.failure(e);
        }

        commandInput.readString();
        return ArgumentParseResult.success(data);
    }
}
