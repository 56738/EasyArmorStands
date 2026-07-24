package me.m56738.easyarmorstands.command.parser;

import me.m56738.easyarmorstands.command.util.MultipleEntitySelector;
import me.m56738.easyarmorstands.command.util.MultiplePlayerSelector;
import me.m56738.easyarmorstands.command.util.SingleEntitySelector;
import me.m56738.easyarmorstands.platform.util.Location;
import org.incendo.cloud.parser.ParserDescriptor;

public interface ArgumentParserProvider {
    <C> ParserDescriptor<C, Location> locationParser();

    <C> ParserDescriptor<C, SingleEntitySelector> singleEntitySelector();

    <C> ParserDescriptor<C, MultipleEntitySelector> multipleEntitySelector();

    <C> ParserDescriptor<C, MultiplePlayerSelector> multiplePlayerSelector();
}
