package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.menu.builder.SplitMenuBuilder;
import me.m56738.easyarmorstands.menu.slot.ItemPropertySlot;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertyType;
import me.m56738.easyarmorstands.session.Session;
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
    private final ComponentCapability componentCapability;

    public EntityEquipmentProperty(LivingEntity entity, EquipmentSlot slot, EquipmentCapability equipmentCapability, ComponentCapability componentCapability) {
        this.entity = entity;
        this.slot = slot;
        this.type = type(slot);
        this.equipmentCapability = equipmentCapability;
        this.componentCapability = componentCapability;
    }

    public static PropertyType<ItemStack> type(EquipmentSlot slot) {
        return TYPES.get(slot);
    }

    public static void populate(SplitMenuBuilder builder, Session session, PropertyContainer container) {
        populate(builder, session, container, 19, EquipmentSlot.HEAD);
        populate(builder, session, container, 27, EasyArmorStands.getInstance().getCapability(EquipmentCapability.class).getOffHand());
        populate(builder, session, container, 28, EquipmentSlot.CHEST);
        populate(builder, session, container, 29, EquipmentSlot.HAND);
        populate(builder, session, container, 37, EquipmentSlot.LEGS);
        populate(builder, session, container, 46, EquipmentSlot.FEET);
    }

    private static void populate(SplitMenuBuilder builder, Session session, PropertyContainer container, int index, EquipmentSlot slot) {
        if (slot == null) {
            return;
        }
        Property<ItemStack> property = container.getOrNull(type(slot));
        if (property == null) {
            return;
        }
        builder.setSlot(index, new ItemPropertySlot(property, session));
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

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    private static class Type implements PropertyType<ItemStack> {
        private final EquipmentSlot slot;
        private final Component displayName;

        private Type(EquipmentSlot slot) {
            this.slot = slot;
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
