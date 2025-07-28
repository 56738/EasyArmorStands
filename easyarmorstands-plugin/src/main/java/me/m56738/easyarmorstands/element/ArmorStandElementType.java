package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperEntityType;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class ArmorStandElementType extends SimpleEntityElementType {
    private final Platform platform;

    public ArmorStandElementType(Platform platform) {
        super(platform, PaperEntityType.fromNative(EntityType.ARMOR_STAND));
        this.platform = platform;
    }

    @Override
    protected SimpleEntityElement createInstance(Entity entity) {
        return new ArmorStandElement(platform, entity, this);
    }

    @Override
    public void applyDefaultProperties(@NotNull PropertyMap properties) {
        super.applyDefaultProperties(properties);
        properties.put(ArmorStandPropertyTypes.GRAVITY, false);
        properties.put(ArmorStandPropertyTypes.BASE_PLATE, false);
        properties.put(ArmorStandPropertyTypes.ARMS, true);
    }

    @Override
    public boolean isSpawnedAtEyeHeight() {
        return false;
    }
}
