package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.EntityPropertyAction;
import me.m56738.easyarmorstands.property.BooleanToggleProperty;
import me.m56738.easyarmorstands.property.key.Key;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ArmorStandMarkerProperty implements BooleanToggleProperty {
    public static final Key<ArmorStandMarkerProperty> KEY = Key.of(ArmorStandMarkerProperty.class);
    private final ArmorStand entity;

    public ArmorStandMarkerProperty(ArmorStand entity) {
        this.entity = entity;
    }

    @Override
    public Boolean getValue() {
        return entity.isMarker();
    }

    @Override
    public void setValue(Boolean value) {
        entity.setMarker(value);
    }

    @Override
    public Action createChangeAction(Boolean oldValue, Boolean value) {
        return new EntityPropertyAction<>(entity, ArmorStandMarkerProperty::new, oldValue, value, Component.text("Changed ").append(getDisplayName()));
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("marker");
    }

    @Override
    public @NotNull Component getValueComponent(Boolean value) {
        return value
                ? Component.text("a marker", NamedTextColor.GREEN)
                : Component.text("not a marker", NamedTextColor.RED);
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.armorstand.marker";
    }

    @Override
    public ItemStack createItem() {
        return Util.createItem(
                ItemType.SUNFLOWER,
                Component.text("Toggle marker", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Currently ", NamedTextColor.GRAY)
                                .append(getValueComponent(getValue()))
                                .append(Component.text(".")),
                        Component.text("Changes whether the", NamedTextColor.GRAY),
                        Component.text("armor stand is a marker", NamedTextColor.GRAY),
                        Component.text("with zero size and without", NamedTextColor.GRAY),
                        Component.text("collision or interaction.", NamedTextColor.GRAY)
                )
        );
    }
}
