package me.m56738.easyarmorstands.modded.command;

import me.m56738.easyarmorstands.command.parser.ArgumentParserProvider;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.util.MultipleEntitySelector;
import me.m56738.easyarmorstands.command.util.MultiplePlayerSelector;
import me.m56738.easyarmorstands.command.util.SingleEntitySelector;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.entity.ModdedEntity;
import me.m56738.easyarmorstands.platform.modded.entity.ModdedPlayer;
import me.m56738.easyarmorstands.platform.modded.world.ModdedWorld;
import me.m56738.easyarmorstands.platform.util.Location;
import me.m56738.easyarmorstands.platform.util.MappedCollection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.incendo.cloud.minecraft.modded.ModdedCommandContextKeys;
import org.incendo.cloud.minecraft.modded.parser.VanillaArgumentParsers;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ParserDescriptor;
import org.joml.Vector3d;

import java.util.Collection;

import static java.util.concurrent.CompletableFuture.completedFuture;

public class ModdedArgumentParserProvider implements ArgumentParserProvider {
    private final ModdedPlatform platform;

    public ModdedArgumentParserProvider(ModdedPlatform platform) {
        this.platform = platform;
    }

    @Override
    public <C extends EasCommandSender> ParserDescriptor<C, Location> locationParser() {
        return VanillaArgumentParsers.<C>vec3Parser(true).flatMapSuccess(Location.class,
                (context, coordinates) -> {
                    CommandSourceStack stack = (CommandSourceStack) context.get(ModdedCommandContextKeys.SHARED_SUGGESTION_PROVIDER);
                    ServerLevel level = stack.getLevel();
                    Vec3 position = coordinates.wrappedCoordinates().getPosition(stack);
                    Vec2 rotation = coordinates.wrappedCoordinates().getRotation(stack);
                    return ArgumentParseResult.successFuture(Location.of(
                            ModdedWorld.fromNative(platform, level),
                            new Vector3d(position.x, position.y, position.z),
                            rotation.y, rotation.x));
                });
    }

    @Override
    public <C extends EasCommandSender> ParserDescriptor<C, SingleEntitySelector> singleEntitySelector() {
        return VanillaArgumentParsers.<C>singleEntitySelectorParser().mapSuccess(SingleEntitySelector.class,
                (_, selector) -> {
                    Entity entity = ModdedEntity.fromNative(platform, selector.single());
                    return completedFuture(new SingleEntitySelector(entity));
                });
    }

    @Override
    public <C extends EasCommandSender> ParserDescriptor<C, MultipleEntitySelector> multipleEntitySelector() {
        return VanillaArgumentParsers.<C>multipleEntitySelectorParser().mapSuccess(MultipleEntitySelector.class,
                (_, selector) -> {
                    Collection<Entity> entities = new MappedCollection<>(selector.values(),
                            e -> ModdedEntity.fromNative(platform, e));
                    return completedFuture(new MultipleEntitySelector(entities));
                });
    }

    @Override
    public <C extends EasCommandSender> ParserDescriptor<C, MultiplePlayerSelector> multiplePlayerSelector() {
        return VanillaArgumentParsers.<C>multiplePlayerSelectorParser().mapSuccess(MultiplePlayerSelector.class,
                (_, selector) -> {
                    Collection<Player> entities = new MappedCollection<>(selector.values(),
                            e -> ModdedPlayer.fromNative(platform, e));
                    return completedFuture(new MultiplePlayerSelector(entities));
                });
    }
}
