package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.capability.glow.GlowCapability;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.EntityPropertyAction;
import me.m56738.easyarmorstands.property.BooleanToggleProperty;
import me.m56738.easyarmorstands.property.key.Key;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class EntityGlowingProperty implements BooleanToggleProperty {
    public static final Key<EntityGlowingProperty> KEY = Key.of(EntityGlowingProperty.class);
    private final Entity entity;
    private final GlowCapability glowCapability;

    public EntityGlowingProperty(Entity entity, GlowCapability glowCapability) {
        this.entity = entity;
        this.glowCapability = glowCapability;
    }

    @Override
    public Boolean getValue() {
        return glowCapability.isGlowing(entity);
    }

    @Override
    public void setValue(Boolean value) {
        glowCapability.setGlowing(entity, value);
    }

    @Override
    public Action createChangeAction(Boolean oldValue, Boolean value) {
        return new EntityPropertyAction<>(entity, e -> new EntityGlowingProperty(e, glowCapability), oldValue, value, Component.text("Changed ").append(getDisplayName()));
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("glowing");
    }

    @Override
    public @NotNull Component getValueComponent(Boolean value) {
        return value
                ? Component.text("glowing", NamedTextColor.GREEN)
                : Component.text("not glowing", NamedTextColor.RED);
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.glow";
    }

    @Override
    public ItemStack createItem() {
        return Util.createItem(
                ItemType.GLOWSTONE_DUST,
                Component.text("Toggle glowing", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Currently ", NamedTextColor.GRAY)
                                .append(getValueComponent(getValue()))
                                .append(Component.text(".")),
                        Component.text("Changes whether the", NamedTextColor.GRAY),
                        Component.text("outline of this entity", NamedTextColor.GRAY),
                        Component.text("is shown.", NamedTextColor.GRAY)
                )
        );
    }
}
