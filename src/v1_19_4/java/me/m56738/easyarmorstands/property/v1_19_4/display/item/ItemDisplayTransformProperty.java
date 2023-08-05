package me.m56738.easyarmorstands.property.v1_19_4.display.item;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.EnumArgument;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.property.ToggleEntityProperty;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.ItemDisplay.ItemDisplayTransform;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Locale;

public class ItemDisplayTransformProperty extends ToggleEntityProperty<ItemDisplay, ItemDisplayTransform> {
    @Override
    public ItemDisplayTransform getValue(ItemDisplay entity) {
        return entity.getItemDisplayTransform();
    }

    @Override
    public void setValue(ItemDisplay entity, ItemDisplayTransform value) {
        entity.setItemDisplayTransform(value);
    }

    @Override
    public @NotNull String getName() {
        return "transform";
    }

    @Override
    public @NotNull Class<ItemDisplay> getEntityType() {
        return ItemDisplay.class;
    }

    @Override
    public TypeToken<ItemDisplayTransform> getValueType() {
        return TypeToken.get(ItemDisplayTransform.class);
    }

    @Override
    public ArgumentParser<EasCommandSender, ItemDisplayTransform> getArgumentParser() {
        return new EnumArgument.EnumParser<>(ItemDisplayTransform.class);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("item transform");
    }

    @Override
    public @NotNull Component getValueName(ItemDisplayTransform value) {
        return Component.text(value.name().toLowerCase(Locale.ROOT));
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.display.item.transform";
    }

    @Override
    public ItemDisplayTransform getNextValue(ItemDisplay entity) {
        ItemDisplayTransform[] values = ItemDisplayTransform.values();
        return values[(entity.getItemDisplayTransform().ordinal() + 1) % values.length];
    }

    @Override
    public ItemStack createToggleButton(ItemDisplay entity) {
        return Util.createItem(
                ItemType.STICK,
                Component.text("Toggle transform mode", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Currently ", NamedTextColor.GRAY)
                                .append(getValueName(getValue(entity)))
                                .append(Component.text(".")),
                        Component.text("Changes which of the", NamedTextColor.GRAY),
                        Component.text("transforms defined in", NamedTextColor.GRAY),
                        Component.text("the model is used.", NamedTextColor.GRAY)
                )
        );
    }
}
