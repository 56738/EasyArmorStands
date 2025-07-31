package me.m56738.easyarmorstands.common.command.parser;

import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.parser.ParserDescriptor;

public class LocationParser implements ArgumentParser<CommandSource, Location> {
    public static ParserDescriptor<CommandSource, Location> locationParser() {
        return ParserDescriptor.of(new LocationParser(), Location.class);
    }

    @Override
    public ArgumentParseResult<Location> parse(CommandContext<CommandSource> commandContext, CommandInput commandInput) {
        // TODO
        return ArgumentParseResult.failure(new UnsupportedOperationException());
    }
}
