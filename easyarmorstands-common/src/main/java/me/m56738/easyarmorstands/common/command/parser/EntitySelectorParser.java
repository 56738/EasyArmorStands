package me.m56738.easyarmorstands.common.command.parser;

import me.m56738.easyarmorstands.common.command.util.EntitySelector;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.parser.ParserDescriptor;

public class EntitySelectorParser implements ArgumentParser<CommandSource, EntitySelector> {
    public static ParserDescriptor<CommandSource, EntitySelector> entitySelectorParser() {
        return ParserDescriptor.of(new EntitySelectorParser(), EntitySelector.class);
    }

    @Override
    public ArgumentParseResult<EntitySelector> parse(CommandContext<CommandSource> commandContext, CommandInput commandInput) {
        // TODO
        return ArgumentParseResult.failure(new UnsupportedOperationException());
    }
}
