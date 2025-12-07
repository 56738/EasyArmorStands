package me.m56738.easyarmorstands.paper.property.display.item;

import me.m56738.easyarmorstands.api.platform.entity.display.ItemDisplayTransform;
import me.m56738.easyarmorstands.api.property.type.ItemDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.ItemDisplay;

public class ItemDisplayTransformProperty extends EntityProperty<ItemDisplay, ItemDisplayTransform> {
    public ItemDisplayTransformProperty(ItemDisplay entity) {
        super(entity);
    }

    @Override
    public PropertyType<ItemDisplayTransform> getType() {
        return ItemDisplayPropertyTypes.TRANSFORM;
    }

    @Override
    public ItemDisplayTransform getValue() {
        return switch (entity.getItemDisplayTransform()) {
            case NONE -> ItemDisplayTransform.NONE;
            case THIRDPERSON_LEFTHAND -> ItemDisplayTransform.THIRDPERSON_LEFTHAND;
            case THIRDPERSON_RIGHTHAND -> ItemDisplayTransform.THIRDPERSON_RIGHTHAND;
            case FIRSTPERSON_LEFTHAND -> ItemDisplayTransform.FIRSTPERSON_LEFTHAND;
            case FIRSTPERSON_RIGHTHAND -> ItemDisplayTransform.FIRSTPERSON_RIGHTHAND;
            case HEAD -> ItemDisplayTransform.HEAD;
            case GUI -> ItemDisplayTransform.GUI;
            case GROUND -> ItemDisplayTransform.GROUND;
            case FIXED -> ItemDisplayTransform.FIXED;
        };
    }

    @Override
    public boolean setValue(ItemDisplayTransform value) {
        entity.setItemDisplayTransform(switch (value) {
            case NONE -> ItemDisplay.ItemDisplayTransform.NONE;
            case THIRDPERSON_LEFTHAND -> ItemDisplay.ItemDisplayTransform.THIRDPERSON_LEFTHAND;
            case THIRDPERSON_RIGHTHAND -> ItemDisplay.ItemDisplayTransform.THIRDPERSON_RIGHTHAND;
            case FIRSTPERSON_LEFTHAND -> ItemDisplay.ItemDisplayTransform.FIRSTPERSON_LEFTHAND;
            case FIRSTPERSON_RIGHTHAND -> ItemDisplay.ItemDisplayTransform.FIRSTPERSON_RIGHTHAND;
            case HEAD -> ItemDisplay.ItemDisplayTransform.HEAD;
            case GUI -> ItemDisplay.ItemDisplayTransform.GUI;
            case GROUND -> ItemDisplay.ItemDisplayTransform.GROUND;
            case FIXED -> ItemDisplay.ItemDisplayTransform.FIXED;
        });
        return true;
    }
}
