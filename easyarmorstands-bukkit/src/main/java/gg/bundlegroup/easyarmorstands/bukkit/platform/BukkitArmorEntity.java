package gg.bundlegroup.easyarmorstands.bukkit.platform;

import gg.bundlegroup.easyarmorstands.bukkit.feature.EquipmentAccessor;
import gg.bundlegroup.easyarmorstands.core.platform.EasArmorEntity;
import gg.bundlegroup.easyarmorstands.core.platform.EasItem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class BukkitArmorEntity<T extends LivingEntity> extends BukkitEntity<T> implements EasArmorEntity {
    private final EquipmentAccessor equipmentAccessor;

    public BukkitArmorEntity(BukkitPlatform platform, T entity) {
        super(platform, entity);
        this.equipmentAccessor = platform.equipmentAccessor();
    }

    @Override
    public EasItem getItem(EasArmorEntity.Slot slot) {
        ItemStack item = equipmentAccessor.getItem(get().getEquipment(), slot);
        if (item != null) {
            item = item.clone();
        }
        return platform().getItem(item);
    }

    @Override
    public void setItem(EasArmorEntity.Slot slot, EasItem item) {
        BukkitItem bukkitItem = (BukkitItem) item;
        equipmentAccessor.setItem(get().getEquipment(), slot, bukkitItem != null ? bukkitItem.get() : null);
    }
}
