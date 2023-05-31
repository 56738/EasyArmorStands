package me.m56738.easyarmorstands.property.v1_19_4.display;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.EnumArgument;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.property.ToggleEntityProperty;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Display;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Locale;

public class DisplayBillboardProperty extends ToggleEntityProperty<Display, Display.Billboard> {
    @Override
    public Display.Billboard getValue(Display entity) {
        return entity.getBillboard();
    }

    @Override
    public TypeToken<Display.Billboard> getValueType() {
        return TypeToken.get(Display.Billboard.class);
    }

    @Override
    public void setValue(Display entity, Display.Billboard value) {
        entity.setBillboard(value);
    }

    @Override
    public @NotNull String getName() {
        return "billboard";
    }

    @Override
    public @NotNull Class<Display> getEntityType() {
        return Display.class;
    }

    @Override
    public ArgumentParser<EasCommandSender, Display.Billboard> getArgumentParser() {
        return new EnumArgument.EnumParser<>(Display.Billboard.class);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("billboard");
    }

    @Override
    public @NotNull Component getValueName(Display.Billboard value) {
        return Component.text(value.name().toLowerCase(Locale.ROOT),
                value == Display.Billboard.FIXED ? NamedTextColor.RED : NamedTextColor.GREEN);
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.display.billboard";
    }

    @Override
    public Display.Billboard getNextValue(Display entity) {
        Display.Billboard[] values = Display.Billboard.values();
        int i;
        for (i = 0; i < values.length; i++) {
            if (values[i] == entity.getBillboard()) {
                break;
            }
        }
        return values[(i + 1) % values.length];
    }

    @Override
    public ItemStack createToggleButton(Display entity) {
        return Util.createItem(
                ItemType.IRON_BARS,
                Component.text("Toggle billboard", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Currently ", NamedTextColor.GRAY)
                                .append(getValueName(getValue(entity)))
                                .append(Component.text(".")),
                        Component.text("Changes whether the", NamedTextColor.GRAY),
                        Component.text("entity rotates with", NamedTextColor.GRAY),
                        Component.text("the player camera.", NamedTextColor.GRAY)
                )
        );
    }
}
