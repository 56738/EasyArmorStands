package me.m56738.easyarmorstands.common.element;

import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TextDisplayElementType extends DisplayElementType {
    public TextDisplayElementType(EasyArmorStandsCommon eas) {
        super(eas, eas.platform().getTextDisplayType());
    }

    @Override
    public void applyDefaultProperties(@NotNull PropertyMap properties) {
        super.applyDefaultProperties(properties);
        properties.put(TextDisplayPropertyTypes.TEXT, Component.text("Text"));
        properties.put(TextDisplayPropertyTypes.BACKGROUND, Optional.empty());
    }
}
