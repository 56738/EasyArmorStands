package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.EntityPropertyAction;
import me.m56738.easyarmorstands.property.ComponentProperty;
import me.m56738.easyarmorstands.property.key.PropertyKey;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityCustomNameProperty implements ComponentProperty {
    public static final PropertyKey<Component> KEY = PropertyKey.of(EasyArmorStands.key("entity_name"));
    private final Entity entity;
    private final ComponentCapability componentCapability;

    public EntityCustomNameProperty(Entity entity, ComponentCapability componentCapability) {
        this.entity = entity;
        this.componentCapability = componentCapability;
    }

    @Override
    public Component getValue() {
        Component name = componentCapability.getCustomName(entity);
        if (name == null) {
            name = Component.empty();
        }
        return name;
    }

    @Override
    public void setValue(Component value) {
        componentCapability.setCustomName(entity, value);
    }

    public boolean hasCustomName() {
        return entity.getCustomName() != null;
    }

    @Override
    public Action createChangeAction(Component oldValue, Component value) {
        return new EntityPropertyAction<>(entity, e -> new EntityCustomNameProperty(e, componentCapability), oldValue, value, Component.text("Changed ").append(getDisplayName()));
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("custom name");
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.name";
    }
}
