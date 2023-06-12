package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.property.BooleanEntityProperty;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArmorStandGravityProperty extends BooleanEntityProperty<ArmorStand> {
    private final @Nullable ArmorStandCanTickProperty canTickProperty;

    public ArmorStandGravityProperty(@Nullable ArmorStandCanTickProperty canTickProperty) {
        this.canTickProperty = canTickProperty;
    }

    @Override
    public Boolean getValue(ArmorStand entity) {
        return entity.hasGravity();
    }

    @Override
    public void setValue(ArmorStand entity, Boolean value) {
        entity.setGravity(value);
    }

    @Override
    public @NotNull String getName() {
        return "gravity";
    }

    @Override
    public @NotNull Class<ArmorStand> getEntityType() {
        return ArmorStand.class;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("gravity");
    }

    @Override
    public @NotNull Component getValueName(Boolean value) {
        return value
                ? Component.text("enabled", NamedTextColor.GREEN)
                : Component.text("static", NamedTextColor.RED);
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.gravity";
    }

    @Override
    public ItemStack createToggleButton(ArmorStand entity) {
        List<Component> description = Arrays.asList(
                Component.text("Currently ", NamedTextColor.GRAY)
                        .append(canTickProperty != null && !canTickProperty.getValue(entity)
                                ? Component.text("frozen", NamedTextColor.GOLD)
                                : entity.hasGravity()
                                ? Component.text("has gravity", NamedTextColor.GREEN)
                                : Component.text("static", NamedTextColor.RED))
                        .append(Component.text(".")),
                Component.text("Changes whether the", NamedTextColor.GRAY),
                Component.text("armor stand will fall", NamedTextColor.GRAY),
                Component.text("due to gravity.", NamedTextColor.GRAY)
        );
        if (canTickProperty != null && entity.hasGravity() && !canTickProperty.getValue(entity)) {
            description = new ArrayList<>(description);
            description.add(Component.text("Gravity is enabled but", NamedTextColor.GOLD));
            description.add(Component.text("armor stand ticking is", NamedTextColor.GOLD));
            description.add(Component.text("disabled, so gravity", NamedTextColor.GOLD));
            description.add(Component.text("has no effect.", NamedTextColor.GOLD));
        }
        return Util.createItem(
                ItemType.FEATHER,
                Component.text("Toggle gravity", NamedTextColor.BLUE),
                description
        );
    }

    @Override
    public void toggle(Session session, ArmorStand entity) {
        boolean gravity = !entity.hasGravity();
        if (gravity && canTickProperty != null && !canTickProperty.getValue(entity) &&
                session.getPlayer().hasPermission(canTickProperty.getPermission())) {
            // Attempt to enable ticking when enabling gravity
            session.tryChange(entity, canTickProperty, true);
        }
        session.tryChange(entity, this, gravity);
        session.commit();
    }
}
