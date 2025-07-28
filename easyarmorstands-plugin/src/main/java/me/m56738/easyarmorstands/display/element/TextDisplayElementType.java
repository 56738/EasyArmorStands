package me.m56738.easyarmorstands.display.element;

import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperEntityType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TextDisplayElementType extends DisplayElementType {
    public TextDisplayElementType(Platform platform) {
        super(platform, PaperEntityType.fromNative(EntityType.TEXT_DISPLAY));
    }

    @Override
    public void applyDefaultProperties(@NotNull PropertyMap properties) {
        super.applyDefaultProperties(properties);
        properties.put(TextDisplayPropertyTypes.TEXT, Component.text("Text"));
        properties.put(TextDisplayPropertyTypes.BACKGROUND, Optional.empty());
    }
}
