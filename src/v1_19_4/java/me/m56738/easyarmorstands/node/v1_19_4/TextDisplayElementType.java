package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyRegistry;
import me.m56738.easyarmorstands.property.v1_19_4.display.text.TextDisplayTextProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;

public class TextDisplayElementType extends DisplayElementType<TextDisplay> {
    public TextDisplayElementType(DisplayRootNodeFactory<TextDisplay> factory) {
        super(TextDisplay.class,
                EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class).
                        getName(EntityType.TEXT_DISPLAY),
                factory);
    }

    @Override
    public void applyDefaultProperties(PropertyRegistry properties) {
        super.applyDefaultProperties(properties);
        properties.merge(Property.of(TextDisplayTextProperty.TYPE, Component.text("Text")));
    }
}
