package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.EntityPropertyAction;
import me.m56738.easyarmorstands.menu.builder.SplitMenuBuilder;
import me.m56738.easyarmorstands.menu.slot.ItemPropertySlot;
import me.m56738.easyarmorstands.property.EntityPropertyType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.SimpleEntityProperty;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Locale;

public class EntityEquipmentPropertyType implements EntityPropertyType<ItemStack> {
    public static final EnumMap<EquipmentSlot, EntityEquipmentPropertyType> SLOTS = new EnumMap<>(EquipmentSlot.class);

    static {
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            String upperName = equipmentSlot.name().replace('_', ' ');
            String lowerName = upperName.toLowerCase(Locale.ROOT);
            SLOTS.put(equipmentSlot, new EntityEquipmentPropertyType(equipmentSlot, Component.text(lowerName)));
        }
    }

    private final EquipmentCapability equipmentCapability;
    private final ComponentCapability componentCapability;
    private final EquipmentSlot equipmentSlot;
    private final Component displayName;

    private EntityEquipmentPropertyType(EquipmentSlot equipmentSlot, Component displayName) {
        this.equipmentCapability = EasyArmorStands.getInstance().getCapability(EquipmentCapability.class);
        this.componentCapability = EasyArmorStands.getInstance().getCapability(ComponentCapability.class);
        this.equipmentSlot = equipmentSlot;
        this.displayName = displayName;
    }

    public static void populate(SplitMenuBuilder builder, Entity entity, Session session) {
        populate(builder, entity, session, 19, EquipmentSlot.HEAD);
        populate(builder, entity, session, 27, EasyArmorStands.getInstance().getCapability(EquipmentCapability.class).getOffHand());
        populate(builder, entity, session, 28, EquipmentSlot.CHEST);
        populate(builder, entity, session, 29, EquipmentSlot.HAND);
        populate(builder, entity, session, 37, EquipmentSlot.LEGS);
        populate(builder, entity, session, 46, EquipmentSlot.FEET);
    }

    private static void populate(SplitMenuBuilder builder, Entity entity, Session session, int index, EquipmentSlot equipmentSlot) {
        if (equipmentSlot == null) {
            return;
        }
        EntityEquipmentPropertyType propertyType = SLOTS.get(equipmentSlot);
        if (propertyType == null) {
            return;
        }
        Property<ItemStack> property = propertyType.bind(entity);
        if (property == null) {
            return;
        }
        builder.setSlot(index, new ItemPropertySlot(property, session));
    }

    @Override
    public @Nullable Property<ItemStack> bind(Entity entity) {
        if (entity instanceof LivingEntity) {
            return new Bound((LivingEntity) entity);
        }
        return null;
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.equipment";
    }

    @Override
    public Component getDisplayName() {
        return displayName;
    }

    @Override
    public Component getValueComponent(ItemStack value) {
        return componentCapability.getItemDisplayName(value);
    }

    private class Bound extends SimpleEntityProperty<ItemStack> {
        private final EntityEquipment equipment;
        private final LivingEntity entity;

        private Bound(LivingEntity entity) {
            super(EntityEquipmentPropertyType.this, entity);
            this.equipment = entity.getEquipment();
            this.entity = entity;
        }

        @Override
        public ItemStack getValue() {
            return equipmentCapability.getItem(equipment, equipmentSlot);
        }

        @Override
        public void setValue(ItemStack value) {
            equipmentCapability.setItem(equipment, equipmentSlot, value);
        }

        @Override
        public Action createChangeAction(ItemStack oldValue, ItemStack value) {
            return new EntityPropertyAction<>(entity, Bound::new, oldValue, value, Component.text("Changed ").append(displayName));
        }
    }
}
