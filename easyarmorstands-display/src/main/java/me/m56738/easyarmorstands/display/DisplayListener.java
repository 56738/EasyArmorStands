package me.m56738.easyarmorstands.display;

import me.m56738.easyarmorstands.api.element.ConfigurableEntityElement;
import me.m56738.easyarmorstands.api.event.element.EntityElementInitializeEvent;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.display.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.display.api.property.type.InteractionPropertyTypes;
import me.m56738.easyarmorstands.display.api.property.type.ItemDisplayPropertyTypes;
import me.m56738.easyarmorstands.display.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.display.property.display.DisplayLeftRotationProperty;
import me.m56738.easyarmorstands.display.property.display.DisplayRightRotationProperty;
import me.m56738.easyarmorstands.display.property.display.DisplayScaleProperty;
import me.m56738.easyarmorstands.display.property.display.DisplayTranslationProperty;
import me.m56738.easyarmorstands.display.property.display.item.ItemDisplayItemProperty;
import me.m56738.easyarmorstands.display.property.display.text.TextDisplayBackgroundProperty;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DisplayListener implements Listener {
    @EventHandler
    public void onInitialize(EntityElementInitializeEvent event) {
        ConfigurableEntityElement<?> element = event.getElement();
        registerProperties(element.getEntity(), element.getProperties());
    }

    private void registerProperties(Entity entity, PropertyRegistry registry) {
        if (entity instanceof Display) {
            registerDisplayProperties((Display) entity, registry);
        }
        if (entity instanceof ItemDisplay) {
            registerItemDisplayProperties((ItemDisplay) entity, registry);
        }
        if (entity instanceof BlockDisplay) {
            registerBlockDisplayProperties((BlockDisplay) entity, registry);
        }
        if (entity instanceof TextDisplay) {
            registerTextDisplayProperties((TextDisplay) entity, registry);
        }
        if (entity instanceof Interaction) {
            registerInteractionProperties((Interaction) entity, registry);
        }
    }

    private void registerDisplayProperties(Display entity, PropertyRegistry registry) {
        registry.register(new DisplayTranslationProperty(entity));
        registry.register(new DisplayLeftRotationProperty(entity));
        registry.register(new DisplayScaleProperty(entity));
        registry.register(new DisplayRightRotationProperty(entity));
        registry.register(Property.of(DisplayPropertyTypes.BILLBOARD, entity::getBillboard, entity::setBillboard));
        registry.register(Property.ofNullable(DisplayPropertyTypes.BRIGHTNESS, entity::getBrightness, entity::setBrightness));
        registry.register(Property.of(DisplayPropertyTypes.BOX_WIDTH, entity::getDisplayWidth, entity::setDisplayWidth));
        registry.register(Property.of(DisplayPropertyTypes.BOX_HEIGHT, entity::getDisplayHeight, entity::setDisplayHeight));
        registry.register(Property.ofNullable(DisplayPropertyTypes.GLOW_COLOR, entity::getGlowColorOverride, entity::setGlowColorOverride));
        registry.register(Property.of(DisplayPropertyTypes.VIEW_RANGE, entity::getViewRange, entity::setViewRange));
    }

    private void registerItemDisplayProperties(ItemDisplay entity, PropertyRegistry registry) {
        registry.register(new ItemDisplayItemProperty(entity));
        registry.register(Property.of(ItemDisplayPropertyTypes.TRANSFORM, entity::getItemDisplayTransform, entity::setItemDisplayTransform));
    }

    private void registerBlockDisplayProperties(BlockDisplay entity, PropertyRegistry registry) {
        registry.register(Property.of(BlockDisplayPropertyTypes.BLOCK, entity::getBlock, entity::setBlock));
    }

    private void registerTextDisplayProperties(TextDisplay entity, PropertyRegistry registry) {
        registry.register(Property.of(TextDisplayPropertyTypes.ALIGNMENT, entity::getAlignment, entity::setAlignment));
        registry.register(new TextDisplayBackgroundProperty(entity));
        registry.register(Property.of(TextDisplayPropertyTypes.LINE_WIDTH, entity::getLineWidth, entity::setLineWidth));
        registry.register(Property.of(TextDisplayPropertyTypes.SEE_THROUGH, entity::isSeeThrough, entity::setSeeThrough));
        registry.register(Property.of(TextDisplayPropertyTypes.SHADOW, entity::isShadowed, entity::setShadowed));
        registry.register(Property.of(TextDisplayPropertyTypes.TEXT, entity::text, entity::text));
    }

    private void registerInteractionProperties(Interaction entity, PropertyRegistry registry) {
        registry.register(Property.of(DisplayPropertyTypes.BOX_WIDTH, entity::getInteractionWidth, entity::setInteractionWidth));
        registry.register(Property.of(DisplayPropertyTypes.BOX_HEIGHT, entity::getInteractionHeight, entity::setInteractionHeight));
        registry.register(Property.of(InteractionPropertyTypes.RESPONSIVE, entity::isResponsive, entity::setResponsive));
    }
}
