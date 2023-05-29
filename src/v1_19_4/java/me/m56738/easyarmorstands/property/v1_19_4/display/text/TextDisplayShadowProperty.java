package me.m56738.easyarmorstands.property.v1_19_4.display.text;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.property.BooleanEntityProperty;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class TextDisplayShadowProperty extends BooleanEntityProperty<TextDisplay> {
    @Override
    public Boolean getValue(TextDisplay entity) {
        return entity.isShadowed();
    }

    @Override
    public void setValue(TextDisplay entity, Boolean value) {
        entity.setShadowed(value);
    }

    @Override
    public @NotNull String getName() {
        return "textshadow";
    }

    @Override
    public @NotNull Class<TextDisplay> getEntityType() {
        return TextDisplay.class;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("text shadow");
    }

    @Override
    public @NotNull Component getValueName(Boolean value) {
        return value
                ? Component.text("enabled", NamedTextColor.GREEN)
                : Component.text("disabled", NamedTextColor.RED);
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.display.textshadow";
    }

    @Override
    public ItemStack createToggleButton(TextDisplay entity) {
        return Util.createItem(
                ItemType.SUNFLOWER,
                Component.text("Toggle shadow", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Currently ", NamedTextColor.GRAY)
                                .append(getValueName(getValue(entity)))
                                .append(Component.text(".")),
                        Component.text("Changes whether the text", NamedTextColor.GRAY),
                        Component.text("is rendered with a shadow.", NamedTextColor.GRAY)
                )
        );
    }
}
