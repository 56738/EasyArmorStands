package me.m56738.easyarmorstands.property.v1_19_4.display;

import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.compound.ArgumentPair;
import cloud.commandframework.types.tuples.Pair;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.command.EasCommandSender;
import me.m56738.easyarmorstands.property.EntityProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DisplayBrightnessProperty implements EntityProperty<Display, Optional<Display.Brightness>> {
    @Override
    public Optional<Display.Brightness> getValue(Display entity) {
        return Optional.ofNullable(entity.getBrightness());
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
    public @Nullable CommandArgument<EasCommandSender, Optional<Display.Brightness>> getArgument() {
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
        });
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
        return "easyarmorstands.property.brightness";
    }
}
