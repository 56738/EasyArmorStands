package me.m56738.easyarmorstands.display.command;

import com.mojang.brigadier.arguments.ArgumentType;
import io.leangen.geantyref.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.BlockData;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.brigadier.CloudBrigadierManager;
import org.incendo.cloud.bukkit.internal.CommandBuildContextSupplier;
import org.incendo.cloud.bukkit.internal.MinecraftArgumentTypes;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;

import java.lang.reflect.Constructor;

public class BlockDataArgumentParser<C> implements ArgumentParser<C, BlockData> {
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
    public @NonNull ArgumentParseResult<@NonNull BlockData> parse(@NonNull CommandContext<@NonNull C> ctx, @NonNull CommandInput commandInput) {
        String input = commandInput.peekString();
        BlockData data;
        try {
            data = Bukkit.createBlockData(input);
        } catch (IllegalArgumentException e) {
            return ArgumentParseResult.failure(e);
        }

        commandInput.readString();
        return ArgumentParseResult.success(data);
    }
}
