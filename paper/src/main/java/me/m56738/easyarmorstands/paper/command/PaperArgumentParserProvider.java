package me.m56738.easyarmorstands.paper.command;

import me.m56738.easyarmorstands.command.parser.ArgumentParserProvider;
import me.m56738.easyarmorstands.command.util.MultipleEntitySelector;
import me.m56738.easyarmorstands.command.util.SingleEntitySelector;
import me.m56738.easyarmorstands.platform.paper.PaperAdapter;
import me.m56738.easyarmorstands.platform.paper.entity.PaperEntity;
import me.m56738.easyarmorstands.platform.util.Location;
import me.m56738.easyarmorstands.platform.util.MappedCollection;
import org.incendo.cloud.bukkit.parser.location.LocationParser;
import org.incendo.cloud.bukkit.parser.selector.MultipleEntitySelectorParser;
import org.incendo.cloud.bukkit.parser.selector.SingleEntitySelectorParser;
import org.incendo.cloud.parser.ParserDescriptor;

import java.util.concurrent.CompletableFuture;

public class PaperArgumentParserProvider implements ArgumentParserProvider {
    @Override
    public <C> ParserDescriptor<C, Location> locationParser() {
        return LocationParser.<C>locationParser().mapSuccess(Location.class,
                (context, location) ->
                        CompletableFuture.completedFuture(PaperAdapter.fromNative(location)));
    }

    @Override
    public <C> ParserDescriptor<C, SingleEntitySelector> singleEntitySelector() {
        return SingleEntitySelectorParser.<C>singleEntitySelectorParser().mapSuccess(SingleEntitySelector.class,
                (context, selector) ->
                        CompletableFuture.completedFuture(new SingleEntitySelector(
                                PaperEntity.fromNative(selector.single()))));
    }

    @Override
    public <C> ParserDescriptor<C, MultipleEntitySelector> multipleEntitySelector() {
        return MultipleEntitySelectorParser.<C>multipleEntitySelectorParser().mapSuccess(MultipleEntitySelector.class,
                (context, selector) ->
                        CompletableFuture.completedFuture(new MultipleEntitySelector(
                                new MappedCollection<>(selector.values(), PaperEntity::fromNative))));
    }
}
