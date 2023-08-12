package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.menu.builder.MenuBuilder;
import me.m56738.easyarmorstands.menu.builder.SplitMenuBuilder;
import me.m56738.easyarmorstands.menu.slot.ItemPropertySlot;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.EnumMap;
import java.util.Locale;

public class EntityEquipmentProperty implements Property<ItemStack> {
    private static final EnumMap<EquipmentSlot, PropertyType<ItemStack>> TYPES = new EnumMap<>(EquipmentSlot.class);

    static {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            TYPES.put(slot, new Type(slot));
        }
    }

    private final LivingEntity entity;
    private final EquipmentSlot slot;
    private final PropertyType<ItemStack> type;
    private final EquipmentCapability equipmentCapability;

    public EntityEquipmentProperty(LivingEntity entity, EquipmentSlot slot, EquipmentCapability equipmentCapability) {
        this.entity = entity;
        this.slot = slot;
        this.type = type(slot);
        this.equipmentCapability = equipmentCapability;
    }

    public static PropertyType<ItemStack> type(EquipmentSlot slot) {
        return TYPES.get(slot);
    }

    public static void populate(MenuBuilder builder, PropertyContainer container) {
        populate(builder, container, 19, EquipmentSlot.HEAD);
        populate(builder, container, 27, EasyArmorStands.getInstance().getCapability(EquipmentCapability.class).getOffHand());
        populate(builder, container, 28, EquipmentSlot.CHEST);
        populate(builder, container, 29, EquipmentSlot.HAND);
        populate(builder, container, 37, EquipmentSlot.LEGS);
        populate(builder, container, 46, EquipmentSlot.FEET);
    }

    private static void populate(MenuBuilder builder, PropertyContainer container, int index, EquipmentSlot equipmentSlot) {
        if (equipmentSlot == null) {
            return;
        }
        Property<ItemStack> property = container.getOrNull(type(equipmentSlot));
        if (property == null) {
            return;
        }
        ItemPropertySlot slot = new ItemPropertySlot(property, container);
        if (builder instanceof SplitMenuBuilder) {
            ((SplitMenuBuilder) builder).setSlot(index, slot);
        } else {
            builder.addUtility(slot);
        }
    }

    @Override
    public PropertyType<ItemStack> getType() {
        return type;
    }

    @Override
    public ItemStack getValue() {
        return equipmentCapability.getItem(entity.getEquipment(), slot);
    }

    @Override
    public boolean setValue(ItemStack value) {
        equipmentCapability.setItem(entity.getEquipment(), slot, value);
        return true;
    }

    private static class Type implements PropertyType<ItemStack> {
        private final Component displayName;

        private Type(EquipmentSlot slot) {
            String upperName = slot.name().replace('_', ' ');
            String lowerName = upperName.toLowerCase(Locale.ROOT);
            this.displayName = Component.text(lowerName);
        }

        @Override
        public String getPermission() {
            return "easyarmorstands.property.equipment";
        }

        @Override
        public Component getDisplayName() {
            return displayName;
        }

        @Override
        public Component getValueComponent(ItemStack value) {
            ComponentCapability componentCapability = EasyArmorStands.getInstance().getCapability(ComponentCapability.class);
            return componentCapability.getItemDisplayName(value);
        }
    }
}
