package me.m56738.easyarmorstands.display.command;

import com.mojang.brigadier.arguments.ArgumentType;
import me.m56738.easyarmorstands.lib.cloud.brigadier.CloudBrigadierManager;
import me.m56738.easyarmorstands.lib.cloud.bukkit.internal.CommandBuildContextSupplier;
import me.m56738.easyarmorstands.lib.cloud.bukkit.internal.MinecraftArgumentTypes;
import me.m56738.easyarmorstands.lib.cloud.context.CommandContext;
import me.m56738.easyarmorstands.lib.cloud.context.CommandInput;
import me.m56738.easyarmorstands.lib.cloud.parser.ArgumentParseResult;
import me.m56738.easyarmorstands.lib.cloud.parser.ArgumentParser;
import me.m56738.easyarmorstands.lib.geantyref.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.BlockData;
import org.checkerframework.checker.nullness.qual.NonNull;

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
