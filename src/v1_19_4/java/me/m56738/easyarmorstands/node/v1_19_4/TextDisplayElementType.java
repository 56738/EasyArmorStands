package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.display.DisplayAddon;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyRegistry;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayPropertyTypes;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;

public class TextDisplayElementType extends DisplayElementType<TextDisplay> {
    public TextDisplayElementType(DisplayAddon addon, DisplayRootNodeFactory<TextDisplay> factory) {
        super(addon,
                TextDisplay.class,
                EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class).
                        getName(EntityType.TEXT_DISPLAY),
                factory);
    }

    @Override
    public void applyDefaultProperties(PropertyRegistry properties) {
        super.applyDefaultProperties(properties);
        properties.merge(Property.of(DisplayPropertyTypes.TEXT_DISPLAY_TEXT, Component.text("Text")));
    }
}
