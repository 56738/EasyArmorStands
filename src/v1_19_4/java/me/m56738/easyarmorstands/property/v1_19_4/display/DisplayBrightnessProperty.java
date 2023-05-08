package me.m56738.easyarmorstands.property.v1_19_4.display;

import cloud.commandframework.arguments.compound.ArgumentPair;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.types.tuples.Pair;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.command.EasCommandSender;
import me.m56738.easyarmorstands.property.EntityProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DisplayBrightnessProperty implements EntityProperty<Display, Optional<Display.Brightness>> {
    @Override
    public Optional<Display.Brightness> getValue(Display entity) {
        return Optional.ofNullable(entity.getBrightness());
    }

    @Override
    public TypeToken<Optional<Display.Brightness>> getValueType() {
        return new TypeToken<Optional<Display.Brightness>>() {
        };
    }

    @Override
    public void setValue(Display entity, Optional<Display.Brightness> value) {
        entity.setBrightness(value.orElse(null));
    }

    @Override
    public @NotNull String getName() {
        return "brightness";
    }

    @Override
    public @NotNull Class<Display> getEntityType() {
        return Display.class;
    }

    @Override
    public ArgumentParser<EasCommandSender, Optional<Display.Brightness>> getArgumentParser() {
        return ArgumentPair.of(EasyArmorStands.getInstance().getCommandManager(),
                getName(),
                Pair.of("block", "sky"),
                Pair.of(int.class, int.class)
        ).withMapper(new TypeToken<Optional<Display.Brightness>>() {
        }, (sender, pair) -> {
            int sky = pair.getFirst();
            int block = pair.getSecond();
            if (sky >= 0 && sky <= 15 && block >= 0 && block <= 15) {
                return Optional.of(new Display.Brightness(block, sky));
            } else {
                return Optional.empty();
            }
        }).getParser();
    }

    @Override
    public boolean hasDefaultValue() {
        return true;
    }

    @Override
    public @Nullable Optional<Display.Brightness> getDefaultValue(@NonNull CommandContext<EasCommandSender> ctx) {
        CommandSender sender = ctx.getSender().get();
        if (sender instanceof Player) {
            Block block = ((Player) sender).getLocation().getBlock();
            return Optional.of(new Display.Brightness(block.getLightFromBlocks(), block.getLightFromSky()));
        }
        return Optional.empty();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("brightness");
    }

    @Override
    public @NotNull Component getValueName(Optional<Display.Brightness> value) {
        if (value.isPresent()) {
            Display.Brightness brightness = value.get();
            return Component.text()
                    .content("Block: ")
                    .append(Component.text(brightness.getBlockLight()))
                    .append(Component.text(", "))
                    .append(Component.text("Sky: "))
                    .append(Component.text(brightness.getSkyLight()))
                    .build();
        } else {
            return Component.text("default");
        }
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.display.brightness";
    }
}
