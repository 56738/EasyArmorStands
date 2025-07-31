package me.m56738.easyarmorstands.common.command.parser;

import me.m56738.easyarmorstands.api.platform.world.BlockData;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.parser.ParserDescriptor;

public class BlockDataParser implements ArgumentParser<CommandSource, BlockData> {
    public static ParserDescriptor<CommandSource, BlockData> blockDataParser() {
        return ParserDescriptor.of(new BlockDataParser(), BlockData.class);
    }

    @Override
    public ArgumentParseResult<BlockData> parse(CommandContext<CommandSource> commandContext, CommandInput commandInput) {
        CommonPlatform platform = commandContext.inject(CommonPlatform.class).orElseThrow();
        BlockData blockData;
        try {
            blockData = platform.createBlockData(commandInput.readString());
        } catch (IllegalArgumentException e) {
            return ArgumentParseResult.failure(e);
        }
        return ArgumentParseResult.success(blockData);
    }
}
