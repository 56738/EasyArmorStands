package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.capability.glow.GlowCapability;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.property.BooleanEntityProperty;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class EntityGlowingProperty extends BooleanEntityProperty<Entity> {
    private final GlowCapability glowCapability;

    public EntityGlowingProperty(GlowCapability glowCapability) {
        this.glowCapability = glowCapability;
    }

    @Override
    public Boolean getValue(Entity entity) {
        return glowCapability.isGlowing(entity);
    }

    @Override
    public void setValue(Entity entity, Boolean value) {
        glowCapability.setGlowing(entity, value);
    }

    @Override
    public @NotNull String getName() {
        return "glow";
    }

    @Override
    public @NotNull Class<Entity> getEntityType() {
        return Entity.class;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("glowing");
    }

    @Override
    public @NotNull Component getValueName(Boolean value) {
        return value
                ? Component.text("glowing", NamedTextColor.GREEN)
                : Component.text("not glowing", NamedTextColor.RED);
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.glow";
    }

    @Override
    public ItemStack createToggleButton(Entity entity) {
        return Util.createItem(
                ItemType.GLOWSTONE_DUST,
                Component.text("Toggle glowing", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Currently ", NamedTextColor.GRAY)
                                .append(getValueName(getValue(entity)))
                                .append(Component.text(".")),
                        Component.text("Changes whether the", NamedTextColor.GRAY),
                        Component.text("outline of this entity", NamedTextColor.GRAY),
                        Component.text("is shown.", NamedTextColor.GRAY)
                )
        );
    }
}
