package me.m56738.easyarmorstands.command.parser;

import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.util.MultipleEntitySelector;
import me.m56738.easyarmorstands.command.util.MultiplePlayerSelector;
import me.m56738.easyarmorstands.command.util.SingleEntitySelector;
import me.m56738.easyarmorstands.platform.util.Location;
import org.incendo.cloud.parser.ParserDescriptor;

public interface ArgumentParserProvider {
    <C extends EasCommandSender> ParserDescriptor<C, Location> locationParser();

    <C extends EasCommandSender> ParserDescriptor<C, SingleEntitySelector> singleEntitySelector();

    <C extends EasCommandSender> ParserDescriptor<C, MultipleEntitySelector> multipleEntitySelector();

    <C extends EasCommandSender> ParserDescriptor<C, MultiplePlayerSelector> multiplePlayerSelector();
}
