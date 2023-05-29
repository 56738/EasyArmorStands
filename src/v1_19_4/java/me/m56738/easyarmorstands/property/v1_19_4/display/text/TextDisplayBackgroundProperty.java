package me.m56738.easyarmorstands.property.v1_19_4.display.text;

import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.exceptions.parsing.NoInputProvidedException;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.property.ToggleEntityProperty;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@SuppressWarnings("deprecation") // presence checked in isSupported
public class TextDisplayBackgroundProperty extends ToggleEntityProperty<TextDisplay, Optional<Color>> {
    public static boolean isSupported() {
        try {
            TextDisplay.class.getMethod("getBackgroundColor");
            TextDisplay.class.getMethod("setBackgroundColor", Color.class);
            TextDisplay.class.getMethod("isDefaultBackground");
            TextDisplay.class.getMethod("setDefaultBackground", boolean.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Optional<Color> getValue(TextDisplay entity) {
        if (entity.isDefaultBackground()) {
            return Optional.empty();
        }
        Color color = entity.getBackgroundColor();
        if (color == null) {
            color = Color.WHITE;
        }
        return Optional.of(color);
    }

    @Override
    public TypeToken<Optional<Color>> getValueType() {
        return new TypeToken<Optional<Color>>() {
        };
    }

    @Override
    public void setValue(TextDisplay entity, Optional<Color> value) {
        if (value.isPresent()) {
            entity.setDefaultBackground(false);
            entity.setBackgroundColor(value.get());
        } else {
            entity.setDefaultBackground(true);
            entity.setBackgroundColor(null);
        }
    }

    @Override
    public @NotNull String getName() {
        return "background";
    }

    @Override
    public @NotNull Class<TextDisplay> getEntityType() {
        return TextDisplay.class;
    }

    @Override
    public ArgumentParser<EasCommandSender, Optional<Color>> getArgumentParser() {
        return new BackgroundParser<>();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("background");
    }

    @Override
    public @NotNull Component getValueName(Optional<Color> value) {
        if (value.isPresent()) {
            Color color = value.get();
            if (color.getAlpha() == 0) {
                return Component.text("none", NamedTextColor.WHITE);
            }
            TextColor textColor = TextColor.color(color.asRGB());
            return Component.text(textColor.asHexString(), textColor);
        } else {
            return Component.text("default", NamedTextColor.DARK_GRAY);
        }
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.display.text.background";
    }

    @Override
    public Optional<Color> getNextValue(TextDisplay entity) {
        if (entity.isDefaultBackground()) {
            return Optional.of(Color.fromARGB(0, 0, 0, 0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public ItemStack createToggleButton(TextDisplay entity) {
        return Util.createItem(
                ItemType.STONE_SLAB,
                Component.text("Toggle background", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Currently ", NamedTextColor.GRAY)
                                .append(getValueName(getValue(entity)))
                                .append(Component.text(".")),
                        Component.text("Changes the background", NamedTextColor.GRAY),
                        Component.text("behind the text.", NamedTextColor.GRAY),
                        Component.text("Use ", NamedTextColor.GRAY)
                                .append(Component.text("/eas background", NamedTextColor.BLUE)),
                        Component.text("to set the color.", NamedTextColor.GRAY)
                )
        );
    }

    public static class BackgroundParser<C> implements ArgumentParser<C, Optional<Color>> {
        @Override
        public @NonNull ArgumentParseResult<@NonNull Optional<Color>> parse(@NonNull CommandContext<@NonNull C> commandContext, @NonNull Queue<@NonNull String> inputQueue) {
            String input = inputQueue.peek();
            if (input == null) {
                return ArgumentParseResult.failure(new NoInputProvidedException(getClass(), commandContext));
            }

            if (input.equals("none")) {
                inputQueue.remove();
                return ArgumentParseResult.success(Optional.of(Color.fromARGB(0, 0, 0, 0)));
            } else if (input.equals("default")) {
                inputQueue.remove();
                return ArgumentParseResult.success(Optional.empty());
            }

            NamedTextColor namedTextColor = NamedTextColor.NAMES.value(input);
            if (namedTextColor != null) {
                inputQueue.remove();
                return ArgumentParseResult.success(Optional.of(Color.fromRGB(namedTextColor.value())));
            }

            try {
                int rgb = Integer.parseInt(input, 16);
                Color color;
                if (input.length() == 6) {
                    color = Color.fromRGB(rgb);
                } else if (input.length() == 8) {
                    color = Color.fromARGB(rgb);
                } else {
                    return ArgumentParseResult.failure(new IllegalArgumentException("Invalid color"));
                }
                inputQueue.remove();
                return ArgumentParseResult.success(Optional.of(color));
            } catch (IllegalArgumentException e) {
                return ArgumentParseResult.failure(e);
            }
        }

        @Override
        public @NonNull List<@NonNull String> suggestions(@NonNull CommandContext<C> commandContext, @NonNull String input) {
            List<String> suggestions = new ArrayList<>();
            suggestions.add("none");
            suggestions.add("default");
            suggestions.addAll(NamedTextColor.NAMES.keys());
            return suggestions;
        }
    }
}
