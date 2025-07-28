package me.m56738.easyarmorstands.display;

import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.api.element.DefaultEntityElement;
import me.m56738.easyarmorstands.paper.api.event.element.EntityElementInitializeEvent;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperEntity;
import me.m56738.easyarmorstands.paper.property.display.DisplayBillboardProperty;
import me.m56738.easyarmorstands.paper.property.display.DisplayBrightnessProperty;
import me.m56738.easyarmorstands.paper.property.display.DisplayGlowColorProperty;
import me.m56738.easyarmorstands.paper.property.display.DisplayHeightProperty;
import me.m56738.easyarmorstands.paper.property.display.DisplayLeftRotationProperty;
import me.m56738.easyarmorstands.paper.property.display.DisplayRightRotationProperty;
import me.m56738.easyarmorstands.paper.property.display.DisplayScaleProperty;
import me.m56738.easyarmorstands.paper.property.display.DisplayTranslationProperty;
import me.m56738.easyarmorstands.paper.property.display.DisplayViewRangeProperty;
import me.m56738.easyarmorstands.paper.property.display.DisplayWidthProperty;
import me.m56738.easyarmorstands.paper.property.display.block.BlockDisplayBlockProperty;
import me.m56738.easyarmorstands.paper.property.display.interaction.InteractionHeightProperty;
import me.m56738.easyarmorstands.paper.property.display.interaction.InteractionResponsiveProperty;
import me.m56738.easyarmorstands.paper.property.display.interaction.InteractionWidthProperty;
import me.m56738.easyarmorstands.paper.property.display.item.ItemDisplayItemProperty;
import me.m56738.easyarmorstands.paper.property.display.item.ItemDisplayTransformProperty;
import me.m56738.easyarmorstands.paper.property.display.text.TextDisplayAlignmentProperty;
import me.m56738.easyarmorstands.paper.property.display.text.TextDisplayBackgroundProperty;
import me.m56738.easyarmorstands.paper.property.display.text.TextDisplayLineWidthProperty;
import me.m56738.easyarmorstands.paper.property.display.text.TextDisplaySeeThroughProperty;
import me.m56738.easyarmorstands.paper.property.display.text.TextDisplayShadowProperty;
import me.m56738.easyarmorstands.paper.property.display.text.TextDisplayTextProperty;
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
        DefaultEntityElement element = event.getElement();
        registerProperties(PaperEntity.toNative(element.getEntity()), element.getProperties());
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
        registry.register(new DisplayBillboardProperty(entity));
        registry.register(new DisplayBrightnessProperty(entity));
        registry.register(new DisplayWidthProperty(entity));
        registry.register(new DisplayHeightProperty(entity));
        registry.register(new DisplayGlowColorProperty(entity));
        registry.register(new DisplayViewRangeProperty(entity));
    }

    private void registerItemDisplayProperties(ItemDisplay entity, PropertyRegistry registry) {
        registry.register(new ItemDisplayItemProperty(entity));
        registry.register(new ItemDisplayTransformProperty(entity));
    }

    private void registerBlockDisplayProperties(BlockDisplay entity, PropertyRegistry registry) {
        registry.register(new BlockDisplayBlockProperty(entity));
    }

    private void registerTextDisplayProperties(TextDisplay entity, PropertyRegistry registry) {
        if (TextDisplayAlignmentProperty.isSupported()) {
            registry.register(new TextDisplayAlignmentProperty(entity));
        }
        if (TextDisplayBackgroundProperty.isSupported()) {
            registry.register(new TextDisplayBackgroundProperty(entity));
        }
        registry.register(new TextDisplayLineWidthProperty(entity));
        registry.register(new TextDisplaySeeThroughProperty(entity));
        registry.register(new TextDisplayShadowProperty(entity));
        registry.register(new TextDisplayTextProperty(entity));
    }

    private void registerInteractionProperties(Interaction entity, PropertyRegistry registry) {
        registry.register(new InteractionWidthProperty(entity));
        registry.register(new InteractionHeightProperty(entity));
        registry.register(new InteractionResponsiveProperty(entity));
    }
}
