package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.platform.EasArmorEntity;
import gg.bundlegroup.easyarmorstands.platform.EasItem;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EquipmentAccessor;
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
        equipmentAccessor.setItem(get().getEquipment(), slot, ((BukkitItem) item).get());
    }
}
