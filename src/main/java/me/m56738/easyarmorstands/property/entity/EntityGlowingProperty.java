package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.capability.glow.GlowCapability;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.property.BooleanTogglePropertyType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertyType;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class EntityGlowingProperty implements Property<Boolean> {
    public static final PropertyType<Boolean> TYPE = new Type();
    private final Entity entity;
    private final GlowCapability glowCapability;

    public EntityGlowingProperty(Entity entity, GlowCapability glowCapability) {
        this.entity = entity;
        this.glowCapability = glowCapability;
    }

    @Override
    public PropertyType<Boolean> getType() {
        return TYPE;
    }

    @Override
    public Boolean getValue() {
        return glowCapability.isGlowing(entity);
    }

    @Override
    public boolean setValue(Boolean value) {
        glowCapability.setGlowing(entity, value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    private static class Type implements BooleanTogglePropertyType {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.glow";
        }

        @Override
        public Component getDisplayName() {
            return Component.text("glowing");
        }

        @Override
        public Component getValueComponent(Boolean value) {
            return value
                    ? Component.text("glowing", NamedTextColor.GREEN)
                    : Component.text("not glowing", NamedTextColor.RED);
        }

        @Override
        public ItemStack createItem(Property<Boolean> property, PropertyContainer container) {
            return Util.createItem(
                    ItemType.GLOWSTONE_DUST,
                    Component.text("Toggle glowing", NamedTextColor.BLUE),
                    Arrays.asList(
                            Component.text("Currently ", NamedTextColor.GRAY)
                                    .append(getValueComponent(property.getValue()))
                                    .append(Component.text(".")),
                            Component.text("Changes whether the", NamedTextColor.GRAY),
                            Component.text("outline of this entity", NamedTextColor.GRAY),
                            Component.text("is shown.", NamedTextColor.GRAY)
                    )
            );
        }
    }
}
