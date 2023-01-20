package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.platform.EasArmorEntity;
import gg.bundlegroup.easyarmorstands.platform.EasItem;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EquipmentAccessor;
import org.bukkit.entity.LivingEntity;

public class BukkitArmorEntity<T extends LivingEntity> extends BukkitEntity<T> implements EasArmorEntity {
    private final EquipmentAccessor equipmentAccessor;

    public BukkitArmorEntity(BukkitPlatform platform, T entity) {
        super(platform, entity);
        this.equipmentAccessor = platform.equipmentAccessor();
    }

    @Override
    public EasItem getItem(EasArmorEntity.Slot slot) {
        return platform().getItem(equipmentAccessor.getItem(get().getEquipment(), slot));
    }

    @Override
    public void setItem(EasArmorEntity.Slot slot, EasItem item) {
        equipmentAccessor.setItem(get().getEquipment(), slot, ((BukkitItem) item).get());
    }
}
