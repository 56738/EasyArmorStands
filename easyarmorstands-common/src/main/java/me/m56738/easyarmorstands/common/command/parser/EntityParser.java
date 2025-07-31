package me.m56738.easyarmorstands.common.command.parser;

import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.parser.ParserDescriptor;

public class EntityParser implements ArgumentParser<CommandSource, Entity> {
    public static ParserDescriptor<CommandSource, Entity> entityParser() {
        return ParserDescriptor.of(new EntityParser(), Entity.class);
    }

    @Override
    public ArgumentParseResult<Entity> parse(CommandContext<CommandSource> commandContext, CommandInput commandInput) {
        // TODO
        return ArgumentParseResult.failure(new UnsupportedOperationException());
    }
}
