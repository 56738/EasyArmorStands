package me.m56738.easyarmorstands.paper.property.entity;

import me.m56738.easyarmorstands.api.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import me.m56738.easyarmorstands.paper.api.platform.inventory.PaperItem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;

public class EntityEquipmentProperty extends EntityProperty<LivingEntity, Item> {
    private final PaperPlatform platform;
    private final org.bukkit.inventory.EquipmentSlot nativeSlot;
    private final PropertyType<Item> type;

    public EntityEquipmentProperty(PaperPlatform platform, LivingEntity entity, EquipmentSlot slot) {
        super(entity);
        this.platform = platform;
        this.nativeSlot = org.bukkit.inventory.EquipmentSlot.valueOf(slot.name());
        this.type = EntityPropertyTypes.EQUIPMENT.get(slot);
    }

    @Override
    public PropertyType<Item> getType() {
        return type;
    }

    @Override
    public Item getValue() {
        EntityEquipment equipment = entity.getEquipment();
        if (equipment != null) {
            return platform.getItem(equipment.getItem(nativeSlot));
        } else {
            return platform.getItem(null);
        }
    }

    @Override
    public boolean setValue(Item value) {
        EntityEquipment equipment = entity.getEquipment();
        if (equipment != null) {
            equipment.setItem(nativeSlot, PaperItem.toNative(value));
            return true;
        } else {
            return false;
        }
    }
}
