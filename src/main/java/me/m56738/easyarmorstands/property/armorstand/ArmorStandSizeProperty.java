package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.EntityPropertyAction;
import me.m56738.easyarmorstands.property.ToggleProperty;
import me.m56738.easyarmorstands.property.key.PropertyKey;
import me.m56738.easyarmorstands.util.ArmorStandSize;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ArmorStandSizeProperty implements ToggleProperty<ArmorStandSize> {
    public static final PropertyKey<ArmorStandSize> KEY = PropertyKey.of(EasyArmorStands.key("armor_stand_size"));
    private final ArmorStand entity;

    public ArmorStandSizeProperty(ArmorStand entity) {
        this.entity = entity;
    }

    @Override
    public ArmorStandSize getValue() {
        return entity.isSmall() ? ArmorStandSize.SMALL : ArmorStandSize.NORMAL;
    }

    @Override
    public void setValue(ArmorStandSize value) {
        entity.setSmall(value.isSmall());
    }

    @Override
    public Action createChangeAction(ArmorStandSize oldValue, ArmorStandSize value) {
        return new EntityPropertyAction<>(entity, ArmorStandSizeProperty::new, oldValue, value, Component.text("Changed ").append(getDisplayName()));
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("size");
    }

    @Override
    public @NotNull Component getValueComponent(ArmorStandSize value) {
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
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.armorstand.size";
    }

    @Override
    public ArmorStandSize getNextValue() {
        return entity.isSmall() ? ArmorStandSize.NORMAL : ArmorStandSize.SMALL;
    }

    @Override
    public ItemStack createItem() {
        return Util.createItem(
                ItemType.BONE_MEAL,
                Component.text("Toggle size", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Currently ", NamedTextColor.GRAY)
                                .append(getValueComponent(getValue()))
                                .append(Component.text(".")),
                        Component.text("Changes the size of", NamedTextColor.GRAY),
                        Component.text("the armor stand.", NamedTextColor.GRAY)
                )
        );
    }
}
