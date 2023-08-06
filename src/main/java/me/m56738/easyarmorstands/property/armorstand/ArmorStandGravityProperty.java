package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.capability.tick.TickCapability;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.EntityPropertyAction;
import me.m56738.easyarmorstands.property.BooleanToggleProperty;
import me.m56738.easyarmorstands.property.ChangeContext;
import me.m56738.easyarmorstands.property.key.Key;
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

public class ArmorStandGravityProperty implements BooleanToggleProperty {
    public static final Key<ArmorStandGravityProperty> KEY = Key.of(ArmorStandGravityProperty.class);
    private final ArmorStand entity;
    private final @Nullable TickCapability tickCapability;
    private final @Nullable ArmorStandCanTickProperty canTickProperty;

    public ArmorStandGravityProperty(ArmorStand entity, @Nullable TickCapability tickCapability) {
        this.entity = entity;
        this.tickCapability = tickCapability;
        this.canTickProperty = tickCapability != null ? new ArmorStandCanTickProperty(entity, tickCapability) : null;
    }

    @Override
    public Boolean getValue() {
        return entity.hasGravity();
    }

    @Override
    public void setValue(Boolean value) {
        entity.setGravity(value);
    }

    @Override
    public Action createChangeAction(Boolean oldValue, Boolean value) {
        return new EntityPropertyAction<>(entity, e -> new ArmorStandGravityProperty(e, tickCapability), oldValue, value, Component.text("Changed ").append(getDisplayName()));
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("gravity");
    }

    @Override
    public @NotNull Component getValueComponent(Boolean value) {
        return value
                ? Component.text("enabled", NamedTextColor.GREEN)
                : Component.text("static", NamedTextColor.RED);
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.gravity";
    }

    @Override
    public ItemStack createItem() {
        List<Component> description = Arrays.asList(
                Component.text("Currently ", NamedTextColor.GRAY)
                        .append(canTickProperty != null && !canTickProperty.getValue()
                                ? Component.text("frozen", NamedTextColor.GOLD)
                                : entity.hasGravity()
                                ? Component.text("has gravity", NamedTextColor.GREEN)
                                : Component.text("static", NamedTextColor.RED))
                        .append(Component.text(".")),
                Component.text("Changes whether the", NamedTextColor.GRAY),
                Component.text("armor stand will fall", NamedTextColor.GRAY),
                Component.text("due to gravity.", NamedTextColor.GRAY)
        );
        if (canTickProperty != null && entity.hasGravity() && !canTickProperty.getValue()) {
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
    public void onClick(ChangeContext context) {
        boolean value = getNextValue();
        if (value && canTickProperty != null && !canTickProperty.getValue()) {
            context.tryChange(canTickProperty, true);
        }
        context.tryChange(this, value);
    }
}
