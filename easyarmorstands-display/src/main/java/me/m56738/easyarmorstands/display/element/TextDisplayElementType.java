package me.m56738.easyarmorstands.display.element;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.display.DisplayAddon;
import me.m56738.easyarmorstands.display.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.display.editor.node.DisplayRootNodeFactory;
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
    public void applyDefaultProperties(PropertyMap properties) {
        super.applyDefaultProperties(properties);
        properties.put(TextDisplayPropertyTypes.TEXT, Component.text("Text"));
    }
}
