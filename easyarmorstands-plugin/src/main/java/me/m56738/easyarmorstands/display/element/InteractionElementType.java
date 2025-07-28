package me.m56738.easyarmorstands.display.element;

import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.InteractionPropertyTypes;
import me.m56738.easyarmorstands.element.SimpleEntityElementType;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperEntityType;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class InteractionElementType extends SimpleEntityElementType {
    private final Platform platform;

    public InteractionElementType(Platform platform) {
        super(platform, PaperEntityType.fromNative(EntityType.INTERACTION));
        this.platform = platform;
    }

    @Override
    protected InteractionElement createInstance(Entity entity) {
        return new InteractionElement(platform, entity, this);
    }

    @Override
    public void applyDefaultProperties(@NotNull PropertyMap properties) {
        properties.put(DisplayPropertyTypes.BOX_WIDTH, 1f);
        properties.put(DisplayPropertyTypes.BOX_HEIGHT, 1f);
        properties.put(InteractionPropertyTypes.RESPONSIVE, true);
    }
}
