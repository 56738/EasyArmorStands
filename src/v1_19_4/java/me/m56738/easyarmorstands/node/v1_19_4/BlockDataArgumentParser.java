package me.m56738.easyarmorstands.node.v1_19_4;

import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.brigadier.CloudBrigadierManager;
import cloud.commandframework.bukkit.internal.CommandBuildContextSupplier;
import cloud.commandframework.bukkit.internal.MinecraftArgumentTypes;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.exceptions.parsing.NoInputProvidedException;
import com.mojang.brigadier.arguments.ArgumentType;
import io.leangen.geantyref.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.BlockData;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Constructor;
import java.util.Queue;

public class BlockDataArgumentParser<C> implements ArgumentParser<C, BlockData> {
    @SuppressWarnings("UnstableApiUsage")
    public static <C> void registerBrigadier(CloudBrigadierManager<C, ?> brigadierManager) {
        Constructor<?> ctr = MinecraftArgumentTypes.getClassByKey(NamespacedKey.minecraft("block_state"))
                .getDeclaredConstructors()[0];
        brigadierManager.registerMapping(
                new TypeToken<BlockDataArgumentParser<C>>() {
                },
                builder -> builder.to(a -> {
                    final Object[] args = ctr.getParameterCount() == 1
                            ? new Object[]{CommandBuildContextSupplier.commandBuildContext()}
                            : new Object[]{};
                    try {
                        return (ArgumentType<?>) ctr.newInstance(args);
                    } catch (final ReflectiveOperationException e) {
                        throw new RuntimeException(e);
                    }
                })
        );
    }

    @Override
    public @NonNull ArgumentParseResult<@NonNull BlockData> parse(@NonNull CommandContext<@NonNull C> ctx, @NonNull Queue<@NonNull String> inputQueue) {
        String input = inputQueue.peek();
        if (input == null) {
            return ArgumentParseResult.failure(new NoInputProvidedException(getClass(), ctx));
        }

        BlockData data;
        try {
            data = Bukkit.createBlockData(input);
        } catch (IllegalArgumentException e) {
            return ArgumentParseResult.failure(e);
        }

        inputQueue.remove();
        return ArgumentParseResult.success(data);
    }
}
