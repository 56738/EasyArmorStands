package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.EntityPropertyAction;
import me.m56738.easyarmorstands.property.BooleanToggleProperty;
import me.m56738.easyarmorstands.property.key.PropertyKey;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ArmorStandBasePlateProperty implements BooleanToggleProperty {
    public static final PropertyKey<Boolean> KEY = PropertyKey.of(EasyArmorStands.key("armor_stand_base_plate"));
    private final ArmorStand entity;

    public ArmorStandBasePlateProperty(ArmorStand entity) {
        this.entity = entity;
    }

    @Override
    public Boolean getValue() {
        return entity.hasBasePlate();
    }

    @Override
    public void setValue(Boolean value) {
        entity.setBasePlate(value);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("base plate");
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public Action createChangeAction(Boolean oldValue, Boolean value) {
        return new EntityPropertyAction<>(entity, ArmorStandBasePlateProperty::new, oldValue, value, Component.text("Changed ").append(getDisplayName()));
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.armorstand.baseplate";
    }

    @Override
    public ItemStack createItem() {
        return Util.createItem(
                ItemType.STONE_SLAB,
                Component.text("Toggle base plate", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Currently ", NamedTextColor.GRAY)
                                .append(getValueComponent(getValue()))
                                .append(Component.text(".")),
                        Component.text("Changes whether the base", NamedTextColor.GRAY),
                        Component.text("plate of the armor stand", NamedTextColor.GRAY),
                        Component.text("is visible.", NamedTextColor.GRAY)
                )
        );
    }
}
