package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.capability.tick.TickCapability;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.EntityPropertyAction;
import me.m56738.easyarmorstands.property.BooleanProperty;
import me.m56738.easyarmorstands.property.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

public class ArmorStandCanTickProperty implements BooleanProperty {
    public static final Key<ArmorStandCanTickProperty> KEY = Key.of(ArmorStandCanTickProperty.class);
    private final ArmorStand entity;
    private final TickCapability tickCapability;

    public ArmorStandCanTickProperty(ArmorStand entity, TickCapability tickCapability) {
        this.entity = entity;
        this.tickCapability = tickCapability;
    }

    @Override
    public Boolean getValue() {
        return tickCapability.canTick(entity);
    }

    @Override
    public void setValue(Boolean value) {
        tickCapability.setCanTick(entity, value);
    }

    @Override
    public Action createChangeAction(Boolean oldValue, Boolean value) {
        return new EntityPropertyAction<>(entity, e -> new ArmorStandCanTickProperty(e, tickCapability), oldValue, value, Component.text("Changed ").append(getDisplayName()));
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("ticking");
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.armorstand.cantick";
    }
}
