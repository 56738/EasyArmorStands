package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.property.BooleanPropertyType;
import me.m56738.easyarmorstands.property.ButtonProperty;
import me.m56738.easyarmorstands.property.ChangeContext;
import me.m56738.easyarmorstands.property.EntityPropertyType;
import me.m56738.easyarmorstands.property.SimpleEntityProperty;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class ArmorStandBasePlatePropertyType extends BooleanPropertyType implements EntityPropertyType<Boolean> {
    public static final ArmorStandBasePlatePropertyType INSTANCE = new ArmorStandBasePlatePropertyType();

    private ArmorStandBasePlatePropertyType() {
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("base plate");
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.armorstand.baseplate";
    }

    @Override
    public @Nullable ButtonProperty<Boolean> bind(Entity entity) {
        if (entity instanceof ArmorStand) {
            return new Bound((ArmorStand) entity);
        }
        return null;
    }

    private class Bound extends SimpleEntityProperty<Boolean> implements ButtonProperty<Boolean> {
        private final ArmorStand entity;

        private Bound(ArmorStand entity) {
            super(ArmorStandBasePlatePropertyType.this, entity);
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

        @Override
        public void onClick(ChangeContext context) {
            context.tryChange(this, !getValue());
        }
    }
}
