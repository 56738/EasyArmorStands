package me.m56738.easyarmorstands.property.armorstand;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.EnumArgument;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.command.ArmorStandSize;
import me.m56738.easyarmorstands.command.EasCommandSender;
import me.m56738.easyarmorstands.property.ToggleEntityProperty;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ArmorStandSizeProperty extends ToggleEntityProperty<ArmorStand, ArmorStandSize> {
    @Override
    public ArmorStandSize getValue(ArmorStand entity) {
        return entity.isSmall() ? ArmorStandSize.SMALL : ArmorStandSize.NORMAL;
    }

    @Override
    public TypeToken<ArmorStandSize> getValueType() {
        return TypeToken.get(ArmorStandSize.class);
    }

    @Override
    public void setValue(ArmorStand entity, ArmorStandSize value) {
        entity.setSmall(value.isSmall());
    }

    @Override
    public @NotNull String getName() {
        return "size";
    }

    @Override
    public @NotNull Class<ArmorStand> getEntityType() {
        return ArmorStand.class;
    }

    @Override
    public ArgumentParser<EasCommandSender, ArmorStandSize> getArgumentParser() {
        return new EnumArgument.EnumParser<>(ArmorStandSize.class);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("size");
    }

    @Override
    public @NotNull Component getValueName(ArmorStandSize value) {
        switch (value) {
            case SMALL:
                return Component.text("small", NamedTextColor.BLUE);
            case NORMAL:
                return Component.text("large", NamedTextColor.GREEN);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.size";
    }

    @Override
    public ArmorStandSize getNextValue(ArmorStand entity) {
        return entity.isSmall() ? ArmorStandSize.NORMAL : ArmorStandSize.SMALL;
    }

    @Override
    public ItemStack createToggleButton(ArmorStand entity) {
        return Util.createItem(
                ItemType.BONE_MEAL,
                Component.text("Toggle size", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Currently ", NamedTextColor.GRAY)
                                .append(getValueName(getValue(entity)))
                                .append(Component.text(".")),
                        Component.text("Changes the size of", NamedTextColor.GRAY),
                        Component.text("the armor stand.", NamedTextColor.GRAY)
                )
        );
    }
}
