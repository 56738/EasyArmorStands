package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.EntityPropertyAction;
import me.m56738.easyarmorstands.menu.builder.SplitMenuBuilder;
import me.m56738.easyarmorstands.menu.slot.ItemPropertySlot;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.key.Key;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class EntityEquipmentProperty implements Property<ItemStack> {
    private static final Key<EntityEquipmentProperty> KEY = Key.of(EntityEquipmentProperty.class);
    private final LivingEntity entity;
    private final EquipmentSlot slot;
    private final Component displayName;
    private final EquipmentCapability equipmentCapability;
    private final ComponentCapability componentCapability;

    public EntityEquipmentProperty(LivingEntity entity, EquipmentSlot slot, Component displayName, EquipmentCapability equipmentCapability, ComponentCapability componentCapability) {
        this.entity = entity;
        this.slot = slot;
        this.displayName = displayName;
        this.equipmentCapability = equipmentCapability;
        this.componentCapability = componentCapability;
    }

    public static Key<EntityEquipmentProperty> key(EquipmentSlot slot) {
        return Key.of(KEY, slot);
    }

    public static void populate(SplitMenuBuilder builder, Session session) {
        populate(builder, session, 19, EquipmentSlot.HEAD);
        populate(builder, session, 27, EasyArmorStands.getInstance().getCapability(EquipmentCapability.class).getOffHand());
        populate(builder, session, 28, EquipmentSlot.CHEST);
        populate(builder, session, 29, EquipmentSlot.HAND);
        populate(builder, session, 37, EquipmentSlot.LEGS);
        populate(builder, session, 46, EquipmentSlot.FEET);
    }

    private static void populate(SplitMenuBuilder builder, Session session, int index, EquipmentSlot slot) {
        if (slot == null) {
            return;
        }
        EntityEquipmentProperty property = session.findProperty(key(slot));
        if (property == null) {
            return;
        }
        builder.setSlot(index, new ItemPropertySlot(property, session));
    }

    @Override
    public ItemStack getValue() {
        return equipmentCapability.getItem(entity.getEquipment(), slot);
    }

    @Override
    public void setValue(ItemStack value) {
        equipmentCapability.setItem(entity.getEquipment(), slot, value);
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

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public Action createChangeAction(ItemStack oldValue, ItemStack value) {
        return new EntityPropertyAction<>(entity, e -> new EntityEquipmentProperty(e, slot, displayName, equipmentCapability, componentCapability), oldValue, value, Component.text("Changed ").append(getDisplayName()));
    }
}
